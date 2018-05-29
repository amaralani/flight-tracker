package ir.mfava.modfava.pardazesh.controller;

import ir.mfava.modfava.pardazesh.model.Unit;
import ir.mfava.modfava.pardazesh.model.UnitType;
import ir.mfava.modfava.pardazesh.model.report.event.Event;
import ir.mfava.modfava.pardazesh.service.UnitService;
import ir.mfava.modfava.pardazesh.service.report.event.EventService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/base-info/unit")
public class UnitController extends BaseController{

    @Autowired
    private UnitService unitService;
    @Autowired
    private EventService eventService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String getUnits(ModelMap map, HttpSession session) {
        map.put("units", unitService.getAll());
        map.put("unitTypes", unitService.getAllUnitTypes());
        map.put("successMessage", session.getAttribute("successMessage"));
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "units";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUnit(@RequestParam(name = "title") String title,
                           @RequestParam(name = "code") String code,
                           @RequestParam(name = "type") Long type,
                           @RequestParam(name = "id", required = false) Long id,
                           @RequestParam(name = "isNew", required = false) Boolean isNew,
                           HttpSession session,
                           HttpServletRequest request,
                           Authentication authentication) {
        Map<String, String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity", "Unit");
        descriptionMap.put("title", title);
        descriptionMap.put("code", code);
        descriptionMap.put("type", String.valueOf(type));
        descriptionMap.put("id", String.valueOf(id));
        descriptionMap.put("isNew", String.valueOf(isNew));

        Unit unit;
        Event.SubType subType;
        Event.Flag flag;
        if (isNew) {
            unit = new Unit();
            subType = Event.SubType.NEW_DATA;
        } else {
            unit = unitService.getById(id);
            subType = Event.SubType.EDIT_DATA;
        }
        unit.setTitle(title);
        unit.setCode(code);
        unit.setType(unitService.getUnitTypeById(type));
        try {
            unitService.save(unit);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (EntityNotFoundException enfex) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(),request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, subType, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/unit/";
    }

    @RequestMapping(value = "/type/save", method = RequestMethod.POST)
    public String saveUnitType(@RequestParam(name = "name") String name,
                               @RequestParam(name = "id", required = false) Long id,
                               @RequestParam(name = "isNew", required = false) Boolean isNew,
                               HttpSession session,
                               HttpServletRequest request,
                               Authentication authentication) {
        Map<String, String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity", "Unit");
        descriptionMap.put("name", name);
        descriptionMap.put("id", String.valueOf(id));
        descriptionMap.put("isNew", String.valueOf(isNew));

        UnitType unitType;
        Event.SubType subType;
        Event.Flag flag;
        if (isNew) {
            unitType = new UnitType();
            subType = Event.SubType.NEW_DATA;
        } else {
            unitType = unitService.getUnitTypeById(id);
            subType = Event.SubType.EDIT_DATA;
        }
        unitType.setName(name);
        try {
            unitService.save(unitType);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (EntityNotFoundException enfex) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(),request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, subType, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/unit/";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(HttpSession session,
                         @RequestParam(name = "id") Long id,
                         HttpServletRequest request,
                         Authentication authentication) {
        Map<String, String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity", "Unit");
        descriptionMap.put("id", String.valueOf(id));

        Unit unit = unitService.getById(id);
        Event.Flag flag;
        try {
            unitService.remove(unit);
            session.setAttribute("successMessage", "حذف یگان با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(),request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.DELETE, Event.SubType.DELETE_FROM_DB, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/unit/";
    }
}
