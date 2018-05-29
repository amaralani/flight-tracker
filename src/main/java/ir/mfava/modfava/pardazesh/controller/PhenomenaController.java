package ir.mfava.modfava.pardazesh.controller;

import ir.mfava.modfava.pardazesh.model.Phenomena;
import ir.mfava.modfava.pardazesh.model.Weather;
import ir.mfava.modfava.pardazesh.model.report.event.Event;
import ir.mfava.modfava.pardazesh.service.PhenomenaService;
import ir.mfava.modfava.pardazesh.service.WeatherService;
import ir.mfava.modfava.pardazesh.service.report.event.EventService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/base-info/phenomena")
public class PhenomenaController extends BaseController{

    @Autowired
    private PhenomenaService phenomenaService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private EventService eventService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String getEvents(ModelMap map, HttpSession session) {
        map.put("weatherEvents", phenomenaService.getAll());
        map.put("successMessage", session.getAttribute("successMessage"));
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "weather-events";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveEvent(@RequestParam(name = "title") String title,
                            @RequestParam(name = "abbreviation") String abbreviation,
                            @RequestParam(name = "description") String description,
                            @RequestParam(name = "priority") Integer priority,
                            @RequestParam(name = "id", required = false) Long id,
                            @RequestParam(name = "isNew", required = false) Boolean isNew,
                            HttpSession session,
                            HttpServletRequest request,
                            Authentication authentication) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity","Phenomena");
        descriptionMap.put("title", title);
        descriptionMap.put("abbreviation", abbreviation);
        descriptionMap.put("description", description);
        descriptionMap.put("priority", String.valueOf(priority));
        descriptionMap.put("id", String.valueOf(id));
        descriptionMap.put("isNew", String.valueOf(isNew));

        Phenomena phenomena;
        Event.SubType subType;
        if (isNew) {
            phenomena = new Phenomena();
            subType = Event.SubType.NEW_DATA;
        } else {
            phenomena = phenomenaService.getById(id);
            subType = Event.SubType.EDIT_DATA;
        }
        phenomena.setTitle(title);
        phenomena.setAbbreviation(abbreviation);
        phenomena.setDescription(description);
        phenomena.setPriority(priority);
        Event.Flag flag;
        try {
            phenomenaService.save(phenomena);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (EntityNotFoundException enfex) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(),request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, subType, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/phenomena/";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(HttpSession session,
                         @RequestParam(name = "id") Long id,
                         HttpServletRequest request,
                         Authentication authentication) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity","Phenomena");
        descriptionMap.put("id", String.valueOf(id));

        Phenomena phenomena = phenomenaService.getById(id);
        Weather weather = new Weather();
        weather.setPhenomena(phenomena);
        Event.Flag flag;
        if (weatherService.exists(weather)) {
            session.setAttribute("errorMessage", "برای این پدیده اطلاعات ثبت شده و امکان حذف آن وجود ندارد.");
            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(),request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.DELETE, Event.SubType.DELETE_FROM_DB, Event.Flag.FAILURE, descriptionMap, Event.Sensitivity.NOTIFICATION);
            return "redirect:/base-info/station/";
        }
        try {
            phenomenaService.remove(phenomena);
            session.setAttribute("successMessage", "حذف ایستگاه با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(),request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.DELETE, Event.SubType.DELETE_FROM_DB, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/phenomena/";
    }

    @RequestMapping(value = "/save-icon", method = RequestMethod.POST)
    public String saveIcon(@RequestParam(name = "file") MultipartFile file,
                           @RequestParam(name = "id") Long id,
                           HttpSession session) throws IOException {
        if (!file.getOriginalFilename().endsWith(".png")) {
            session.setAttribute("errorMessage", "خطا: فرمت فایل بارگذاری شده باید png باشد.");
            return "redirect:/base-info/phenomena/";
        }
        byte[] bytes = file.getBytes();
        String root = session.getServletContext().getRealPath("/");
        File iconsDirectory = new File(root + "/icons/");
        if (!iconsDirectory.exists()) {
            iconsDirectory.mkdir();
        }
        Path path = Paths.get(iconsDirectory + "/" + file.getOriginalFilename());
        Files.write(path, bytes);
        Phenomena phenomena = phenomenaService.getById(id);
        phenomena.setIcon(file.getOriginalFilename());
        phenomenaService.save(phenomena);
        session.setAttribute("successMessage", "ثبت تصویر پدیده با موفقیت انجام شد.");
        return "redirect:/base-info/phenomena/";
    }

    @RequestMapping(value = "/view-icon/{file}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] viewIcon(@PathVariable("file") String icon, HttpSession httpSession) throws IOException {
        String root = httpSession.getServletContext().getRealPath("/");
        File iconsDirectory = new File(root + "/icons/");
        if (!iconsDirectory.exists()) {
            iconsDirectory.mkdir();
        }
        return Files.readAllBytes(new File(iconsDirectory + "/" + icon + ".png").toPath());
    }
}
