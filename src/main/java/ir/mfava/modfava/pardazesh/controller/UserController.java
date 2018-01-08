package ir.mfava.modfava.pardazesh.controller;


import ir.mfava.modfava.pardazesh.model.Configuration;
import ir.mfava.modfava.pardazesh.model.DTO.JSONMessage;
import ir.mfava.modfava.pardazesh.model.Role;
import ir.mfava.modfava.pardazesh.model.User;
import ir.mfava.modfava.pardazesh.security.SecurityService;
import ir.mfava.modfava.pardazesh.service.ConfigurationService;
import ir.mfava.modfava.pardazesh.service.RoleService;
import ir.mfava.modfava.pardazesh.service.UserService;
import ir.mfava.modfava.pardazesh.util.ValidatorUtils;
import ir.mfava.modfava.pardazesh.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SessionRegistry sessionRegistry;
    @Autowired
    private ConfigurationService configurationService;

    @RequestMapping(value = {"/base-info/user"}, method = RequestMethod.GET)
    public String getAllUsers(ModelMap map, HttpSession session) {
        map.put("users", userService.getAll());
        map.put("roles", roleService.getAll());
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
                           @RequestParam(name = "locked") Boolean locked,
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
        user.setLocked(locked);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setNationalCode(nationalCode);
        user.setPersonNumber(personNumber);

        List<Configuration> configurationList = configurationService.getAll();
        if (!configurationList.isEmpty() && newPassword != null && !newPassword.isEmpty()) {
            Configuration configuration = configurationList.get(0);
            if (newPassword.length() < configuration.getPasswordLength()) {
                session.setAttribute("errorMessage", "رمز عبور باید حداقل " + configuration.getPasswordLength() + "  کاراکتر باشد.");
                return "redirect:/base-info/user";
            }
            try {
                ValidatorUtils.isValidPassword(newPassword, configuration.getPasswordComplexity().ordinal());
            } catch (IllegalArgumentException e) {
                String errorMessage = "";
                switch (e.getMessage()) {
                    case "must-contain-number":
                        errorMessage = "رمز عبور باید شامل حداقل یک عدد باشد.";
                        break;
                    case "must-contain-upper-lower":
                        errorMessage = "رمز عبور باید حداقل شامل یک حرف بزرگ و یک حرف کوچک باشد.";
                        break;
                    case "must-contain-upper-lower-number":
                        errorMessage = "رمز عبور باید حداقل شامل یک حرف بزرگ، یک حرف کوچک و یک عدد باشد.";
                        break;
                    case "must-contain-upper-lower-number-special":
                        errorMessage = "رمز عبور باید حداقل شامل یک حرف بزرگ، یک حرف کوچک، یک عدد و یک علامت خاص باشد.";
                        break;
                    case "must-not-be-empty":
                        errorMessage = "رمز عبور باید شامل حداقل یک کاراکتر باشد.";
                        break;
                }
                session.setAttribute("errorMessage", errorMessage);
                return "redirect:/base-info/user";
            }
        }
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

    @ResponseBody
    @RequestMapping(value = "/base-info/user/roles", method = RequestMethod.GET)
    public JSONMessage grantRole(@RequestParam(name = "userId") Long userId) {
        User user = userService.getById(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("roles", user.getRoles());
        return JSONMessage.Success("", map);
    }

    @ResponseBody
    @RequestMapping(value = "/base-info/grant/role", method = RequestMethod.POST)
    public JSONMessage grantRole(@RequestParam(name = "userId") Long userId,
                                 @RequestParam(name = "roleId") Long roleId,
                                 @RequestParam(name = "grant") Boolean grant) {
        User user = userService.getById(userId);
        Role role = roleService.getById(roleId);
        Set<Role> userRoles = user.getRoles();
        try {
            if (grant != null && grant) {
                if (!userRoles.contains(role)) {
                    userRoles.add(role);
                }
            } else {
                if (userRoles.contains(role)) {
                    userRoles.remove(role);
                }
            }
            user.setRoles(userRoles);
            userService.save(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            return JSONMessage.Error("unknown.exception");
        }

        return JSONMessage.Success();
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
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
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

        // TODO : There's a problem with this method, fix it (error message is not shown)

        if (error != null)
            model.addAttribute("error", "نام کاربری و رمز عبور معتیر نیست.");

        if (logout != null)
            model.addAttribute("message", "شما با موفقیت از سیستم خارج شدید.");

        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        List<SessionInformation> infos = sessionRegistry.getAllSessions(authentication.getPrincipal(), false);

        for (SessionInformation info : infos) {
            info.expireNow(); //expire the session
//            sessionRegistry.removeSessionInformation(info.getSessionId()); //remove session
        }
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
