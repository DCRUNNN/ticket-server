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
        String result = orderService.createOrder(orderPO);
        if (result.startsWith("order")) {
            return new BaseResult<>(0, result);
        } else {
            return new BaseResult<>(-1, result);
        }

    }

    @GetMapping("/getUserDiscount")
    public BaseResult getUserDiscount(@RequestParam String userID, @RequestParam int vipLevel) {
        return new BaseResult<>(0, orderService.getUserDiscount(userID, vipLevel));
    }

    @GetMapping("/getRecentOrders")
    public BaseResult getRecentOrders(@RequestParam String userID) {
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

    @GetMapping("/getPayLeftTime")
    public BaseResult getPayLeftTime(@RequestParam String orderID) {
        return new BaseResult(0, orderService.getPayLeftTime(orderID));
    }

    @GetMapping("/cancelOrder")
    public BaseResult cancelOrder(@RequestParam String orderID) {
        int result = orderService.cancelOrder(orderID);
        return result == 1 ? new BaseResult(0, "取消订单成功！") : new BaseResult(-1, "取消订单失败！");
    }

    @GetMapping("/getAllOrders")
    public BaseResult getAllOrders(@RequestParam String userID) {
        return new BaseResult(0, orderService.getAllOrders(userID));
    }

    @GetMapping("/getOrdersByState")
    public BaseResult getOrdersByState(@RequestParam String userID, @RequestParam String state) {
        return new BaseResult(0, orderService.getOrdersByState(userID, state));
    }

    @GetMapping("/refundOrder")
    public BaseResult refundOrder(@RequestParam String userID,@RequestParam String orderID) {
        double backMoney = orderService.refundOrder(userID, orderID);
        return backMoney != -1 ? new BaseResult(0, backMoney) : new BaseResult(-1, "申请退款失败！");
    }

}
