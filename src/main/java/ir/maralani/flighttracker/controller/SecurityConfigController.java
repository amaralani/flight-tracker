package ir.maralani.flighttracker.controller;

import ir.maralani.flighttracker.model.Configuration;
import ir.maralani.flighttracker.model.ContentText;
import ir.maralani.flighttracker.model.report.event.Event;
import ir.maralani.flighttracker.service.ConfigurationService;
import ir.maralani.flighttracker.service.ContentTextService;
import ir.maralani.flighttracker.service.report.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/base-info/security/config")
public class SecurityConfigController extends BaseController {

    @Autowired
    private ContentTextService contentTextService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private EventService eventService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String showSecurityConfigPage(ModelMap map, HttpSession session) {
        ContentText contentText = contentTextService.getByTextContext(ContentText.TextContext.BANNER);
        map.put("contentText", contentText);
        List<Configuration> configurations = configurationService.getAll();
        Configuration configuration;
        Integer complexity;
        if (!configurations.isEmpty()) {
            configuration = configurations.get(0);
            complexity = configuration.getPasswordComplexity().ordinal();
        } else {
            configuration = new Configuration();
            complexity = 0;
        }
        map.put("configuration", configuration);
        map.put("complexity", complexity);
        map.put("successMessage", session.getAttribute("successMessage"));
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "security-configs";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveSecurityConfig(@RequestParam(name = "loginFailureAccountLockCount") Integer loginFailureAccountLockCount,
                                     @RequestParam(name = "passwordComplexity") Integer passwordComplexity,
                                     @RequestParam(name = "passwordLength") Integer passwordLength,
                                     @RequestParam(name = "passwordResetPeriod") Integer passwordResetPeriod,
                                     @RequestParam(name = "id", required = false) Long id,
                                     @RequestParam(name = "sessionTimeoutInSeconds") Integer sessionTimeoutInSeconds,
                                     HttpSession session,
                                     HttpServletRequest request,
                                     Authentication authentication) {
        Map<String, String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity", "SecurityConfig");
        descriptionMap.put("loginFailureAccountLockCount", String.valueOf(loginFailureAccountLockCount));
        descriptionMap.put("passwordComplexity", String.valueOf(passwordComplexity));
        descriptionMap.put("passwordLength", String.valueOf(passwordLength));
        descriptionMap.put("passwordResetPeriod", String.valueOf(passwordResetPeriod));
        descriptionMap.put("id", String.valueOf(id));
        descriptionMap.put("sessionTimeoutInSeconds", String.valueOf(sessionTimeoutInSeconds));

        Configuration configuration;
        Event.SubType subType;
        Event.Flag flag;
        if (id != null) {
            configuration = configurationService.getById(id);
            subType = Event.SubType.CHANGE_SECURITY_CONFIG;
        } else {
            configuration = new Configuration();
            subType = Event.SubType.NEW_DATA;
        }
        for (Configuration.PasswordComplexity complexity : Configuration.PasswordComplexity.values()) {
            if (passwordComplexity == complexity.ordinal()) {
                configuration.setPasswordComplexity(complexity);
                break;
            }
        }
        configuration.setLoginFailureAccountLockCount(loginFailureAccountLockCount);
        configuration.setPasswordLength(passwordLength);
        configuration.setPasswordResetPeriod(passwordResetPeriod);
        configuration.setSessionTimeoutInSeconds(sessionTimeoutInSeconds);
        try {
            configurationService.save(configuration);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, subType, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/security/config";
    }

    @RequestMapping(value = "/saveBanner", method = RequestMethod.POST)
    public String saveBanner(@RequestParam(name = "text") String text,
                             @RequestParam(name = "id", required = false) Long id,
                             HttpSession session,
                             HttpServletRequest request,
                             Authentication authentication) {
        Map<String, String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity", "Banner");
        descriptionMap.put("text", text);
        descriptionMap.put("id", String.valueOf(id));

        Event.SubType subType;
        Event.Flag flag;
        ContentText contentText;
        if (id == null) {
            contentText = new ContentText();
            subType = Event.SubType.NEW_DATA;
        } else {
            contentText = contentTextService.getById(id);
            subType = Event.SubType.EDIT_DATA;
        }
        try {
            contentText.setText(text);
            contentText.setTextContext(ContentText.TextContext.BANNER);
            contentTextService.save(contentText);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, subType, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/security/config/";
    }

    @RequestMapping(value = "/track/save/plane", method = RequestMethod.POST)
    public String saveFile(@RequestParam(name = "file") MultipartFile file,
                           HttpSession session) {

        if (file != null && file.getSize() > 0) {
            if (!file.getOriginalFilename().endsWith(".png")) {
                session.setAttribute("errorMessage", "فایل باید حتما png باشد.");
                return "redirect:/base-info/security/config/";
            }
            try {
                session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");

                File planeImage = getTrackPlaneImage();
                if (planeImage.exists()) {
                    planeImage.delete();
                }
                Path path = Paths.get(planeImage.getPath());
                try {
                    Files.write(path, file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
            }
        } else {
            System.out.println("Bad file");
            session.setAttribute("errorMessage", "خطا در بارگذاری فایل.");
            return "redirect:/base-info/security/config/";
        }
        return "redirect:/base-info/security/config/";
    }
}
