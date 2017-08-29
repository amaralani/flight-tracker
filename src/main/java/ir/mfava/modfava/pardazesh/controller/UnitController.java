package ir.mfava.modfava.pardazesh.controller;

import ir.mfava.modfava.pardazesh.model.Unit;
import ir.mfava.modfava.pardazesh.model.UnitType;
import ir.mfava.modfava.pardazesh.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/base-info/unit")
public class UnitController {

    @Autowired
    private UnitService unitService;

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
                             HttpSession session) {
        Unit unit;
        if (isNew) {
            unit = new Unit();
        } else {
            unit = unitService.getById(String.valueOf(id));
        }
        unit.setTitle(title);
        unit.setCode(code);
        unit.setType(unitService.getUnitTypeById(type));
        try {
            unitService.save(unit);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
        } catch (EntityNotFoundException enfex) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
        }
        return "redirect:/base-info/unit/";
    }

    @RequestMapping(value = "/type/save", method = RequestMethod.POST)
    public String saveUnitType(@RequestParam(name = "name") String name,
                             @RequestParam(name = "id", required = false) Long id,
                             @RequestParam(name = "isNew", required = false) Boolean isNew,
                             HttpSession session) {
        UnitType unitType;
        if (isNew) {
            unitType = new UnitType();
        } else {
            unitType = unitService.getUnitTypeById(id);
        }
        unitType.setName(name);
        try {
            unitService.save(unitType);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
        } catch (EntityNotFoundException enfex) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
        }
        return "redirect:/base-info/unit/";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(HttpSession session,
                         @RequestParam(name = "id") Long id) {
        Unit unit = unitService.getById(String.valueOf(id));
        try {
            unitService.remove(unit);
            session.setAttribute("successMessage", "حذف یگان با موفقیت انجام شد.");
            return "redirect:/base-info/unit/";
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
            return "redirect:/base-info/unit/";
        }
    }
}
