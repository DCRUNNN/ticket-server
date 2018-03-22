package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void getPayLeftTime() throws Exception {
        System.out.println(orderService.getPayLeftTime("order-20180318-000016"));

    }

}