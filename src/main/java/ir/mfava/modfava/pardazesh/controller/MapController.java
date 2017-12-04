package ir.mfava.modfava.pardazesh.controller;


import ir.mfava.modfava.pardazesh.model.Bulletin;
import ir.mfava.modfava.pardazesh.model.DTO.WeatherDTO;
import ir.mfava.modfava.pardazesh.model.Phenomena;
import ir.mfava.modfava.pardazesh.model.Weather;
import ir.mfava.modfava.pardazesh.service.BulletinService;
import ir.mfava.modfava.pardazesh.service.WeatherService;
import ir.mfava.modfava.pardazesh.util.DateUtils;
import ir.mfava.modfava.pardazesh.util.metar.MetarConstants;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/map")
public class MapController {

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private BulletinService bulletinService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String showMap(ModelMap map, HttpServletRequest request) {

        List<WeatherDTO> weatherDTOList = new ArrayList<>();

        List<Weather> weathers = weatherService.getCurrentWeather();
        for (Weather weather : weathers) {
            WeatherDTO weatherDTO = new WeatherDTO();
            weatherDTO.setLatitude(weather.getWeatherStation().getLatitude());
            weatherDTO.setLongitude(weather.getWeatherStation().getLongitude());
            if (weather.getWeatherStation().getLevel() == 1) {
                weatherDTO.setZoomLevelMax(8);
                weatherDTO.setZoomLevelMin(5);
            } else {
                weatherDTO.setZoomLevelMax(18);
                weatherDTO.setZoomLevelMin(8);
            }
            String markerType;
            String cloud = weather.getCloud();
            if (cloud != null) {
                switch (cloud) {
                    case MetarConstants.METAR_OVERCAST:
                        markerType = "4";
                        break;
                    case MetarConstants.METAR_BROKEN:
                        markerType = "3";
                        break;
                    case MetarConstants.METAR_SCATTERED:
                        markerType = "2";
                        break;
                    case MetarConstants.METAR_FEW:
                        markerType = "1";
                        break;
                    default:
                        markerType = "0";
                        break;
                }
            } else {
                markerType = "0";
            }

            markerType = markerType + "cloud";
            Phenomena phenomena = weather.getPhenomena();
            if (phenomena != null) {
                markerType = markerType + "_" + phenomena.getIcon();
            }
            if (!markerType.startsWith("0") && markerType.endsWith("cloud")) {
                markerType = markerType + "_norain";
            }
            weatherDTO.setMarkerType(markerType);
            String stationFarsiName = weather.getWeatherStation().getFarsiName();
            stationFarsiName = (stationFarsiName != null ? stationFarsiName : "");
            String content = "<table class='weather-info-table' style='font-size: larger;font-weight:bold' border='1'>";
            content = content + "<tr><td colspan=2> ساعت و زمان تهیه گزارش : " + DateUtils.convertJulianToPersian(weather.getReportDate(), "HH:mm:SS dd-MM-YYYY") + "</td></tr>";
            content = content + "<tr><td> سرعت باد </td><td> ";
            if (weather.getWindSpeedInKnots() != null) {
                content = content + weather.getWindSpeedInKnots() + " نات " + "</td><tr>";
            } else {
                content = printNoDataExists(content);
            }
            content = content + "<tr><td> جهت باد </td><td> ";
            if (weather.getWindDirection() != null) {
                content = content + weather.getWindDirection() + " درجه " + "</td></tr>";
            } else {
                content = printNoDataExists(content);
            }
            content = content + "<tr><td> فشار هوا </td><td> ";
            if (weather.getPressure() != null) {
                content = content + weather.getPressureInHectoPascals() + " هکتوپاسکال " + "</td></tr>";
            } else {
                content = printNoDataExists(content);
            }
            content = content + "<tr><td> دمای هوا </td><td> ";
            if (weather.getTemperature() != null) {
                content = content + weather.getTemperature() + " درجه سانتیگراد " + "</td></tr>";
            } else {
                content = printNoDataExists(content);
            }
            content = content + "<tr><td> نقطه شبنم </td><td> ";
            if (weather.getDewPoint() != null) {
                content = content + weather.getDewPoint() + " درجه سانتیگراد " + "</td></tr>";
            } else {
                content = printNoDataExists(content);
            }
            content = content + "<tr><td> دید افقی </td><td> ";
            if (weather.getVisibility() != null) {
                content = content + weather.getVisibility() + " متر " + "</td></tr>";
            } else {
                content = printNoDataExists(content);
            }
            content = content + "<tr style='direction: ltr; text-align: left'><td colspan=2> ";
            if (weather.getOriginalContent() != null) {
                content = content + weather.getOriginalContent() + "</td></tr>";
            } else {
                content = printNoDataExists(content);
            }

            content = content + "</table>";
            weatherDTOList.add(
                    new WeatherDTO(
                            weather.getWeatherStation().getLongitude(),
                            weather.getWeatherStation().getLatitude(),
                            stationFarsiName + " - " + weather.getWeatherStation().getName(),
                            content, markerType,
                            5, 8, weather.isCumulonimbus(), weather.getWindSpeedInKnots()
                    ));
        }

        map.put("weatherList", weatherDTOList);

        List<Bulletin> bulletins = bulletinService.getCurrentForecasts();
        List<WeatherDTO> bulletinDTOs = new ArrayList<>();

        for (Bulletin bulletin : bulletins) {
            WeatherDTO bulletinDTO = new WeatherDTO();
            bulletinDTO.setLatitude(bulletin.getProvince().getLatitude());
            bulletinDTO.setLongitude(bulletin.getProvince().getLongitude());
            bulletinDTO.setZoomLevelMax(100);
            bulletinDTO.setZoomLevelMin(0);
            bulletinDTO.setMarkerType(bulletin.getIcon());

            String content = "<ul>";
            content = content + "<li> پیش بینی 24 ساعت آینده (" + DateUtils.convertJulianToPersian(bulletin.getForecastDate(), "dd-MM-YYYY") + ")</li>";
            content = content + "<li> حداقل دما : " + bulletin.getMinTemperature() + "<img src='/icons/downL.png'></li>";
            content = content + "<li> حداکثر دما : " + bulletin.getMaxTemperature() + " <img src='/icons/upL.png'></li>";
            content = content + "<li>" + bulletin.getPhenomena() + "</li>";
            content = content + "<li><a href='/bulletin/view?provinceId=" + bulletin.getProvince().getId() + "' >مشاهده پیش بینی چند روزه </a></li>";

            content = content + "</ul>";
            bulletinDTOs.add(
                    new WeatherDTO(
                            bulletin.getProvince().getLatitude(),
                            bulletin.getProvince().getLongitude(),
                            bulletin.getProvince().getName(),
                            content, bulletin.getIcon(),
                            5, 8, false, 0F
                    ));
        }
        map.put("forecasts", bulletinDTOs);
        List<String> icons = new ArrayList<>();
        File iconsDirectory = getIconsDirectory();
        if (iconsDirectory != null) {
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

    private String printNoDataExists(String content) {
        return content + " اطلاعات موجود نیست " + "</td><tr>";
    }


    private File getIconsDirectory() {
        URL url = this.getClass().getClassLoader().getResource("static/icons");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        } finally {
            return file;
        }
    }
}