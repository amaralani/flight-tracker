package ir.maralani.flighttracker.controller;

import ir.maralani.flighttracker.model.report.event.Event;
import ir.maralani.flighttracker.service.WeatherService;
import ir.maralani.flighttracker.service.report.event.EventService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/base-info/update")
public class UpdateController extends BaseController {

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private EventService eventService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String showUpdatePage(ModelMap map, HttpSession session) {
        map.put("successMessage", session.getAttribute("successMessage"));
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "view-update";
    }

    @RequestMapping(value = "/process", method = RequestMethod.GET)
    public String processData(HttpSession session,
                              HttpServletRequest request,
                              Authentication authentication) {
        Map<String, String> descriptionMap = new HashMap<>();
        descriptionMap.put("Action", "Weather update ");
        descriptionMap.put("timestamp", String.valueOf(new Date()));

        Event.Flag flag;
        if (weatherService.process()) {
            session.setAttribute("successMessage", "فرآیند با موفقیت شروع شد.");
            flag = Event.Flag.SUCCESS;
        } else {
            session.setAttribute("errorMessage", "فرآیند به روزرسانی قبلی هنوز به اتمام نرسیده است.");
            flag = Event.Flag.SUCCESS;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, Event.SubType.EDIT_DATA, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/update/";
    }

}

