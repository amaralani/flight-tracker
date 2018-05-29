package ir.mfava.modfava.pardazesh.controller;

import ir.mfava.modfava.pardazesh.model.DTO.JSONMessage;
import ir.mfava.modfava.pardazesh.model.Defactor;
import ir.mfava.modfava.pardazesh.model.report.event.Event;
import ir.mfava.modfava.pardazesh.service.DefactorService;
import ir.mfava.modfava.pardazesh.service.ProvinceService;
import ir.mfava.modfava.pardazesh.service.report.event.EventService;
import ir.mfava.modfava.pardazesh.util.DateUtils;
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
import java.util.Map;

@Controller
@RequestMapping(value = "/defactor")
public class DefactorController extends BaseController {

    @Autowired
    private DefactorService defactorService;
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String showDefactors(ModelMap map, HttpSession session) {
        map.put("defactors", defactorService.getAll());
        map.put("provinces", provinceService.getAll());

        map.put("currentDate", DateUtils.convertJulianToPersian(new Date(), "yyyy/MM/dd"));
        map.put("successMessage", session.getAttribute("successMessage"));
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "defactors";
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public JSONMessage getDefactor(@RequestParam(name = "defactorId") Long defactorId) {
        Defactor defactor = defactorService.getById(defactorId);
        Map<String, Object> map = new HashMap<>();
        map.put("defactor", defactor);
        return JSONMessage.Success("success", map);
    }

    @RequestMapping(value = "/save")
    public String saveDefactor(@RequestParam(name = "provinceId") Long provinceId,
                               @RequestParam(name = "date") String date,
                               @RequestParam(name = "hoursFrom") Integer hoursFrom,
                               @RequestParam(name = "hoursTo") Integer hoursTo,
                               @RequestParam(name = "height300") Integer height300,
                               @RequestParam(name = "temperature300") Integer temperature300,
                               @RequestParam(name = "windSpeed300") Integer windSpeed300,
                               @RequestParam(name = "windDirection300") Integer windDirection300,
                               @RequestParam(name = "height500") Integer height500,
                               @RequestParam(name = "temperature500") Integer temperature500,
                               @RequestParam(name = "windSpeed500") Integer windSpeed500,
                               @RequestParam(name = "windDirection500") Integer windDirection500,
                               @RequestParam(name = "height700") Integer height700,
                               @RequestParam(name = "temperature700") Integer temperature700,
                               @RequestParam(name = "windSpeed700") Integer windSpeed700,
                               @RequestParam(name = "windDirection700") Integer windDirection700,
                               @RequestParam(name = "id", required = false) Long id,
                               @RequestParam(name = "isNew", required = false) Boolean isNew,
                               HttpSession session,
                               HttpServletRequest request,
                               Authentication authentication) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity","Defactor");
        descriptionMap.put("provinceId", String.valueOf(provinceId));
        descriptionMap.put("date", date);
        descriptionMap.put("hoursFrom", String.valueOf(hoursFrom));
        descriptionMap.put("hoursTo", String.valueOf(hoursTo));
        descriptionMap.put("height300", String.valueOf(height300));
        descriptionMap.put("temperature300", String.valueOf(temperature300));
        descriptionMap.put("windSpeed300", String.valueOf(windSpeed300));
        descriptionMap.put("windDirection300", String.valueOf(windDirection300));
        descriptionMap.put("height500", String.valueOf(height500));
        descriptionMap.put("temperature500", String.valueOf(temperature500));
        descriptionMap.put("windSpeed500", String.valueOf(windSpeed500));
        descriptionMap.put("windDirection500", String.valueOf(windDirection500));
        descriptionMap.put("height700", String.valueOf(height700));
        descriptionMap.put("temperature700", String.valueOf(temperature700));
        descriptionMap.put("windSpeed700", String.valueOf(windSpeed700));
        descriptionMap.put("windDirection700", String.valueOf(windDirection700));
        descriptionMap.put("id", String.valueOf(id));
        descriptionMap.put("isNew", String.valueOf(isNew));

        Defactor defactor;
        if (isNew) {
            defactor = new Defactor();
        } else {
            defactor = defactorService.getById(id);
        }
        Date realDate;
        Event.Flag flag;
        if (date != null && !date.isEmpty()) {
            try {
                realDate = DateUtils.convertPersianToJulian(date);
                if (realDate == null) throw new Exception("bad format");

                defactor.setDate(realDate);
            } catch (Exception e) {
                session.setAttribute("errorMessage", "خطا در خواندن تاریخ. لطفا فرمت تاریخ را بررسی و مجددا امتحان کنید.");
                return "redirect:/defactor/view";
            }
        }

        defactor.setProvince(provinceService.getById(provinceId));

        defactor.setPersianDate(date);
        defactor.setHoursFrom(hoursFrom);
        defactor.setHoursTo(hoursTo);
        defactor.setHeight300(height300);
        defactor.setTemperature300(temperature300);
        defactor.setWindSpeed300(windSpeed300);
        defactor.setWindDirection300(windDirection300);
        defactor.setHeight500(height500);
        defactor.setTemperature500(temperature500);
        defactor.setWindSpeed500(windSpeed500);
        defactor.setWindDirection500(windDirection500);
        defactor.setHeight700(height700);
        defactor.setTemperature700(temperature700);
        defactor.setWindSpeed700(windSpeed700);
        defactor.setWindDirection700(windDirection700);
        defactor.setVisible(true);
        try {
            defactorService.save(defactor);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception ex) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, isNew ? Event.SubType.NEW_DATA : Event.SubType.EDIT_DATA, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/defactor/view";
    }

    @RequestMapping(value = "/remove")
    public String removeDefactor(@RequestParam(name = "defactorId") Long defactorId,
                                 HttpSession session,
                                 HttpServletRequest request,
                                 Authentication authentication) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity","Defactor");
        descriptionMap.put("defactorId", String.valueOf(defactorId));

        Defactor defactor = defactorService.getById(defactorId);
        Event.Flag flag;
        try {
            defactorService.remove(defactor);
            session.setAttribute("successMessage", "حذف اطلاعات با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.DELETE, Event.SubType.DELETE_FROM_DB, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/defactor/view";
    }

    @RequestMapping(value = "/hide")
    public String hideDefactor(@RequestParam(name = "defactorId") Long defactorId,
                               HttpSession session,
                               HttpServletRequest request,
                               Authentication authentication) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity","Defactor");
        descriptionMap.put("defactorId", String.valueOf(defactorId));
        Defactor defactor = defactorService.getById(defactorId);
        defactor.setVisible(false);
        Event.Flag flag;
        try {
            defactorService.save(defactor);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception ex) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, Event.SubType.EDIT_DATA, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/defactor/view";
    }
}
