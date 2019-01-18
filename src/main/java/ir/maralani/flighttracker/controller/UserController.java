package ir.maralani.flighttracker.controller;


import ir.maralani.flighttracker.model.Configuration;
import ir.maralani.flighttracker.model.DTO.JSONMessage;
import ir.maralani.flighttracker.model.Role;
import ir.maralani.flighttracker.model.User;
import ir.maralani.flighttracker.model.UserGroup;
import ir.maralani.flighttracker.model.report.event.Event;
import ir.maralani.flighttracker.security.SecurityService;
import ir.maralani.flighttracker.service.*;
import ir.maralani.flighttracker.service.report.event.EventService;
import ir.maralani.flighttracker.util.ValidatorUtils;
import ir.maralani.flighttracker.validator.UserValidator;
import org.apache.commons.lang.StringUtils;
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
public class UserController extends BaseController {
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
    @Autowired
    private UserSessionInformationService userSessionInformationService;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private EventService eventService;

    @RequestMapping(value = {"/base-info/user"}, method = RequestMethod.GET)
    public String getAllUsers(ModelMap map, HttpSession session) {
        map.put("users", userService.getAll());
        map.put("roles", roleService.getAll());
        map.put("userGroups", userGroupService.getAll());
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
                           @RequestParam(name = "userGroup") Long userGroupId,
                           @RequestParam(name = "isNew", required = false) Boolean isNew,
                           HttpSession session,
                           HttpServletRequest request,
                           Authentication authentication) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity","User");
        descriptionMap.put("username",username);
        descriptionMap.put("firstName", firstName);
        descriptionMap.put("lastName", lastName);
        descriptionMap.put("nationalCode", nationalCode);
        descriptionMap.put("personNumber", personNumber);
        descriptionMap.put("enabled", String.valueOf(enabled));
        descriptionMap.put("locked", String.valueOf(locked));
        descriptionMap.put("userGroupId", String.valueOf(userGroupId));
        User user;
        if (isNew) {
            user = new User();
            user.setUsername(username);
            user.setCreateDate(new Date());
            user.setPasswordExpired(false);
        } else {
            user = userService.findByUsername(username);
        }
        user.setEnabled(enabled);
        user.setLocked(locked);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setNationalCode(nationalCode);
        user.setPersonNumber(personNumber);
        user.setUserGroupId(userGroupId);

        List<Configuration> configurationList = configurationService.getAll();
        if (!configurationList.isEmpty() && newPassword != null && !newPassword.isEmpty()) {
            Configuration configuration = configurationList.get(0);
            if (newPassword.length() < configuration.getPasswordLength()) {
                session.setAttribute("errorMessage", "رمز عبور باید حداقل " + configuration.getPasswordLength() + "  کاراکتر باشد.");
                eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.USER_MANAGEMENT, Event.SubType.CHANGE_PASSWORD, Event.Flag.FAILURE, descriptionMap, Event.Sensitivity.NOTIFICATION);
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
                eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.USER_MANAGEMENT, Event.SubType.CHANGE_PASSWORD, Event.Flag.FAILURE, descriptionMap, Event.Sensitivity.NOTIFICATION);
                return "redirect:/base-info/user";
            }
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            if (newPasswordRepeat != null && newPassword.equals(newPasswordRepeat)) {
                if (isNew) {
                    user.setPassword(newPassword);
                    user.setPasswordUpdatedDate(new Date());
                } else {
                    user.setPassword(bCryptPasswordEncoder.encode(newPassword));
                    user.setPasswordUpdatedDate(new Date());
                }
            } else {
                session.setAttribute("errorMessage", "رمز عبور با تکرار رمز عبور همسان نیست.");
                eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.USER_MANAGEMENT, Event.SubType.CHANGE_PASSWORD, Event.Flag.FAILURE, descriptionMap, Event.Sensitivity.NOTIFICATION);
                return "redirect:/base-info/user";
            }
        }
        Event.Flag flag;
        try {
            if (isNew) {
                userService.addNewUser(user);
            } else {
                userService.save(user);
            }
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.USER_MANAGEMENT, isNew ? Event.SubType.ADD_NEW_USER : Event.SubType.EDIT_DATA, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/user";
    }

    @RequestMapping(value = {"/base-info/user/group/save"}, method = RequestMethod.POST)
    public String saveUserGroup(@RequestParam(name = "name") String name,
                                @RequestParam(name = "userGroupId", required = false) Long id,
                                @RequestParam(name = "isNew", required = false) Boolean isNew,
                                HttpSession session,
                                HttpServletRequest request,
                                Authentication authentication) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity","usergroup");
        descriptionMap.put("name",name);
        descriptionMap.put("userGroupId", String.valueOf(id));

        UserGroup userGroup;
        Event.SubType subType;
        Event.Flag flag;
        if (isNew) {
            userGroup = new UserGroup();
            subType = Event.SubType.ADD_GROUP;
        } else {
            userGroup = userGroupService.getById(id);
            subType = Event.SubType.EDIT_GROUP;
        }
        userGroup.setName(name);
        try {
            userGroupService.save(userGroup);
            session.setAttribute("successMessage", "ثبت گروه کاربری با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.USER_MANAGEMENT, subType, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/user";
    }

    @RequestMapping(value = {"/base-info/user/group/delete"}, method = RequestMethod.DELETE)
    public String deleteUserGroup(@RequestParam(name = "id", required = false) Long id,
                                  HttpSession session,
                                  HttpServletRequest request,
                                  Authentication authentication) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity","usergroup");
        descriptionMap.put("userGroupId", String.valueOf(id));
        UserGroup userGroup = userGroupService.getById(id);
        Event.Flag flag;
        User user = new User();
        user.setUserGroupId(userGroup.getId());
        if (userService.exists(user)) {
            session.setAttribute("errorMessage", "کاربر عضو این گروه موجود است و امکان حذف آن وجود ندارد.");
            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.DELETE, Event.SubType.DELETE_FROM_DB, Event.Flag.FAILURE, descriptionMap, Event.Sensitivity.NOTIFICATION);
        } else {
            try {
                userGroupService.remove(userGroup);
                session.setAttribute("successMessage", "حذف گروه کاربری با موفقیت انجام شد.");
                flag = Event.Flag.SUCCESS;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
                flag = Event.Flag.FAILURE;
            }
            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.DELETE, Event.SubType.DELETE_FROM_DB, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
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
                                 @RequestParam(name = "grant") Boolean grant,
                                 HttpServletRequest request,
                                 Authentication authentication) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("action","assign role to user");
        descriptionMap.put("userId", String.valueOf(userId));
        descriptionMap.put("roleId", String.valueOf(roleId));
        descriptionMap.put("grant", String.valueOf(grant));

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
            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.USER_MANAGEMENT, Event.SubType.CHANGE_USER_ACCESS, Event.Flag.SUCCESS, descriptionMap, Event.Sensitivity.NOTIFICATION);
        } catch (Exception ex) {
            ex.printStackTrace();
            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.USER_MANAGEMENT, Event.SubType.CHANGE_USER_ACCESS, Event.Flag.FAILURE, descriptionMap, Event.Sensitivity.NOTIFICATION);
            return JSONMessage.Error("unknown.exception");
        }

        return JSONMessage.Success();
    }

    @RequestMapping(value = {"/base-info/user/remove"}, method = RequestMethod.POST)
    public String removeUser(@RequestParam(name = "username") String username,
                             HttpSession session,
                             HttpServletRequest request,
                             Authentication authentication) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("action","remove user");
        descriptionMap.put("username", username);
        Event.Flag flag;
        try {
            User user = userService.findByUsername(username);
            userService.remove(user);
            session.setAttribute("successMessage", "حذف کاربر با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.USER_MANAGEMENT, Event.SubType.DELETE_USER, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
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
    public String login(Model model,
                        String error,
                        String logout,
                        HttpServletRequest request,
                        Authentication authentication) {

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
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.LOGIN_LOGOUT, Event.SubType.USER_LOGIN_LOGOUT, Event.Flag.SUCCESS, null, Event.Sensitivity.NOTIFICATION);
        for (SessionInformation info : infos) {
            info.expireNow(); //expire the session
        }
        return "redirect:/login?logout";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(ModelMap map, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            try {
                map.put("userLastSessions", userSessionInformationService.getTop10LastUserSessions(getUser(authentication)));
            } catch (Exception ignore) {

            }
            map.put("isAuthenticated", true);
        }
        return "welcome";
    }

}
