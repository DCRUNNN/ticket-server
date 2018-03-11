package nju.dc.ticketserver.controller;

import nju.dc.ticketserver.dto.BaseResult;
import nju.dc.ticketserver.po.ModifyApplicationPO;
import nju.dc.ticketserver.po.RegApplicationPO;
import nju.dc.ticketserver.po.VenuePO;
import nju.dc.ticketserver.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/venue")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @PostMapping("/regVenue")
    public BaseResult applyRegVenue(@RequestBody RegApplicationPO regApplicationPO) {

        //处理area？
        String venueID = venueService.applyRegVenue(regApplicationPO);

        return !venueID.equals("fail") ? new BaseResult<>(0, venueID) : new BaseResult<>(-1, "Fail to register venue!");
    }

    @PostMapping("/modifyVenueInfo")
    public BaseResult modifyVenueInfo(@RequestBody ModifyApplicationPO modifyApplicationPO) {

        //处理area？
        int result = venueService.applyModifyVenueInfo(modifyApplicationPO);
        return result == 1 ? new BaseResult<>(0, "Venue Register Successfully!") : new BaseResult<>(-1, "Fail to register venue!");
    }



}
