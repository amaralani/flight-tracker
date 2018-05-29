package ir.mfava.modfava.pardazesh.controller.report;

import ir.mfava.modfava.pardazesh.controller.BaseController;
import ir.mfava.modfava.pardazesh.model.DTO.AnalyticDTO;
import ir.mfava.modfava.pardazesh.model.DTO.GridDTO;
import ir.mfava.modfava.pardazesh.model.DTO.JSONMessage;
import ir.mfava.modfava.pardazesh.model.Weather;
import ir.mfava.modfava.pardazesh.model.report.event.Event;
import ir.mfava.modfava.pardazesh.service.PhenomenaService;
import ir.mfava.modfava.pardazesh.service.WeatherService;
import ir.mfava.modfava.pardazesh.service.WeatherStationService;
import ir.mfava.modfava.pardazesh.service.report.event.EventService;
import ir.mfava.modfava.pardazesh.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/report/")
public class ReportController extends BaseController {

    @Autowired
    private EventService eventService;
    @Autowired
    private WeatherStationService weatherStationService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private PhenomenaService phenomenaService;

    @RequestMapping(value = {"view", ""})
    public String showReportsPage(ModelMap map) {
        map.put("weatherStations", weatherStationService.getAll());
        map.put("phenomenas", phenomenaService.getAll());
        map.put("currentDate", DateUtils.convertJulianToPersian(new Date(), "yyyy/MM/dd"));
        return "report/reports";
    }

    @ResponseBody
    @RequestMapping("weather-report")
    public JSONMessage getWeatherReport(@RequestParam(name = "phenomenas", required = false) List<Long> phenomenas,
                                        @RequestParam(name = "station", required = false) Long stationId,
                                        @RequestParam(name = "startDate", required = false) String startDate,
                                        @RequestParam(name = "endDate", required = false) String endDate,
                                        @RequestParam(name = "type", required = false) String type,
                                        HttpServletRequest request,
                                        Authentication authentication) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("reportType","weather-report");
        descriptionMap.put("fileType",type);
        descriptionMap.put("phenomenas", StringUtils.join(phenomenas.toArray(),","));
        descriptionMap.put("stationId", String.valueOf(stationId));
        descriptionMap.put("startDate", startDate);
        descriptionMap.put("endDate", endDate);
        try {
            if (startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
                return JSONMessage.Error("لطفا بازه زمانی تهیه گزارش را مشخص فرمایید.");
            }
            Date realStartDate;
            Date realEndDate;
            try {
                realStartDate = DateUtils.convertPersianToJulian(startDate);
                if (realStartDate == null) throw new Exception("bad format");
                realEndDate = DateUtils.convertPersianToJulian(endDate);
                if (realEndDate == null) throw new Exception("bad format");
                realEndDate = DateUtils.addDays(realEndDate, 1);
            } catch (Exception e) {
                return JSONMessage.Error("خطا در خواندن تاریخ. لطفا فرمت تاریخ را بررسی و مجددا امتحان کنید.");
            }
            Weather.ReportType reportType = null;
            if ("METAR".equals(type)) {
                reportType = Weather.ReportType.METAR;
            }
            if ("TAFOR".equals(type)) {
                reportType = Weather.ReportType.TAFOR;
            }
            List<Weather> weathers = weatherService.searchWeather(reportType, realStartDate, realEndDate, phenomenas, stationId);

            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.REPORT, Event.SubType.TAKE_REPORT, Event.Flag.SUCCESS, descriptionMap, Event.Sensitivity.NOTIFICATION);
            List<GridDTO> gridDTOs = new ArrayList<>();
            for (Weather weather : weathers) {
                GridDTO gridDTO = new GridDTO();
                gridDTO.mapFromWeather(weather);
                gridDTOs.add(gridDTO);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("weathers", gridDTOs);
            return JSONMessage.Success(map);
        } catch (Exception e) {
            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.REPORT, Event.SubType.TAKE_REPORT, Event.Flag.FAILURE, descriptionMap, Event.Sensitivity.NOTIFICATION);
            return JSONMessage.Error("خطا در تهیه گزارش");
        }
    }

    @ResponseBody
    @RequestMapping("analytic-report")
    public JSONMessage getAnalyticReport(@RequestParam(name = "station", required = false) Long stationId,
                                         @RequestParam(name = "startDate", required = false) String startDate,
                                         @RequestParam(name = "endDate", required = false) String endDate,
                                         HttpServletRequest request,
                                         Authentication authentication) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("reportType","analytic-report");
        descriptionMap.put("stationId", String.valueOf(stationId));
        descriptionMap.put("startDate", startDate);
        descriptionMap.put("endDate", endDate);
        try {
            if (startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
                return JSONMessage.Error("لطفا بازه زمانی تهیه گزارش را مشخص فرمایید.");
            }
            Date realStartDate;
            Date realEndDate;
            try {
                realStartDate = DateUtils.convertPersianToJulian(startDate);
                if (realStartDate == null) throw new Exception("bad format");
                realEndDate = DateUtils.convertPersianToJulian(endDate);
                if (realEndDate == null) throw new Exception("bad format");
                realEndDate = DateUtils.addDays(realEndDate, 1);
            } catch (Exception e) {
                return JSONMessage.Error("خطا در خواندن تاریخ. لطفا فرمت تاریخ را بررسی و مجددا امتحان کنید.");
            }

            List<AnalyticDTO> analyticDTOs = weatherService.getAnalyticReport(stationId, realStartDate, realEndDate);
            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.REPORT, Event.SubType.TAKE_REPORT, Event.Flag.SUCCESS, descriptionMap, Event.Sensitivity.NOTIFICATION);
            Map<String, Object> map = new HashMap<>();
            map.put("analyticDTOs", analyticDTOs);
            return JSONMessage.Success(map);
        } catch (Exception e) {
            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.REPORT, Event.SubType.TAKE_REPORT, Event.Flag.FAILURE, descriptionMap, Event.Sensitivity.NOTIFICATION);
            return JSONMessage.Error("خطا در تهیه گزارش");
        }
    }

    @ResponseBody
    @RequestMapping("type-count-report")
    public JSONMessage getTypeCountReport(@RequestParam(name = "station", required = false) Long stationId,
                                          @RequestParam(name = "startDate", required = false) String startDate,
                                          @RequestParam(name = "endDate", required = false) String endDate,
                                          HttpServletRequest request,
                                          Authentication authentication) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("reportType","type-count-report");
        descriptionMap.put("stationId", String.valueOf(stationId));
        descriptionMap.put("startDate", startDate);
        descriptionMap.put("endDate", endDate);
        try {
            if (startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
                return JSONMessage.Error("لطفا بازه زمانی تهیه گزارش را مشخص فرمایید.");
            }
            Date realStartDate;
            Date realEndDate;
            try {
                realStartDate = DateUtils.convertPersianToJulian(startDate);
                if (realStartDate == null) throw new Exception("bad format");
                realEndDate = DateUtils.convertPersianToJulian(endDate);
                if (realEndDate == null) throw new Exception("bad format");
                realEndDate = DateUtils.addDays(realEndDate, 1);
            } catch (Exception e) {
                return JSONMessage.Error("خطا در خواندن تاریخ. لطفا فرمت تاریخ را بررسی و مجددا امتحان کنید.");
            }

            List<AnalyticDTO> analyticDTOs = weatherService.getTypeCountReport(stationId, realStartDate, realEndDate);
            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.REPORT, Event.SubType.TAKE_REPORT, Event.Flag.SUCCESS, descriptionMap, Event.Sensitivity.NOTIFICATION);
            Map<String, Object> map = new HashMap<>();
            map.put("analyticDTOs", analyticDTOs);
            return JSONMessage.Success(map);
        } catch (Exception e) {
            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.REPORT, Event.SubType.TAKE_REPORT, Event.Flag.FAILURE, descriptionMap, Event.Sensitivity.NOTIFICATION);
            return JSONMessage.Error("خطا در تهیه گزارش");
        }
    }

    @ResponseBody
    @RequestMapping("send-count-report")
    public JSONMessage getSendCountReport(@RequestParam(name = "stationIds", required = false) List<Long> stationIds,
                                          @RequestParam(name = "startDate", required = false) String startDate,
                                          @RequestParam(name = "endDate", required = false) String endDate,
                                          HttpServletRequest request,
                                          Authentication authentication) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("reportType","type-count-report");
        descriptionMap.put("stationId", StringUtils.join(stationIds.toArray(),","));
        descriptionMap.put("startDate", startDate);
        descriptionMap.put("endDate", endDate);
        try {
            if (startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
                return JSONMessage.Error("لطفا بازه زمانی تهیه گزارش را مشخص فرمایید.");
            }
            Date realStartDate;
            Date realEndDate;
            try {
                realStartDate = DateUtils.convertPersianToJulian(startDate);
                if (realStartDate == null) throw new Exception("bad format");
                realEndDate = DateUtils.convertPersianToJulian(endDate);
                if (realEndDate == null) throw new Exception("bad format");
                realEndDate = DateUtils.addDays(realEndDate, 1);
            } catch (Exception e) {
                return JSONMessage.Error("خطا در خواندن تاریخ. لطفا فرمت تاریخ را بررسی و مجددا امتحان کنید.");
            }

            List<AnalyticDTO> analyticDTOs = weatherService.getSendCountReport(stationIds, realStartDate, realEndDate);
            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.REPORT, Event.SubType.TAKE_REPORT, Event.Flag.SUCCESS, descriptionMap, Event.Sensitivity.NOTIFICATION);
            Map<String, Object> map = new HashMap<>();
            map.put("analyticDTOs", analyticDTOs);
            return JSONMessage.Success(map);
        } catch (Exception e) {
            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.REPORT, Event.SubType.TAKE_REPORT, Event.Flag.FAILURE, descriptionMap, Event.Sensitivity.NOTIFICATION);
            return JSONMessage.Error("خطا در تهیه گزارش");
        }
    }

}
