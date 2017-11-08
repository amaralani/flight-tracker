//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ir.mfava.modfava.pardazesh.controller;

import ir.mfava.modfava.pardazesh.model.Bulletin;
import ir.mfava.modfava.pardazesh.model.Province;
import ir.mfava.modfava.pardazesh.service.BulletinService;
import ir.mfava.modfava.pardazesh.service.ProvinceService;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/base-info/province"})
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private BulletinService bulletinService;

    public ProvinceController() {
    }

    @RequestMapping(
            value = {"/", ""},
            method = {RequestMethod.GET}
    )
    public String getProvinces(ModelMap map, HttpSession session) {
        map.put("provinces", this.provinceService.getAll());
        map.put("successMessage", session.getAttribute("successMessage"));
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "view-provinces";
    }

    @RequestMapping(
            value = {"/save"},
            method = {RequestMethod.POST}
    )
    public String savePerson(@RequestParam(
            name = "name"
    ) String name, @RequestParam(
            name = "latitude"
    ) String latitude, @RequestParam(
            name = "longitude"
    ) String longitude, @RequestParam(
            name = "id",
            required = false
    ) Long id, @RequestParam(
            name = "isNew",
            required = false
    ) Boolean isNew, HttpSession session) {
        Province province;
        if(isNew.booleanValue()) {
            province = new Province();
        } else {
            province = (Province)this.provinceService.getById(id);
        }

        province.setName(name);
        province.setLatitude(latitude);
        province.setLongitude(longitude);

        try {
            this.provinceService.save(province);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
        } catch (EntityNotFoundException var9) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
        }

        return "redirect:/base-info/province/";
    }

    @RequestMapping(
            value = {"/remove"},
            method = {RequestMethod.POST}
    )
    public String remove(HttpSession session, @RequestParam(
            name = "id"
    ) Long id) {
        Province province = (Province)this.provinceService.getById(id);
        Bulletin bulletin = new Bulletin();
        bulletin.setProvince(province);
        if(this.bulletinService.exists(bulletin)) {
            session.setAttribute("errorMessage", "برای این شهرستان اطلاعات ثبت شده و امکان حذف آن وجود ندارد.");
            return "redirect:/base-info/province/";
        } else {
            try {
                this.provinceService.remove(province);
                session.setAttribute("successMessage", "حذف شهرستان با موفقیت انجام شد.");
                return "redirect:/base-info/province/";
            } catch (Exception var6) {
                var6.printStackTrace();
                session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
                return "redirect:/base-info/province/";
            }
        }
    }
}
