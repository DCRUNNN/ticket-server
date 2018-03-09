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
        return result == 1 ? new BaseResult<>(0, "Create Order Successfully!") : new BaseResult<>(-1, "Fail to create order!");
    }

    @GetMapping("/getUserDiscount")
    public BaseResult getUserDiscount(@RequestParam String userID, @RequestParam int vipLevel) {
        return new BaseResult<>(0, orderService.getUserDiscount(userID, vipLevel));
    }



}
