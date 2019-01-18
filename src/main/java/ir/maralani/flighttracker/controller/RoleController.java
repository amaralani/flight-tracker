package ir.maralani.flighttracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/base-info/role")
public class RoleController {


    @RequestMapping(value = {"/grant", ""}, method = RequestMethod.GET)
    public String getUnits(ModelMap map, HttpSession session) {
//        map.put("units", unitService.getAll());
        map.put("successMessage", session.getAttribute("successMessage"));
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "grant-role";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUnit(@RequestParam(name = "title") String title,
                           @RequestParam(name = "code") String code,
                           @RequestParam(name = "type") Long type,
                           @RequestParam(name = "id", required = false) Long id,
                           @RequestParam(name = "isNew", required = false) Boolean isNew,
                           HttpSession session) {

        return "redirect:/base-info/role/grant";
    }

    @RequestMapping(value = "/type/save", method = RequestMethod.POST)
    public String saveUnitType(@RequestParam(name = "name") String name,
                               @RequestParam(name = "id", required = false) Long id,
                               @RequestParam(name = "isNew", required = false) Boolean isNew,
                               HttpSession session) {

        return "redirect:/base-info/role/grant";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(HttpSession session,
                         @RequestParam(name = "id") Long id) {
        return "redirect:/base-info/role/grant";
    }
}
