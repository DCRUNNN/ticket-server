package nju.dc.ticketserver.controller;

import nju.dc.ticketserver.dto.BaseResult;
import nju.dc.ticketserver.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PostMapping("/confirmRegVenue")
    public BaseResult confirmRegVenue(@RequestParam String venueID) {

        int result = managerService.confirmRegVenue(venueID);
        return result == 1 ? new BaseResult<>(0, "Confirm Venue Registration Successfully!") : new BaseResult<>(-1, "Fail to confirm venue registration!");
    }


    @PostMapping("/comfirmModifyVenueInfo")
    public BaseResult confirmModifyVenueInfo(@RequestParam String venueID) {

        int result = managerService.confirmModifyVenueInfo(venueID);
        return result == 1 ? new BaseResult<>(0, "Confirm Venue Modification Successfully!") : new BaseResult<>(-1, "Fail to confirm venue modification!");
    }

}
