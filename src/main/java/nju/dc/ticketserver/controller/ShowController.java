package nju.dc.ticketserver.controller;

import nju.dc.ticketserver.dto.BaseResult;
import nju.dc.ticketserver.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @GetMapping("/getShowPO")
    public BaseResult getShowPO(@RequestParam String showID) {
        return new BaseResult<>(0, showService.getShowPOByID(showID));
    }

    @GetMapping("/getShowPOByCity")
    public BaseResult getShowPOByCity(@RequestParam String city) {
        return new BaseResult<>(0, showService.getShowPOByCity(city));
    }

    @GetMapping("/getShowPOByPerformer")
    public BaseResult getShowPOByPerformer(@RequestParam String performer) {
        return new BaseResult<>(0, showService.getShowPOByPerformer(performer));
    }

    @GetMapping("/getShowPOByCategory")
    public BaseResult getShowPOByCategory(@RequestParam String category) {
        return new BaseResult<>(0, showService.getShowPOByCategory(category));
    }

    @GetMapping("/getShowPOByVenue")
    public BaseResult getShowPOByVenue(@RequestParam String venue) {
        return new BaseResult<>(0, showService.getShowPOByVenue(venue));
    }

    @GetMapping("/guessYouLike")
    public BaseResult guessYouLike() {
        return new BaseResult<>(0, showService.guessYouLike());
    }

    @GetMapping("/todayRecommend")
    public BaseResult todayRecommend() {
        return new BaseResult<>(0, showService.todayRecommend());
    }

    @GetMapping("/allCities")
    public BaseResult getAllCities() {
        return new BaseResult<>(0, showService.getAllCities());
    }

    @GetMapping("/getShowPOByCityAndCategory")
    public BaseResult getShowPOByCityAndCategory(@RequestParam String city, @RequestParam String category) {
        return new BaseResult(0, showService.getShowPOByCityAndCategory(city, category));
    }

}
