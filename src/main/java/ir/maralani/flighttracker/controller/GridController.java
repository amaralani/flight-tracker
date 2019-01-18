package ir.maralani.flighttracker.controller;

import ir.maralani.flighttracker.model.DTO.GridDTO;
import ir.maralani.flighttracker.model.DTO.JSONMessage;
import ir.maralani.flighttracker.model.Track;
import ir.maralani.flighttracker.model.Weather;
import ir.maralani.flighttracker.service.TrackService;
import ir.maralani.flighttracker.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
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
    @Autowired
    private TrackService trackService;


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

    @RequestMapping(value = "/track", method = RequestMethod.GET)
    public String showTrackGrid(ModelMap map) {
        Track track = new Track();
        List<Track> tracks = trackService.findByExample(track);
        map.put("tracks",tracks);
        return "track-grid";
    }

    @ResponseBody
    @RequestMapping(value = "/tracks/get", method = RequestMethod.GET)
    public JSONMessage getTracks(@RequestParam(name = "code",required = false) String code,
                                 @RequestParam(name = "speed",required = false) Integer speed,
                                 @RequestParam(name = "radar",required = false) String radar,
                                 @RequestParam(name = "longitude",required = false) Float longitude,
                                 @RequestParam(name = "latitude",required = false) Float latitude,
                                 @RequestParam(name = "altitude",required = false) Float altitude,
                                 @RequestParam(name = "source",required = false) String source,
                                 @RequestParam(name = "destination",required = false) String destination
                                 ){
        Track track = new Track();
        track.setCode(StringUtils.isEmpty(code) ? null : code);
        track.setSpeed(speed);
        track.setRadar(StringUtils.isEmpty(radar) ? null : radar);
        track.setLongitude(longitude);
        track.setLatitude(latitude);
        track.setAltitude(altitude);
        track.setSource(StringUtils.isEmpty(source) ? null : source);
        track.setDestination(StringUtils.isEmpty(destination) ? null : destination);
        List<Track> tracks = trackService.findByExample(track);
        Map<String,Object> map = new HashMap<>();
        map.put("tracks",tracks);
        return JSONMessage.Success(map);
    }

}
