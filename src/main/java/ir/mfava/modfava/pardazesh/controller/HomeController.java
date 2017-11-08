package ir.mfava.modfava.pardazesh.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Drago
 * @since 1.0
 */
@Controller
public class HomeController {

    @RequestMapping(value = {"/horse"}, method = RequestMethod.GET)
    @ResponseBody
    public String goHome() {
        return "welcome";
    }

}