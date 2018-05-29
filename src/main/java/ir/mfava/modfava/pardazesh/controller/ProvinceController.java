package ir.mfava.modfava.pardazesh.controller;

import ir.mfava.modfava.pardazesh.model.Bulletin;
import ir.mfava.modfava.pardazesh.model.Province;
import ir.mfava.modfava.pardazesh.model.report.event.Event;
import ir.mfava.modfava.pardazesh.service.BulletinService;
import ir.mfava.modfava.pardazesh.service.ProvinceService;
import ir.mfava.modfava.pardazesh.service.report.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/base-info/province")
public class ProvinceController extends BaseController {

    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private BulletinService bulletinService;
    @Autowired
    private EventService eventService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String getProvinces(ModelMap map, HttpSession session) {
        map.put("provinces", provinceService.getAll());
        map.put("successMessage", session.getAttribute("successMessage"));
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "view-provinces";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProvince(@RequestParam(name = "name") String name,
                               @RequestParam(name = "latitude") String latitude,
                               @RequestParam(name = "longitude") String longitude,
                               @RequestParam(name = "id", required = false) Long id,
                               @RequestParam(name = "isNew", required = false) Boolean isNew,
                               HttpSession session,
                               HttpServletRequest request,
                               Authentication authentication) {
        Map<String, String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity", "Province");
        descriptionMap.put("name", name);
        descriptionMap.put("latitude", latitude);
        descriptionMap.put("longitude", longitude);
        descriptionMap.put("id", String.valueOf(id));
        descriptionMap.put("isNew", String.valueOf(isNew));

        Province province;
        Event.SubType subType;
        Event.Flag flag;
        if (isNew) {
            province = new Province();
            subType = Event.SubType.NEW_DATA;
        } else {
            province = provinceService.getById(id);
            subType = Event.SubType.EDIT_DATA;
        }
        province.setName(name);
        province.setLatitude(latitude);
        province.setLongitude(longitude);
        try {
            provinceService.save(province);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (EntityNotFoundException enfex) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, subType, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/province/";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(HttpSession session,
                         @RequestParam(name = "id") Long id,
                         HttpServletRequest request,
                         Authentication authentication) {
        Map<String, String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity", "Province");
        descriptionMap.put("id", String.valueOf(id));

        Province province = provinceService.getById(id);
        Bulletin bulletin = new Bulletin();
        bulletin.setProvince(province);
        if (bulletinService.exists(bulletin)) {
            session.setAttribute("errorMessage", "برای این شهرستان اطلاعات ثبت شده و امکان حذف آن وجود ندارد.");
            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.DELETE, Event.SubType.DELETE_FROM_DB, Event.Flag.FAILURE, descriptionMap, Event.Sensitivity.NOTIFICATION);
            return "redirect:/base-info/province/";
        }
        Event.Flag flag;
        try {
            provinceService.remove(province);
            session.setAttribute("successMessage", "حذف شهرستان با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.DELETE, Event.SubType.DELETE_FROM_DB, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/province/";
    }
}
