package nju.dc.ticketserver.controller;

import nju.dc.ticketserver.dto.BaseResult;
import nju.dc.ticketserver.po.CouponPO;
import nju.dc.ticketserver.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;


    @GetMapping("/getAllCoupons")
    public BaseResult getAllCoupons(@RequestParam String userID) {
        return new BaseResult<>(0, couponService.getAllCouponPOs(userID));
    }

    @GetMapping("/getCouponPO")
    public BaseResult getCouponPO(@RequestParam String couponID) {
        return new BaseResult<>(0, couponService.getCouponPO(couponID));
    }

    @PostMapping("/addCoupon")
    public BaseResult addCoupon(@RequestBody CouponPO couponPO) {
        int result = couponService.addCouponPO(couponPO);
        return result == 1 ? new BaseResult<>(0, "Add CouponPO Successfully!") : new BaseResult<>(-1, "Fail to add couponPO!");
    }

    @GetMapping("/getCouponPOByState")
    public BaseResult getCouponPOByState(@RequestParam String userID, @RequestParam String state) {
        return new BaseResult<>(0, couponService.getCouponPOByState(userID, state));
    }
}
