package ir.mfava.modfava.pardazesh.controller;

import ir.mfava.modfava.pardazesh.model.DTO.GridDTO;
import ir.mfava.modfava.pardazesh.model.DTO.JSONMessage;
import ir.mfava.modfava.pardazesh.model.Weather;
import ir.mfava.modfava.pardazesh.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/grid")
public class GridController {

    @Autowired
    private WeatherService weatherService;


    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String showGrid(ModelMap map, HttpSession session) {
        List<Weather> weatherList = weatherService.getCurrentWeather();
        List<GridDTO> gridDTOs = new ArrayList<>();
        for (Weather weather : weatherList) {
            GridDTO gridDTO = new GridDTO();
            gridDTO.mapFromWeather(weather);
            gridDTOs.add(gridDTO);
        }
        map.put("gridData", gridDTOs);
        map.put("successMessage", session.getAttribute("successMessage"));
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "grid";
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public JSONMessage getProvinceForecasts(HttpServletRequest request/*,
                                            @RequestParam(name = "wsu") Long weatherSpeedUnit,
                                            @RequestParam(name = "tu") Long temperatureUnit,
                                            @RequestParam(name = "dpu") Long dewPointUnit,
                                            @RequestParam(name = "pu") Long pressureUnit*/) {
        List<Weather> weatherList = weatherService.getCurrentWeather();
        List<GridDTO> gridDTOs = new ArrayList<>();
        for (Weather weather : weatherList) {
            GridDTO gridDTO = new GridDTO();
            gridDTO.mapFromWeather(weather);
            gridDTOs.add(gridDTO);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("gridData", gridDTOs);
        return JSONMessage.Success("success", map);
    }

}
