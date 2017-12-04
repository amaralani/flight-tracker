package ir.mfava.modfava.pardazesh.controller;

import ir.mfava.modfava.pardazesh.model.ContentText;
import ir.mfava.modfava.pardazesh.service.ContentTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/base-info/security/config")
public class SecurityConfigController {

    @Autowired
    private ContentTextService contentTextService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String getUnits(ModelMap map, HttpSession session) {


        // Add security things

        ContentText contentText = contentTextService.getByTextContext(ContentText.TextContext.BANNER);
        map.put("contentText", contentText);

        map.put("successMessage", session.getAttribute("successMessage"));
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "security-configs";
    }

    @RequestMapping(value = "/saveBanner", method = RequestMethod.POST)
    public String saveUnit(@RequestParam(name = "text") String text,
                           @RequestParam(name = "id", required = false) Long id,
                           HttpSession session) {
        try {
            ContentText contentText;
            if (id == null) {
                contentText = new ContentText();
            } else {
                contentText = contentTextService.getById(id);
            }
            contentText.setText(text);
            contentText.setTextContext(ContentText.TextContext.BANNER);
            contentTextService.save(contentText);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
        }catch (Exception ex){
            ex.printStackTrace();
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
        }

        return "redirect:/base-info/security/config/";
    }

    @RequestMapping(value = "/type/save", method = RequestMethod.POST)
    public String saveUnitType(@RequestParam(name = "name") String name,
                               @RequestParam(name = "id", required = false) Long id,
                               @RequestParam(name = "isNew", required = false) Boolean isNew,
                               HttpSession session) {

        return "redirect:/base-info/security/config";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(HttpSession session,
                         @RequestParam(name = "id") Long id) {
        return "redirect:/base-info/security/config";
    }
}
