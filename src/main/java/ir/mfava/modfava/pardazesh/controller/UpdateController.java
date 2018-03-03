package ir.mfava.modfava.pardazesh.controller;

import ir.mfava.modfava.pardazesh.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/base-info/update")
public class UpdateController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = {"/",""},method = RequestMethod.GET)
    public String showUpdatePage(ModelMap map, HttpSession session){
        map.put("successMessage", session.getAttribute("successMessage"));
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "view-update";
    }

    @RequestMapping(value = "/process",method = RequestMethod.GET)
    public String processData(ModelMap map, HttpSession session){
        if(weatherService.process()) {
            session.setAttribute("successMessage","فرآیند با موفقیت شروع شد.");
        }else {
            session.setAttribute("errorMessage","فرآیند به روزرسانی قبلی هنوز به اتمام نرسیده است.");
        }
        return "redirect:/base-info/update/";
    }

}

