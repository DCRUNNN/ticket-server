package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.OrderDao;
import nju.dc.ticketserver.po.OrderPO;
import nju.dc.ticketserver.service.ShowService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShowServiceImplTest {

    @Autowired
    private ShowService showService;

    @Autowired
    private OrderDao orderDao;

    @Test
    public void isShowStartCheckTicket() throws Exception {

        System.out.println(showService.isShowStartCheckTicket("show-000003"));
    }

    @Test
    public void getAreaByPrice() throws Exception {
        List<OrderPO> orders = orderDao.getNeedToArrangeSeatOrders("show-000004");

        for (OrderPO orderPO : orders) {
            System.out.println(orderPO);
            System.out.println(orderPO.getUnitPrice());

            System.out.println(String.valueOf((int)orderPO.getUnitPrice()));

            System.out.println(showService.getAreaByPrice("show-000004", String.valueOf((int)orderPO.getUnitPrice())));

        }
    }


}