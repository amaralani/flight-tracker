package ir.mfava.modfava.pardazesh.controller;

import ir.mfava.modfava.pardazesh.model.Weather;
import ir.mfava.modfava.pardazesh.model.WeatherStation;
import ir.mfava.modfava.pardazesh.model.report.event.Event;
import ir.mfava.modfava.pardazesh.service.WeatherService;
import ir.mfava.modfava.pardazesh.service.WeatherStationService;
import ir.mfava.modfava.pardazesh.service.report.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/base-info/station")
public class WeatherStationController extends BaseController {

    @Autowired
    private WeatherStationService weatherStationService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private EventService eventService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String getStations(ModelMap map, HttpSession session) {
        map.put("weatherStations", weatherStationService.getAll());
        map.put("successMessage", session.getAttribute("successMessage"));
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "weather-stations";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveWeatherStation(@RequestParam(name = "provinceCode") String provinceCode,
                                     @RequestParam(name = "farsiName") String farsiName,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "stationId") String stationId,
                                     @RequestParam(name = "stationNo") Integer stationNo,
                                     @RequestParam(name = "latitude") String latitude,
                                     @RequestParam(name = "longitude") String longitude,
                                     @RequestParam(name = "altitude") String altitude,
                                     @RequestParam(name = "level") Integer level,
                                     @RequestParam(name = "id", required = false) Long id,
                                     @RequestParam(name = "isNew", required = false) Boolean isNew,
                                     HttpSession session,
                                     HttpServletRequest request,
                                     Authentication authentication) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity","Weather Station");
        descriptionMap.put("provinceCode", provinceCode);
        descriptionMap.put("farsiName", farsiName);
        descriptionMap.put("name", name);
        descriptionMap.put("stationId", stationId);
        descriptionMap.put("stationNo", String.valueOf(stationNo));
        descriptionMap.put("latitude", latitude);
        descriptionMap.put("longitude", longitude);
        descriptionMap.put("altitude", altitude);
        descriptionMap.put("level", String.valueOf(level));
        descriptionMap.put("id", String.valueOf(id));
        descriptionMap.put("isNew", String.valueOf(isNew));

        WeatherStation weatherStation;
        Event.Flag flag;
        Event.SubType subType;
        if (isNew) {
            weatherStation = new WeatherStation();
            subType = Event.SubType.NEW_DATA;
        } else {
            weatherStation = weatherStationService.getById(id);
            subType = Event.SubType.EDIT_DATA;
        }
        weatherStation.setProvinceCode(provinceCode);
        weatherStation.setName(name);
        weatherStation.setFarsiName(farsiName);
        weatherStation.setStationId(stationId);
        weatherStation.setStationNo(stationNo);
        weatherStation.setLatitude(latitude);
        weatherStation.setLongitude(longitude);
        weatherStation.setAltitude(altitude);
        weatherStation.setLevel(level);
        try {
            weatherStationService.save(weatherStation);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (EntityNotFoundException enfex) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, subType, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/station/";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(HttpSession session,
                         @RequestParam(name = "id") Long id,
                         HttpServletRequest request,
                         Authentication authentication) {
        Map<String, String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity", "Weather Station");
        descriptionMap.put("id", String.valueOf(id));

        WeatherStation weatherStation = weatherStationService.getById(id);
        Weather weather = new Weather();
        weather.setWeatherStation(weatherStation);
        Event.Flag flag;
        if (weatherService.exists(weather)) {
            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, Event.SubType.DELETE_FROM_DB, Event.Flag.FAILURE, descriptionMap, Event.Sensitivity.NOTIFICATION);
            session.setAttribute("errorMessage", "برای این ایستگاه اطلاعات ثبت شده و امکان حذف آن وجود ندارد.");
            return "redirect:/base-info/station/";
        }
        try {
            weatherStationService.remove(weatherStation);
            session.setAttribute("successMessage", "حذف ایستگاه با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception ex) {
            session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
            ex.printStackTrace();
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, Event.SubType.DELETE_FROM_DB, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/station/";
    }
}