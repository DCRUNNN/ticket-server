package nju.dc.ticketserver.controller;

import nju.dc.ticketserver.dto.BaseResult;
import nju.dc.ticketserver.po.ManagerPO;
import nju.dc.ticketserver.service.ManagerService;
import nju.dc.ticketserver.utils.EncryptHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/confirmRegVenue")
    public BaseResult confirmRegVenue(@RequestParam String venueID) {
        int result = managerService.confirmRegVenue(venueID);

        return result == 1 ? new BaseResult<>(0, "Confirm Venue Registration Successfully!") : new BaseResult<>(-1, "Fail to confirm venue registration!");
    }


    @GetMapping("/comfirmModifyVenueInfo")
    public BaseResult confirmModifyVenueInfo(@RequestParam String venueID) {

        int result = managerService.confirmModifyVenueInfo(venueID);
        return result == 1 ? new BaseResult<>(0, "Confirm Venue Modification Successfully!") : new BaseResult<>(-1, "Fail to confirm venue modification!");
    }


    @PostMapping("/login")
    public BaseResult login(@RequestBody ManagerPO managerPO) {
        ManagerPO checkManagerPO = managerService.getManagerPO(managerPO.getEmail());
        if (checkManagerPO == null) {
            return new BaseResult(-1, "account not exists！");
        }
        boolean equal = EncryptHelper.checkPassword(managerPO.getPassword(), checkManagerPO.getPassword());
        if (equal) {
            return new BaseResult(0, checkManagerPO);
        } else {
            return new BaseResult(2, "email and password do not match!");
        }
    }

    @GetMapping("/getManagerPO")
    public BaseResult getManagerPO(@RequestParam  String email) {
        return new BaseResult<>(0, managerService.getManagerPO(email));
    }

    @GetMapping("/getVenueRegApplications")
    public BaseResult getVenueRegApplications() {
        return new BaseResult<>(0, managerService.getVenueRegApplicationPOs());
    }

    @GetMapping("/getVenueModifyApplications")
    public BaseResult getVenueModifyApplications() {
        return new BaseResult<>(0, managerService.getVenueModifyApplicationPOs());
    }

    @GetMapping("/giveMoneyToVenue")
    public BaseResult giveMoneyToVenue(@RequestParam String showID, @RequestParam double paymentRatio) {
        int result = managerService.giveMoneyToVenue(showID, paymentRatio);

        return result == 1 ? new BaseResult<>(0, "支付成功！") : new BaseResult<>(-1, "支付失败");
    }

    @GetMapping("/getNeedToPayShows")
    public BaseResult getNeedToPayShows() {
        return new BaseResult<>(0, managerService.getNeedToPayShows());
    }

}
