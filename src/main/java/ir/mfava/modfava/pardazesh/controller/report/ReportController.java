package ir.mfava.modfava.pardazesh.controller.report;

import ir.mfava.modfava.pardazesh.controller.BaseController;
import ir.mfava.modfava.pardazesh.model.DTO.AnalyticDTO;
import ir.mfava.modfava.pardazesh.model.DTO.GridDTO;
import ir.mfava.modfava.pardazesh.model.DTO.JSONMessage;
import ir.mfava.modfava.pardazesh.model.Weather;
import ir.mfava.modfava.pardazesh.service.*;
import ir.mfava.modfava.pardazesh.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping(value = "/report/")
public class ReportController extends BaseController {

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
                                        @RequestParam(name = "type", required = false) String type) {

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
        List<GridDTO> gridDTOs = new ArrayList<>();
        for (Weather weather : weathers) {
            GridDTO gridDTO = new GridDTO();
            gridDTO.mapFromWeather(weather);
            gridDTOs.add(gridDTO);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("weathers", gridDTOs);
        return JSONMessage.Success(map);
    }

    @ResponseBody
    @RequestMapping("analytic-report")
    public JSONMessage getAnalyticReport(@RequestParam(name = "station", required = false) Long stationId,
                                         @RequestParam(name = "startDate", required = false) String startDate,
                                         @RequestParam(name = "endDate", required = false) String endDate) {

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
        Map<String, Object> map = new HashMap<>();
        map.put("analyticDTOs", analyticDTOs);
        return JSONMessage.Success(map);
    }

    @ResponseBody
    @RequestMapping("type-count-report")
    public JSONMessage getTypeCountReport(@RequestParam(name = "station", required = false) Long stationId,
                                          @RequestParam(name = "startDate", required = false) String startDate,
                                          @RequestParam(name = "endDate", required = false) String endDate) {

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
        Map<String, Object> map = new HashMap<>();
        map.put("analyticDTOs", analyticDTOs);
        return JSONMessage.Success(map);
    }

    @ResponseBody
    @RequestMapping("send-count-report")
    public JSONMessage getSendCountReport(@RequestParam(name = "stationIds", required = false) List<Long> stationIds,
                                          @RequestParam(name = "startDate", required = false) String startDate,
                                          @RequestParam(name = "endDate", required = false) String endDate) {

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
        Map<String, Object> map = new HashMap<>();
        map.put("analyticDTOs", analyticDTOs);
        return JSONMessage.Success(map);
    }

}
