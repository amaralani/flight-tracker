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

import java.util.ArrayList;
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
            String content = "<ul>";
            content = content + "<li> ساعت و زمان تهیه گزارش : " + DateUtils.convertJulianToPersian(weather.getReportDate(), "HH:mm:SS dd-MM-YYYY") + "</li>";
            content = content + "<li> سرعت باد : ";
            if (weather.getWindSpeedInKnots() != null) {
                content = content + weather.getWindSpeedInKnots() + " نات " + "</li>";
            } else {
                content = printNoDataExists(content);
            }
            content = content + "<li> جهت باد : ";
            if (weather.getWindDirection() != null) {
                content = content + weather.getWindDirection() + " درجه " + "</li>";
            } else {
                content = printNoDataExists(content);
            }
            content = content + "<li> فشار هوا : ";
            if (weather.getPressure() != null) {
                content = content + weather.getPressureInHectoPascals() + " هکتوپاسکال " + "</li>";
            } else {
                content = printNoDataExists(content);
            }
            content = content + "<li> دمای هوا : ";
            if (weather.getTemperature() != null) {
                content = content + weather.getTemperature() + " درجه سانتیگراد " + "</li>";
            } else {
                content = printNoDataExists(content);
            }
            content = content + "<li> نقطه شبنم : ";
            if (weather.getDewPoint() != null) {
                content = content + weather.getDewPoint() + " درجه سانتیگراد " + "</li>";
            } else {
                content = printNoDataExists(content);
            }
            content = content + "<li> دید افقی : ";
            if (weather.getVisibility() != null) {
                content = content + weather.getVisibility() + " متر " + "</li>";
            } else {
                content = printNoDataExists(content);
            }
            content = content + "<li style='direction: ltr; text-align: left'> ";
            if (weather.getOriginalContent() != null) {
                content = content + weather.getOriginalContent() + "</li>";
            } else {
                content = printNoDataExists(content);
            }

            content = content + "</ul>";
            weatherDTOList.add(
                    new WeatherDTO(
                            weather.getWeatherStation().getLongitude(),
                            weather.getWeatherStation().getLatitude(),
                            stationFarsiName + " - " + weather.getWeatherStation().getName(),
                            content, markerType,
                            5, 8
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
            content = content + "<li><a href='/bulletin/view?provinceId="+ bulletin.getProvince().getId() +"' >مشاهده پیش بینی چند روزه </a></li>";

            content = content + "</ul>";
            bulletinDTOs.add(
                    new WeatherDTO(
                            bulletin.getProvince().getLatitude(),
                            bulletin.getProvince().getLongitude(),
                            bulletin.getProvince().getName(),
                            content, bulletin.getIcon(),
                            5, 8
                    ));
        }
        map.put("forecasts", bulletinDTOs);

        StringBuffer url = request.getRequestURL();
        String uri = request.getRequestURI();
        String host = url.substring(0, url.indexOf(uri) -5); //result
        map.put("host", host);
        return "map";
    }

    private String printNoDataExists(String content) {
        return content + " اطلاعات موجود نیست " + "</li>";
    }
}