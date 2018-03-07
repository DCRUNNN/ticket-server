package nju.dc.ticketserver.controller;

import nju.dc.ticketserver.dto.BaseResult;
import nju.dc.ticketserver.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/getShowPO")
    public BaseResult getShowPO(@RequestParam String showID) {
        ;
        return new BaseResult<>(0, showService.getShowPOByID(showID));
    }

    @PostMapping("/getShowPOByCity")
    public BaseResult getShowPOByCity(@RequestParam String city) {
        return new BaseResult<>(0, showService.getShowPOByCity(city));
    }

    @PostMapping("/getShowPOByPerformer")
    public BaseResult getShowPOByPerformer(@RequestParam String performer) {
        return new BaseResult<>(0, showService.getShowPOByPerformer(performer));
    }

    @PostMapping("/getShowPOByCategory")
    public BaseResult getShowPOByCategory(@RequestParam String category) {
        return new BaseResult<>(0, showService.getShowPOByCategory(category));
    }

    @PostMapping("/getShowPOByVenue")
    public BaseResult getShowPOByVenue(@RequestParam String venue) {
        return new BaseResult<>(0, showService.getShowPOByVenue(venue));
    }


}
