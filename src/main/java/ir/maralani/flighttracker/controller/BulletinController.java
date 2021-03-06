package ir.maralani.flighttracker.controller;

import ir.maralani.flighttracker.model.Bulletin;
import ir.maralani.flighttracker.model.DTO.JSONMessage;
import ir.maralani.flighttracker.model.Province;
import ir.maralani.flighttracker.model.report.event.Event;
import ir.maralani.flighttracker.service.BulletinService;
import ir.maralani.flighttracker.service.ProvinceService;
import ir.maralani.flighttracker.service.report.event.EventService;
import ir.maralani.flighttracker.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/bulletin")
public class BulletinController extends BaseController {

    @Autowired
    private BulletinService bulletinService;
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String showForecastsPage(@RequestParam(name = "provinceId", required = false) Long provinceId,
                                    ModelMap map, HttpSession session) {
        map.put("provinces", provinceService.getAll());
        map.put("provinceId", provinceId);
        List<Bulletin> bulletins = bulletinService.getListByProvinceAndForecastDate(provinceId, new Date());

        prepareBulletins(bulletins);
        map.put("bulletins", bulletins);
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "province-bulletin";
    }

    private void prepareBulletins(List<Bulletin> bulletins) {
        for (Bulletin currentBulletin : bulletins) {
            currentBulletin.setForecastDateString(DateUtils.convertJulianToPersianForUi(currentBulletin.getForecastDate()));
            long hoursDifference = (DateUtils.differenceInDays(new Date(), currentBulletin.getForecastDate()) + 1) * 24; // we do not want 0 hours
            currentBulletin.setTitle(hoursDifference + " ساعت آینده");
            currentBulletin.setProvinceName(currentBulletin.getProvince().getName());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/get/", method = RequestMethod.GET)
    public JSONMessage getProvinceForecasts(@RequestParam(name = "provinceId") Long provinceId) {
        List<Bulletin> bulletins = bulletinService.getListByProvinceAndForecastDate(provinceId, new Date());
        prepareBulletins(bulletins);
        Map<String, Object> map = new HashMap<>();
        map.put("bulletins", bulletins);
        return JSONMessage.Success("success", map);
    }

    @RequestMapping(value = "/save")
    public String saveBulletin(@RequestParam(name = "provinceId") Long provinceId,
                               @RequestParam(name = "minTemperature") Float minTemperature,
                               @RequestParam(name = "maxTemperature") Float maxTemperature,
                               @RequestParam(name = "phenomena") String phenomena,
                               @RequestParam(name = "forecastDate") Long forecastDate,
                               @RequestParam(name = "icon") String icon,
                               HttpSession session,
                               HttpServletRequest request,
                               Authentication authentication) {
        Map<String, String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity", "Bulletin");
        descriptionMap.put("provinceId", String.valueOf(provinceId));
        descriptionMap.put("minTemperature", String.valueOf(minTemperature));
        descriptionMap.put("maxTemperature", String.valueOf(maxTemperature));
        descriptionMap.put("phenomena", phenomena);
        descriptionMap.put("forecastDate", String.valueOf(forecastDate));
        descriptionMap.put("icon", icon);

        Bulletin bulletin = new Bulletin();
        Province province = provinceService.getById(provinceId);
        bulletin.setProvince(province);
        bulletin.setMaxTemperature(maxTemperature);
        bulletin.setMinTemperature(minTemperature);
        bulletin.setPhenomena(phenomena);
        bulletin.setIcon(icon);
        Long currentDate = new Date().getTime();
        Long daysToAdd = forecastDate * 24 * 60 * 60 * 1000;
        bulletin.setForecastDate(new Date(currentDate + daysToAdd));
        Event.Flag flag;
        try {
            bulletinService.save(bulletin);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception e) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
            flag = Event.Flag.FAILURE;
        }

        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, Event.SubType.NEW_DATA, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/bulletin/view?provinceId=" + provinceId;
    }

    @RequestMapping(value = "/remove")
    public String saveBulletin(@RequestParam(name = "bulletinId") Long bulletinId,
                               HttpSession session,
                               HttpServletRequest request,
                               Authentication authentication) {
        Map<String, String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity", "Bulletin");
        descriptionMap.put("bulletinId", String.valueOf(bulletinId));
        Bulletin bulletin = bulletinService.getById(bulletinId);
        Event.Flag flag;
        try {
            bulletinService.remove(bulletin);
            session.setAttribute("successMessage", "حذف پیش بینی با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.DELETE, Event.SubType.DELETE_FROM_DB, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/bulletin/view?provinceId=" + bulletin.getProvince().getId();
    }
}
