package ir.mfava.modfava.pardazesh.controller;

import ir.mfava.modfava.pardazesh.model.Weather;
import ir.mfava.modfava.pardazesh.model.WeatherStation;
import ir.mfava.modfava.pardazesh.service.WeatherService;
import ir.mfava.modfava.pardazesh.service.WeatherStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(value = "/base-info/station")
public class WeatherStationController {

    @Autowired
    private WeatherStationService weatherStationService;
    @Autowired
    private WeatherService weatherService;

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
    public String savePerson(@RequestParam(name = "provinceCode") String provinceCode,
                             @RequestParam(name = "farsiName") String farsiName,
                             @RequestParam(name = "name") String name,
                             @RequestParam(name = "stationId") String stationId,
                             @RequestParam(name = "stationNo") Integer stationNo,
                             @RequestParam(name = "latitude") String latitude,
                             @RequestParam(name = "longitude") String longitude,
                             @RequestParam(name = "altitude") String altitude,
                             @RequestParam(name = "level") Integer level,
                             @RequestParam(name = "id",required = false) Long id,
                             @RequestParam(name = "isNew", required = false) Boolean isNew,
                             HttpSession session) {
        WeatherStation weatherStation;
        if (isNew) {
            weatherStation = new WeatherStation();
        } else {
            weatherStation = weatherStationService.getById(String.valueOf(id));
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
        } catch (EntityNotFoundException enfex) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
        }
        return "redirect:/base-info/station/";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(HttpSession session,
                         @RequestParam(name = "id") Long id) {
        WeatherStation weatherStation =weatherStationService.getById(String.valueOf(id));
        Weather weather = new Weather();
        weather.setWeatherStation(weatherStation);
        if(weatherService.exists(weather)) {
            session.setAttribute("errorMessage", "برای این ایستگاه اطلاعات ثبت شده و امکان حذف آن وجود ندارد.");
            return "redirect:/base-info/station/";
        }
        try{
            weatherStationService.remove(weatherStation);
            session.setAttribute("successMessage", "حذف ایستگاه با موفقیت انجام شد.");
            return "redirect:/base-info/station/";
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
            return "redirect:/base-info/station/";
        }
    }
}
