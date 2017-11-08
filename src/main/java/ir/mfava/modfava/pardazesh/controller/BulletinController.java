package ir.mfava.modfava.pardazesh.controller;

import ir.mfava.modfava.pardazesh.model.Bulletin;
import ir.mfava.modfava.pardazesh.model.DTO.JSONMessage;
import ir.mfava.modfava.pardazesh.model.Province;
import ir.mfava.modfava.pardazesh.service.BulletinService;
import ir.mfava.modfava.pardazesh.service.ProvinceService;
import ir.mfava.modfava.pardazesh.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/bulletin")
public class BulletinController {

    @Autowired
    private BulletinService bulletinService;
    @Autowired
    private ProvinceService provinceService;

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String showForecastsPage(@RequestParam(name = "provinceId", required = false) Long provinceId,
                                    ModelMap map, HttpSession session) {
        map.put("provinces", provinceService.getAll());
        map.put("provinceId", provinceId);
        if (provinceId != null) {
            List<Bulletin> bulletins = bulletinService.getListByProvinceAndForecastDate(provinceId, new Date());
            for (int i = 0; i < bulletins.size(); i++) {
                Bulletin currentBulletin = bulletins.get(i);
                currentBulletin.setForecastDateString(DateUtils.convertJulianToPersianForUi(currentBulletin.getForecastDate()));
                currentBulletin.setTitle(((i + 1) * 24) + " ساعت آینده");
            }
            map.put("bulletins", bulletins);
        }
        map.put("successMessage", session.getAttribute("successMessage"));
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "province-bulletin";
    }

    @ResponseBody
    @RequestMapping(value = "/get/", method = RequestMethod.GET)
    public JSONMessage getProvinceForecasts(@RequestParam(name = "provinceId") Long provinceId) {
        List<Bulletin> bulletins = bulletinService.getListByProvinceAndForecastDate(provinceId, new Date());
        for (int i = 0; i < bulletins.size(); i++) {
            Bulletin currentBulletin = bulletins.get(i);
            currentBulletin.setForecastDateString(DateUtils.convertJulianToPersianForUi(currentBulletin.getForecastDate()));
            long hoursDifference = (DateUtils.differenceInDays(new Date(), currentBulletin.getForecastDate()) + 1) * 24; // we do not want 0 hours
            currentBulletin.setTitle(hoursDifference + " ساعت آینده");
        }
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
                               HttpSession session) {
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
        try {
            bulletinService.save(bulletin);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
        }
        return "redirect:/bulletin/view?provinceId=" + provinceId;
    }

    @RequestMapping(value = "/remove")
    public String saveBulletin(@RequestParam(name = "bulletinId") Long bulletinId,
                               HttpSession session) {
        Bulletin bulletin = bulletinService.getById(bulletinId);
        try {
            bulletinService.remove(bulletin);
            session.setAttribute("successMessage", "حذف اطلاعات با موفقیت انجام شد.");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
        }
        return "redirect:/bulletin/view?provinceId=" + bulletin.getProvince().getId();
    }
}
