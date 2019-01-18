package ir.maralani.flighttracker.controller;


import ir.maralani.flighttracker.model.Bulletin;
import ir.maralani.flighttracker.model.DTO.JSONMessage;
import ir.maralani.flighttracker.model.DTO.TrackDTO;
import ir.maralani.flighttracker.model.DTO.WeatherDTO;
import ir.maralani.flighttracker.model.Track;
import ir.maralani.flighttracker.model.TrackLog;
import ir.maralani.flighttracker.model.Weather;
import ir.maralani.flighttracker.service.*;
import ir.maralani.flighttracker.model.*;
import ir.maralani.flighttracker.model.DTO.JSONMessage;
import ir.maralani.flighttracker.model.DTO.TrackDTO;
import ir.maralani.flighttracker.model.DTO.WeatherDTO;
import ir.maralani.flighttracker.service.*;
import ir.maralani.flighttracker.util.DTOUtils;
import ir.maralani.flighttracker.util.DateUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/tracks")
public class TrackController extends BaseController {

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private BulletinService bulletinService;
    @Autowired
    private TrackService trackService;
    @Autowired
    private TrackLogService trackLogService;
    @Autowired
    private ConfigurationService configurationService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String showTracks(HttpServletRequest request, ModelMap map) {
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
            File[] files = iconsDirectory.listFiles();
            for (File file : files) {
                icons.add(file.getName());
            }
        }
        map.put("icons", icons);

        Configuration configuration = configurationService.getAll().get(0);
        String host = "";
        if (configuration != null) {
            host = configuration.getTileServerAddress();
        }
        map.put("host", host);
        return "track";
    }

    @ResponseBody
    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    public JSONMessage getTrackProfile(@RequestParam(name = "trackCode") String trackCode) {
        Map<String, Object> map = new HashMap<>();
        List<TrackLog> trackLogs = trackLogService.getByCode(trackCode);
        for (TrackLog trackLog : trackLogs) {
            trackLog.setDetailedTime(DateUtils.convertJulianToPersian(new Date(trackLog.getTime()), "HH:mm:ss"));
        }
        map.put("logs", trackLogs);
        return JSONMessage.Success(map);
    }


    @ResponseBody
    @RequestMapping(value = {"/renew"}, method = RequestMethod.GET)
    public JSONMessage getNewTracks(@RequestParam(name = "source", required = false) String source,
                                    @RequestParam(name = "destination", required = false) String destination,
                                    @RequestParam(name = "radar", required = false) String radar,
                                    @RequestParam(name = "callsign", required = false) String callsign) {
        Track trackExample = new Track();
        trackExample.setSource(StringUtils.isEmpty(StringUtils.trim(source)) ? null : source);
        trackExample.setDestination(StringUtils.isEmpty(StringUtils.trim(destination)) ? null : destination);
        trackExample.setRadar(StringUtils.isEmpty(StringUtils.trim(radar)) ? null : radar);
        trackExample.setCol3(StringUtils.isEmpty(StringUtils.trim(callsign)) ? null : callsign);
        List<Track> tracks = trackService.findByExample(trackExample);
        Collections.sort(tracks, new Comparator<Track>() {
            @Override
            public int compare(Track o1, Track o2) {
                return o1.getReceiveTime().compareTo(o2.getReceiveTime());
            }
        });
        List<TrackDTO> trackDTOs = new ArrayList<>();
        for (Track track : tracks) {
            TrackDTO trackDTO = new TrackDTO(track);
            String content = "<table class='weather-info-table' style='font-size: larger;font-weight:bold' border='1'>";
            content += "<tr><td> کد </td><td> ";
            if (StringUtils.trim(track.getCode()) != null) {
                content = content + StringUtils.trim(track.getCode()) + "</td><tr>";
            } else {
                content = printNoDataExists(content);
            }
            content += "<tr><td> عرض جغرافیایی </td><td> ";
            if (track.getLatitude() != null) {
                content = content + track.getLatitude() + "</td><tr>";
            } else {
                content = printNoDataExists(content);
            }
            content += "<tr><td> طول جغرافیایی </td><td> ";
            if (track.getLongitude() != null) {
                content = content + track.getLongitude() + "</td><tr>";
            } else {
                content = printNoDataExists(content);
            }
            content += "<tr><td> ارتفاع</td><td> ";
            if (track.getAltitude() != null) {
                content = content + track.getAltitude() + "</td><tr>";
            } else {
                content = printNoDataExists(content);
            }
            content += "<tr><td> سرعت </td><td> ";
            if (track.getSpeed() != null) {
                content = content + track.getSpeed() + "</td><tr>";
            } else {
                content = printNoDataExists(content);
            }
            content += "<tr><td> رادار </td><td> ";
            if (track.getRadar() != null) {
                content = content + track.getRadar() + "</td><tr>";
            } else {
                content = printNoDataExists(content);
            }
            content += "<tr><td> مبدا </td><td> ";
            if (track.getSource() != null) {
                content = content + track.getSource() + "</td><tr>";
            } else {
                content = printNoDataExists(content);
            }
            content += "<tr><td> مقصد </td><td> ";
            if (track.getDestination() != null) {
                content = content + track.getDestination() + "</td><tr>";
            } else {
                content = printNoDataExists(content);
            }
            content += "<tr><td> جهت </td><td> ";
            if (track.getHeading() != null) {
                content = content + track.getHeading() + "</td><tr>";
            } else {
                content = printNoDataExists(content);
            }
            content += "<tr><td> نوع </td><td> ";
            if (track.getType() != null) {
                content = content + track.getType() + "</td><tr>";
            } else {
                content = printNoDataExists(content);
            }
            content = content + "<tr><td colspan=2><a href='#' onclick=\"showProfile('" + StringUtils.trim(track.getCode()) + "')\">نمایش پروفایل پرواز</a></td></tr>";
            content = content + "<tr><td colspan=2><a href='#' onclick=\"showAircraftHistory('" + StringUtils.trim(track.getCode()) + "')\">نمایش چند مسیر آخر هواپیما</a></td></tr>";
            content = content + "<tr><td colspan=2><a href='#' onclick=\"showTrackHistory('" + StringUtils.trim(track.getCode()) + "', '" + StringUtils.trim(track.getSource()) + "', '" + StringUtils.trim(track.getDestination()) + "', '" + StringUtils.trim(track.getRadar()) + "', '" + StringUtils.trim(track.getCol3()) + "')\">نمایش مسیر طی شده</a></td></tr>";
            content = content + "</table>";
            trackDTO.setContent(content);

            trackDTOs.add(trackDTO);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("tracks", trackDTOs);
        return JSONMessage.Success(map);
    }


    @ResponseBody
    @RequestMapping(value = {"/aircraft/history"}, method = RequestMethod.GET)
    public JSONMessage getAircraftHistory(@RequestParam(name = "trackCode") String trackCode) {
        Map<String, Object> map = new HashMap<>();
        List<TrackLog> trackLogs = trackLogService.getAircraftHistoryByCode(trackCode);
        for (TrackLog trackLog : trackLogs) {
            trackLog.setDetailedTime(DateUtils.convertJulianToPersian(new Date(trackLog.getTime()), "HH:mm:ss"));
        }
        map.put("logs", trackLogs);
        return JSONMessage.Success(map);
    }

    @ResponseBody
    @RequestMapping(value = {"/aircraft/find"}, method = RequestMethod.GET)
    public JSONMessage findAircraft(@RequestParam(name = "callsign") String callsign) {
        Map<String, Object> map = new HashMap<>();
        TrackLog trackLog = trackLogService.getAircraftLastPosition(callsign);
        trackLog.setDetailedTime(DateUtils.convertJulianToPersian(new Date(trackLog.getTime()), "HH:mm:ss"));
        map.put("log", trackLog);
        return JSONMessage.Success(map);
    }

    @ResponseBody
    @RequestMapping(value = {"/track/history"}, method = RequestMethod.GET)
    public JSONMessage getTrackHistory(@RequestParam(name = "trackCode") String trackCode) {
        List<TrackLog> tracks = trackLogService.getByCode(trackCode);
        Collections.sort(tracks, new Comparator<TrackLog>() {
            @Override
            public int compare(TrackLog o1, TrackLog o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });

        List<TrackDTO> trackDTOs = new ArrayList<>();
        for (TrackLog track : tracks) {
            TrackDTO trackDTO = new TrackDTO(track);
            trackDTOs.add(trackDTO);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("tracks", trackDTOs);
        return JSONMessage.Success(map);
    }

    @RequestMapping(value = "/image/plane")
    public void getImage(HttpServletResponse httpResponse) throws IOException {
        File trackPlaneImage = getTrackPlaneImage();
        if (trackPlaneImage != null) {
            IOUtils.copy(new FileInputStream(trackPlaneImage), httpResponse.getOutputStream());
        }
    }

    private String printNoDataExists(String content) {
        return content + " اطلاعات موجود نیست " + "</td><tr>";
    }
}