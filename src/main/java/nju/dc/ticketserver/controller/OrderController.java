package nju.dc.ticketserver.controller;

import nju.dc.ticketserver.dto.BaseResult;
import nju.dc.ticketserver.po.OrderPO;
import nju.dc.ticketserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/getOrderPO")
    public BaseResult getOrderPO(@RequestParam String orderID) {
        return new BaseResult<>(0, orderService.getOrderPO(orderID));
    }

    @PostMapping("/createOrder")
    public BaseResult createOrder(@RequestBody OrderPO orderPO) {
        int result = orderService.createOrder(orderPO);
        if (result == 1) {
            return new BaseResult<>(0, "Create Order Successfully!");

        } else if (result == -1) {
            return new BaseResult<>(-1, "座位已售出！");

        }else if (result == -2) {
            return new BaseResult<>(-1, "余额不足！");

        }else if (result == -3) {
            return new BaseResult<>(-1, "修改座位状态失败！");
        }else{
            return new BaseResult<>(-2, "创建订单失败！");
        }
    }

    @GetMapping("/getUserDiscount")
    public BaseResult getUserDiscount(@RequestParam String userID, @RequestParam int vipLevel) {
        return new BaseResult<>(0, orderService.getUserDiscount(userID, vipLevel));
    }

    @GetMapping("/getRecentOrders")
    public BaseResult getRecentOrders(@RequestParam String userID){
        return new BaseResult(0, orderService.getRecentOrders(userID));
    }

    @GetMapping("/getUnpayOrders")
    public BaseResult getUnpayOrders(@RequestParam String userID, @RequestParam String showID) {
        return new BaseResult(0, orderService.getUserUnpayOrders(userID, showID));

    }

    @GetMapping("/getUnpayOrder")
    public BaseResult getUnpayOrder(@RequestParam String orderID) {
        return new BaseResult(0, orderService.getUnpayOrder(orderID));
    }

}
