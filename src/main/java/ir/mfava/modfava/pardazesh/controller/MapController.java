package ir.mfava.modfava.pardazesh.controller;


import ir.mfava.modfava.pardazesh.model.Bulletin;
import ir.mfava.modfava.pardazesh.model.DTO.WeatherDTO;
import ir.mfava.modfava.pardazesh.model.Weather;
import ir.mfava.modfava.pardazesh.service.BulletinService;
import ir.mfava.modfava.pardazesh.service.WeatherService;
import ir.mfava.modfava.pardazesh.util.DTOUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/map")
public class MapController extends BaseController {

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private BulletinService bulletinService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String showMap(ModelMap map, HttpServletRequest request) throws IOException {

        List<WeatherDTO> weatherDTOList = new ArrayList<>();

        List<Weather> weathers = weatherService.getCurrentWeather();
        DTOUtils.getWeathersFromList(weatherDTOList, weathers);
        map.put("weatherList", weatherDTOList);

        List<Bulletin> bulletins = bulletinService.getCurrentForecasts();
        List<WeatherDTO> bulletinDTOs = new ArrayList<>();

        DTOUtils.getBulletinDTOsFromList(bulletins, bulletinDTOs);
        map.put("forecasts", bulletinDTOs);
        List<String> icons = new ArrayList<>();
        File iconsDirectory = getIconsDirectory();
        if (iconsDirectory != null && iconsDirectory.listFiles() != null) {
            List<File> files = Arrays.asList(iconsDirectory.listFiles());
            for (File file : files) {
                icons.add(file.getName());
            }
        }
        StringBuffer url = request.getRequestURL();
        String uri = request.getRequestURI();
        String host = url.substring(0, url.indexOf(uri) - 5); //result
        map.put("host", host);
        map.put("icons", icons);
        return "map";
    }

}