package nju.dc.ticketserver.controller;

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

    @GetMapping("/getUserPO")
    public BaseResult getUserPO(@RequestParam String username) {
        return new BaseResult<>(0, userService.getUserPO(username));
    }

    @GetMapping("/getUserPOByUserID")
    public BaseResult getUserPOByUserID(@RequestParam String userID) {
        return new BaseResult<>(0, userService.getUserPOByUserID(userID));
    }

    @PostMapping("/login")
    public BaseResult login(@RequestBody UserPO userPO) {
        UserPO checkUserPO = userService.getUserPOByEmail(userPO.getEmail());

        if (checkUserPO == null) {
            return new BaseResult(-1, "account not exists！");
        }
        boolean equal = EncryptHelper.checkPassword(userPO.getPassword(), checkUserPO.getPassword());
        if (equal) {
            return new BaseResult(0, checkUserPO);
        } else {
            return new BaseResult(2, "userName and password do not match!");
        }
    }

    @GetMapping("/modifyUserInfo")
    public BaseResult modifyUserInfo(@RequestParam String userID, @RequestParam String username, @RequestParam String phoneNumber) {
        UserPO checkUserPO = userService.getUserPO(username);
        if (checkUserPO != null && !checkUserPO.getUserID().equals(userID)) {
            return new BaseResult(-1, "用户名已存在");
        } else {
            return new BaseResult<>(0, userService.modifyUserPO(userID, username, phoneNumber));
        }
    }

    @GetMapping("/modifyUserPassword")
    public BaseResult modifyUserPassword(@RequestParam String userID, @RequestParam String previousPassword, @RequestParam String newPassword) {
        int result = userService.modifyUserPassword(userID, previousPassword, newPassword);
        if (result == 1) {
            return new BaseResult<>(0, "Modify Password Successfully!");
        } else if (result == -1) {
            return new BaseResult<>(-1, "当前密码输入不正确!");
        } else {
            return new BaseResult<>(-2, "修改密码失败");
        }
    }

    @GetMapping("/confirmPayOrder")
    public BaseResult confirmPayOrder(@RequestParam String userID, @RequestParam String orderID, @RequestParam String couponID) {
        int result = userService.payOrder(userID, orderID, couponID);
        return result == 1 ? new BaseResult<>(0, "Pay Order Successfully!") : new BaseResult<>(-1, "Fail to pay order!");
    }
}
