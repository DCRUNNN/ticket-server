package nju.dc.ticketserver.controller;

import nju.dc.ticketserver.dao.utils.DaoUtils;
import nju.dc.ticketserver.dto.BaseResult;
import nju.dc.ticketserver.po.*;
import nju.dc.ticketserver.service.SeatService;
import nju.dc.ticketserver.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venue")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private DaoUtils daoUtils;

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

    @PostMapping("/releaseShowPlan")
    public BaseResult releaseShowPlan(@RequestBody ShowPO showPO, @RequestParam String area, @RequestParam int row, @RequestParam String seatInfo) {
        showPO.setShowID(daoUtils.createShowID());

        List<ShowSeatPO> showSeatPOList = seatService.convertShowSeatInfo(showPO, area, row, seatInfo);
        int result = venueService.releaseShowPlan(showPO, showSeatPOList);
        return result > 0 ? new BaseResult<>(0, "Release Show Plan Successfully!") : new BaseResult<>(-1, "Fail to release show plan!");
    }
}
