package ir.mfava.modfava.pardazesh.controller.report;

import ir.mfava.modfava.pardazesh.controller.BaseController;
import ir.mfava.modfava.pardazesh.model.DTO.JSONMessage;
import ir.mfava.modfava.pardazesh.model.LoginFailureLog;
import ir.mfava.modfava.pardazesh.model.UserSessionInformation;
import ir.mfava.modfava.pardazesh.service.LoginFailureLogService;
import ir.mfava.modfava.pardazesh.service.UserService;
import ir.mfava.modfava.pardazesh.service.UserSessionInformationService;
import ir.mfava.modfava.pardazesh.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/report/login-report/")
public class LoginReportController extends BaseController {

    @Autowired
    private UserSessionInformationService userSessionInformationService;
    @Autowired
    private LoginFailureLogService loginFailureLogService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = {"view", ""})
    public String showLoginReport(ModelMap map) {
        map.put("AllUsers", userService.getAll());
        map.put("currentDate", DateUtils.convertJulianToPersian(new Date(), "yyyy/MM/dd"));
        return "report/login-report";
    }

    @ResponseBody
    @RequestMapping("search-sessions")
    public JSONMessage searchUserSessionInformation(@RequestParam(name = "startDate", required = false) String startDate,
                                                    @RequestParam(name = "endDate", required = false) String endDate,
                                                    @RequestParam(name = "userId") Long userId) {

        if (startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
            return JSONMessage.Error("لطفا بازه زمانی تهیه گزارش را مشخص فرمایید.");
        }
        Date realStartDate;
        Date realEndDate;

        try {
            realStartDate = DateUtils.convertPersianToJulian(startDate);
            if (realStartDate == null) throw new Exception("bad format");
            realEndDate = DateUtils.convertPersianToJulian(endDate);
            if (realEndDate == null) throw new Exception("bad format");
            realEndDate = DateUtils.addDays(realEndDate, 1);
        } catch (Exception e) {
            return JSONMessage.Error("خطا در خواندن تاریخ. لطفا فرمت تاریخ را بررسی و مجددا امتحان کنید.");
        }

        List<UserSessionInformation> userSessionInformations = userSessionInformationService.searchSessionInformations(userId, realStartDate, realEndDate);
        Map<String, Object> map = new HashMap<>();
        map.put("userSessionInformations", userSessionInformations);
        return JSONMessage.Success(map);
    }

    @ResponseBody
    @RequestMapping("online-users")
    public JSONMessage getOnlineUsers() {
        List<UserSessionInformation> userSessionInformations = userSessionInformationService.getOnlineUsers();
        Map<String, Object> map = new HashMap<>();
        map.put("userSessionInformations", userSessionInformations);
        return JSONMessage.Success(map);
    }

    @ResponseBody
    @RequestMapping("search-login-failures")
    public JSONMessage searchLoginFailures(@RequestParam(name = "startDate", required = false) String startDate,
                                           @RequestParam(name = "endDate", required = false) String endDate,
                                           @RequestParam(name = "ip", required = false) String ip,
                                           @RequestParam(name = "username", required = false) String username) {

        if (startDate == null || endDate == null) {
            return JSONMessage.Error("لطفا بازه زمانی تهیه گزارش را مشخص فرمایید.");
        }
        Date realStartDate;
        Date realEndDate;

        try {
            realStartDate = DateUtils.convertPersianToJulian(startDate);
            if (realStartDate == null) throw new Exception("bad format");
            realEndDate = DateUtils.convertPersianToJulian(endDate);
            if (realEndDate == null) throw new Exception("bad format");
            realEndDate = DateUtils.addDays(realEndDate, 1);
        } catch (Exception e) {
            return JSONMessage.Error("خطا در خواندن تاریخ. لطفا فرمت تاریخ را بررسی و مجددا امتحان کنید.");
        }

        if (username.isEmpty()) {
            username = null;
        }
        if (ip.isEmpty()) {
            ip = null;
        }
        List<LoginFailureLog> loginFailureLogs = loginFailureLogService.getByDateAndUsername(realStartDate, realEndDate, username, ip);
        Map<String, Object> map = new HashMap<>();
        map.put("loginFailureLogs", loginFailureLogs);
        return JSONMessage.Success(map);
    }

}
