package nju.dc.ticketserver.controller;

import nju.dc.ticketserver.dao.utils.DaoUtils;
import nju.dc.ticketserver.dto.BaseResult;
import nju.dc.ticketserver.po.*;
import nju.dc.ticketserver.service.SeatService;
import nju.dc.ticketserver.service.VenueService;
import nju.dc.ticketserver.utils.EncryptHelper;
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
    public BaseResult releaseShowPlan(@RequestBody ReleaseShowPlanPO releaseShowPlanPO) {
        ShowPO showPO = releaseShowPlanPO.getShowPO();
        showPO.setShowID(daoUtils.createShowID());
        showPO.setState("售票中");

        List<ShowSeatPO> showSeatPOList = seatService.convertShowSeatInfo(showPO, releaseShowPlanPO.getArea(), releaseShowPlanPO.getRow(), releaseShowPlanPO.getSeatInfo());

        int result = venueService.releaseShowPlan(showPO, showSeatPOList);
        return result > 0 ? new BaseResult<>(0, "Release Show Plan Successfully!") : new BaseResult<>(-1, "Fail to release show plan!");
    }

    @PostMapping("/login")
    public BaseResult venueLogin(@RequestBody VenuePO venuePO) {
        VenuePO checkVenuePO = venueService.getVenuePO(venuePO.getVenueID());
        if (checkVenuePO == null) {
            return new BaseResult(-1, "account not exists！");
        }
        boolean equal = EncryptHelper.checkPassword(venuePO.getPassword(), checkVenuePO.getPassword());
        if (equal) {
            return new BaseResult(0, checkVenuePO);
        }else{
            return new BaseResult(2, "userName and password do not match!");
        }
    }

    @GetMapping("/getVenuePO")
    public BaseResult getVenuePO(@RequestParam String venueID) {
        return new BaseResult<>(0, venueService.getVenuePO(venueID));
    }

}
