package ir.maralani.flighttracker.controller.rest;

import ir.maralani.flighttracker.model.exception.BadRESTCredentialsException;
import ir.maralani.flighttracker.model.report.event.Event;
import ir.maralani.flighttracker.model.report.event.SoftwareInfo;
import ir.maralani.flighttracker.service.report.event.ActionImportanceService;
import ir.maralani.flighttracker.service.report.event.EventService;
import ir.maralani.flighttracker.service.report.event.SoftwareInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sec/")
public class SecurityReportController {

    @Autowired
    EventService eventService;
    @Autowired
    ActionImportanceService actionImportanceService;
    @Autowired
    SoftwareInfoService softwareInfoService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private void checkCredentials(String username, String password) throws IllegalArgumentException, BadRESTCredentialsException {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username is required.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password is required.");
        }
        SoftwareInfo softwareInfo = softwareInfoService.getByUsername(username);

        if (!bCryptPasswordEncoder.matches(password,softwareInfo.getPassword()) ) {
            throw new BadRESTCredentialsException();
        }

    }

    @RequestMapping(value = "receive/data", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Object search(@RequestParam(name = "username", required = false) String username,
                         @RequestParam(name = "password", required = false) String password,
                         @RequestParam(name = "fromTime", required = false) Long fromTime,
                         @RequestParam(name = "toTime", required = false) Long toTime,
                         @RequestParam(name = "actionType", required = false) String actionTypeString,
                         @RequestParam(name = "subtype", required = false) String subTypeString,
                         @RequestParam(name = "sensitivity", required = false) String sensitivityString,
                         @RequestParam(name = "importance", required = false) String importanceString) {
        try {
            checkCredentials(username, password);

            Event.ActionType actionType = null;
            Event.SubType subType = null;
            Event.Sensitivity sensitivity = null;
            Event.Importance importance = null;
            if (fromTime == null || toTime == null) {
                return ResponseEntity.badRequest().body("fromTime or toTime is invalid.");
            }
            if (actionTypeString == null && subTypeString != null) {
                return ResponseEntity.badRequest().body("actionType must be specified when subtype is not null.");
            }
            if (actionTypeString != null) {
                actionType = Event.ActionType.valueOf(actionTypeString);
            }
            if (subTypeString != null) {
                subType = Event.SubType.valueOf(subTypeString);
            }
            if (sensitivityString != null) {
                sensitivity = Event.Sensitivity.valueOf(sensitivityString);
            }
            if (importanceString != null) {
                importance = Event.Importance.valueOf(importanceString);
            }
            try {
                return eventService.search(fromTime, toTime, actionType, subType, sensitivity, importance);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
            }
        } catch (BadRESTCredentialsException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad credentials.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
        }

    }

    @RequestMapping(value = "set-action-importance", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity setActionImportance(@RequestParam(name = "username", required = false) String username,
                                              @RequestParam(name = "password", required = false) String password,
                                              @RequestParam(name = "actionType", required = false) String actionTypeString,
                                              @RequestParam(name = "importance", required = false) String importanceString) {
        try {
            checkCredentials(username, password);
            Event.ActionType actionType;
            Event.Importance importance;
            try {
                if (actionTypeString == null) {
                    return ResponseEntity.badRequest().body("ActionType must be specified.");
                } else {
                    actionType = Event.ActionType.valueOf(actionTypeString);
                }
                if (importanceString == null) {
                    return ResponseEntity.badRequest().body("Importance must be specified.");
                } else {
                    importance = Event.Importance.valueOf(importanceString);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Parameter.");
            }
            try {
                actionImportanceService.setActionImportance(actionType, importance);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
            }
        } catch (BadRESTCredentialsException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad credentials.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
        }
    }

    @RequestMapping(value = "set-password", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity changePassword(@RequestParam(name = "username", required = false) String username,
                                         @RequestParam(name = "password", required = false) String password,
                                         @RequestParam(name = "newPassword", required = false) String newPassword) {
        try {
            checkCredentials(username, password);
            SoftwareInfo softwareInfo = softwareInfoService.getByUsername(username);
            softwareInfo.setPassword(bCryptPasswordEncoder.encode(newPassword));
            softwareInfoService.save(softwareInfo);
            return ResponseEntity.ok().build();
        } catch (BadRESTCredentialsException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad credentials.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
        }
    }
}
