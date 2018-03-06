package nju.dc.ticketserver.controller;

import nju.dc.ticketserver.dao.UserDao;
import nju.dc.ticketserver.dao.utils.UserDaoUtils;
import nju.dc.ticketserver.dto.BaseResult;
import nju.dc.ticketserver.po.UserPO;
import nju.dc.ticketserver.service.UserService;
import nju.dc.ticketserver.utils.EncryptHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public BaseResult addUser(@RequestBody UserPO userPO) {

        boolean result = userService.doRegister(userPO, userPO.getEmail());
        return result == true ? new BaseResult<>(0, "Sign Up Successfully!") : new BaseResult<>(-1, "Fail to Sign Up!");
    }

    @GetMapping("/activeUser")
    public BaseResult activeUser(@RequestParam String activeCode) {
        System.out.println(activeCode);
        int result = userService.activeUser(activeCode);
        return result == 1 ? new BaseResult<>(0, "Active User Successfully!") : new BaseResult<>(-1, "Fail to active user!");
    }

    @PostMapping("/cancelVIP")
    public BaseResult cancelVIP(@RequestParam String userID) {

        int result = userService.cancelVIP(userID);
        return result == 1 ? new BaseResult<>(0, "cancel VIP Successfully!") : new BaseResult<>(-1, "Fail to cancel VIP!");
    }

}
