package ir.mfava.modfava.pardazesh.controller;


import ir.mfava.modfava.pardazesh.model.User;
import ir.mfava.modfava.pardazesh.security.SecurityService;
import ir.mfava.modfava.pardazesh.service.UserService;
import ir.mfava.modfava.pardazesh.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = {"/base-info/user"}, method = RequestMethod.GET)
    public String getAllUsers(ModelMap map, HttpSession session) {
        map.put("users", userService.getAll());
        map.put("successMessage", session.getAttribute("successMessage"));
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "view-users";
    }

    @RequestMapping(value = {"/base-info/user/save"}, method = RequestMethod.POST)
    public String saveUser(@RequestParam(name = "username") String username,
                           @RequestParam(name = "firstName") String firstName,
                           @RequestParam(name = "lastName") String lastName,
                           @RequestParam(name = "nationalCode") String nationalCode,
                           @RequestParam(name = "personNumber") String personNumber,
                           @RequestParam(name = "enabled") Boolean enabled,
                           @RequestParam(name = "password", required = false) String newPassword,
                           @RequestParam(name = "re-password", required = false) String newPasswordRepeat,
                           @RequestParam(name = "isNew", required = false) Boolean isNew,
                           HttpSession session) {
        User user;
        if (isNew) {
            user = new User();
            user.setUsername(username);
            user.setCreateDate(new Date());
        } else {
            user = userService.findByUsername(username);
        }
        user.setEnabled(enabled);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setNationalCode(nationalCode);
        user.setPersonNumber(personNumber);
        if (newPassword != null && !newPassword.isEmpty()) {
            if (newPasswordRepeat != null && newPassword.equals(newPasswordRepeat)) {
                if (isNew) {
                    user.setPassword(newPassword);
                } else {
                    user.setPassword(bCryptPasswordEncoder.encode(newPassword));
                }
            } else {
                session.setAttribute("errorMessage", "رمز عبور با تکرار رمز عبور همسان نیست.");
                return "redirect:/base-info/user";
            }
        }
        try {
            if (isNew) {
                userService.addNewUser(user);
            } else {
                userService.save(user);
            }
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
        }
        return "redirect:/base-info/user";
    }

    @RequestMapping(value = {"/base-info/user/remove"}, method = RequestMethod.POST)
    public String removeUser(@RequestParam(name = "username") String username,
                             HttpSession session) {
        try {
            User user = userService.findByUsername(username);
            userService.remove(user);
            session.setAttribute("successMessage", "حذف کاربر با موفقیت انجام شد.");
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
        }
        return "redirect:/base-info/user";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.addNewUser(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPassword());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "نام کاربری و رمز عبور معتیر نیست.");

        if (logout != null)
            model.addAttribute("message", "شما با موفقیت از سیستم خارج شدید.");

        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response,Authentication authentication) {
        new SecurityContextLogoutHandler().logout(request,response,authentication);
        return "redirect:/login?logout";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(ModelMap map, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            map.put("isAuthenticated", true);
        }
        return "welcome";
    }

}
