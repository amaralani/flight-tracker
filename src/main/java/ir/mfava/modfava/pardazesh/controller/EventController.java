package ir.mfava.modfava.pardazesh.controller;

import ir.mfava.modfava.pardazesh.model.Event;
import ir.mfava.modfava.pardazesh.model.Weather;
import ir.mfava.modfava.pardazesh.service.EventService;
import ir.mfava.modfava.pardazesh.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
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


@Controller
@RequestMapping(value = "/base-info/event")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String getEvents(ModelMap map, HttpSession session) {
        map.put("weatherEvents", eventService.getAll());
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
                            HttpSession session) {
        Event event;
        if (isNew) {
            event = new Event();
        } else {
            event = eventService.getById(String.valueOf(id));
        }
        event.setTitle(title);
        event.setAbbreviation(abbreviation);
        event.setDescription(description);
        event.setPriority(priority);
        try {
            eventService.save(event);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
        } catch (EntityNotFoundException enfex) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
        }
        return "redirect:/base-info/event/";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(HttpSession session,
                         @RequestParam(name = "id") Long id) {
        Event event = eventService.getById(String.valueOf(id));
        Weather weather = new Weather();
        weather.setEvent(event);
        if (weatherService.exists(weather)) {
            session.setAttribute("errorMessage", "برای این پدیده اطلاعات ثبت شده و امکان حذف آن وجود ندارد.");
            return "redirect:/base-info/station/";
        }
        try {
            eventService.remove(event);
            session.setAttribute("successMessage", "حذف ایستگاه با موفقیت انجام شد.");
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
        }
        return "redirect:/base-info/event/";
    }

    @RequestMapping(value = "/save-icon", method = RequestMethod.POST)
    public String saveIcon(@RequestParam(name = "file") MultipartFile file,
                           @RequestParam(name = "id") Long id,
                           HttpSession session) throws IOException {
        if (!file.getOriginalFilename().endsWith(".png")) {
            session.setAttribute("errorMessage", "خطا: فرمت فایل بارگذاری شده باید png باشد.");
            return "redirect:/base-info/event/";
        }
        byte[] bytes = file.getBytes();
        String root = session.getServletContext().getRealPath("/");
        File iconsDirectory = new File(root + "/icons/");
        if (!iconsDirectory.exists()) {
            iconsDirectory.mkdir();
        }
        Path path = Paths.get(iconsDirectory + "/" + file.getOriginalFilename());
        Files.write(path, bytes);
        Event event = eventService.getById(String.valueOf(id));
        event.setIcon(file.getOriginalFilename());
        eventService.save(event);
        session.setAttribute("successMessage", "ثبت تصویر پدیده با موفقیت انجام شد.");
        return "redirect:/base-info/event/";
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
