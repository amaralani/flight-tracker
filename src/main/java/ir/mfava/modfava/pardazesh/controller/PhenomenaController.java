package ir.mfava.modfava.pardazesh.controller;

import ir.mfava.modfava.pardazesh.model.Phenomena;
import ir.mfava.modfava.pardazesh.model.Weather;
import ir.mfava.modfava.pardazesh.service.PhenomenaService;
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
@RequestMapping(value = "/base-info/phenomena")
public class PhenomenaController {

    @Autowired
    private PhenomenaService phenomenaService;
    @Autowired
    private WeatherService weatherService;

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
                            HttpSession session) {
        Phenomena phenomena;
        if (isNew) {
            phenomena = new Phenomena();
        } else {
            phenomena = phenomenaService.getById(id);
        }
        phenomena.setTitle(title);
        phenomena.setAbbreviation(abbreviation);
        phenomena.setDescription(description);
        phenomena.setPriority(priority);
        try {
            phenomenaService.save(phenomena);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
        } catch (EntityNotFoundException enfex) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
        }
        return "redirect:/base-info/phenomena/";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(HttpSession session,
                         @RequestParam(name = "id") Long id) {
        Phenomena phenomena = phenomenaService.getById(id);
        Weather weather = new Weather();
        weather.setPhenomena(phenomena);
        if (weatherService.exists(weather)) {
            session.setAttribute("errorMessage", "برای این پدیده اطلاعات ثبت شده و امکان حذف آن وجود ندارد.");
            return "redirect:/base-info/station/";
        }
        try {
            phenomenaService.remove(phenomena);
            session.setAttribute("successMessage", "حذف ایستگاه با موفقیت انجام شد.");
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
        }
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
