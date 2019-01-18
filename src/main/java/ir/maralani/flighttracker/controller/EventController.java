//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ir.maralani.flighttracker.controller;

import ir.maralani.flighttracker.model.Phenomena;
import ir.maralani.flighttracker.model.Weather;
import ir.maralani.flighttracker.service.PhenomenaService;
import ir.maralani.flighttracker.service.WeatherService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"/base-info/event"})
public class EventController {
    @Autowired
    private PhenomenaService phenomenaService;
    @Autowired
    private WeatherService weatherService;

    public EventController() {
    }

    @RequestMapping(
            value = {"/", ""},
            method = {RequestMethod.GET}
    )
    public String getEvents(ModelMap map, HttpSession session) {
        map.put("weatherEvents", this.phenomenaService.getAll());
        map.put("successMessage", session.getAttribute("successMessage"));
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "weather-events";
    }

    @RequestMapping(
            value = {"/save"},
            method = {RequestMethod.POST}
    )
    public String saveEvent(@RequestParam(
            name = "title"
    ) String title, @RequestParam(
            name = "abbreviation"
    ) String abbreviation, @RequestParam(
            name = "description"
    ) String description, @RequestParam(
            name = "priority"
    ) Integer priority, @RequestParam(
            name = "id",
            required = false
    ) Long id, @RequestParam(
            name = "isNew",
            required = false
    ) Boolean isNew, HttpSession session) {
        Phenomena phenomena;
        if(isNew.booleanValue()) {
            phenomena = new Phenomena();
        } else {
            phenomena = (Phenomena)this.phenomenaService.getById(id);
        }

        phenomena.setTitle(title);
        phenomena.setAbbreviation(abbreviation);
        phenomena.setDescription(description);
        phenomena.setPriority(priority);

        try {
            this.phenomenaService.save(phenomena);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
        } catch (EntityNotFoundException var10) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
        }

        return "redirect:/base-info/phenomena/";
    }

    @RequestMapping(
            value = {"/remove"},
            method = {RequestMethod.POST}
    )
    public String remove(HttpSession session, @RequestParam(
            name = "id"
    ) Long id) {
        Phenomena phenomena = (Phenomena)this.phenomenaService.getById(id);
        Weather weather = new Weather();
        weather.setPhenomena(phenomena);
        if(this.weatherService.exists(weather)) {
            session.setAttribute("errorMessage", "برای این پدیده اطلاعات ثبت شده و امکان حذف آن وجود ندارد.");
            return "redirect:/base-info/station/";
        } else {
            try {
                this.phenomenaService.remove(phenomena);
                session.setAttribute("successMessage", "حذف ایستگاه با موفقیت انجام شد.");
            } catch (Exception var6) {
                var6.printStackTrace();
                session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
            }

            return "redirect:/base-info/phenomena/";
        }
    }

    @RequestMapping(
            value = {"/save-icon"},
            method = {RequestMethod.POST}
    )
    public String saveIcon(@RequestParam(
            name = "file"
    ) MultipartFile file, @RequestParam(
            name = "id"
    ) Long id, HttpSession session) throws IOException {
        if(!file.getOriginalFilename().endsWith(".png")) {
            session.setAttribute("errorMessage", "خطا: فرمت فایل بارگذاری شده باید png باشد.");
            return "redirect:/base-info/phenomena/";
        } else {
            byte[] bytes = file.getBytes();
            String root = session.getServletContext().getRealPath("/");
            File iconsDirectory = new File(root + "/icons/");
            if(!iconsDirectory.exists()) {
                iconsDirectory.mkdir();
            }

            Path path = Paths.get(iconsDirectory + "/" + file.getOriginalFilename(), new String[0]);
            Files.write(path, bytes, new OpenOption[0]);
            Phenomena phenomena = (Phenomena)this.phenomenaService.getById(id);
            phenomena.setIcon(file.getOriginalFilename());
            this.phenomenaService.save(phenomena);
            session.setAttribute("successMessage", "ثبت تصویر پدیده با موفقیت انجام شد.");
            return "redirect:/base-info/phenomena/";
        }
    }

    @RequestMapping(
            value = {"/view-icon/{file}"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public byte[] viewIcon(@PathVariable("file") String icon, HttpSession httpSession) throws IOException {
        String root = httpSession.getServletContext().getRealPath("/");
        File iconsDirectory = new File(root + "/icons/");
        if(!iconsDirectory.exists()) {
            iconsDirectory.mkdir();
        }

        return Files.readAllBytes((new File(iconsDirectory + "/" + icon + ".png")).toPath());
    }
}
