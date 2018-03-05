package nju.dc.ticketserver.po;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderPO {

    private String orderID;
    private String userID;
    private String username;
    private String venueID;
    private String showID;
    private List<SeatPO> seatPOList = new ArrayList<>();  //数据库中以varchar形式存储
    private String purchaseMethod; //立即购买还是选座购买
    private int ticketsAmount;
    private String orderState;
    private String orderDate;
    private double totalPrice; //总价
    private double unitPrice; //一张票的单价

    public OrderPO() {
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVenueID() {
        return venueID;
    }

    public void setVenueID(String venueID) {
        this.venueID = venueID;
    }

    public String getShowID() {
        return showID;
    }

    public void setShowID(String showID) {
        this.showID = showID;
    }

    public List<SeatPO> getSeatPOList() {
        return seatPOList;
    }

    public void setSeatPOList(List<SeatPO> seatPOList) {
        this.seatPOList = seatPOList;
    }

    public String getPurchaseMethod() {
        return purchaseMethod;
    }

    public void setPurchaseMethod(String purchaseMethod) {
        this.purchaseMethod = purchaseMethod;
    }

    public int getTicketsAmount() {
        return ticketsAmount;
    }

    public void setTicketsAmount(int ticketsAmount) {
        this.ticketsAmount = ticketsAmount;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderPO{" +
                "orderID='" + orderID + '\'' +
                ", userID ='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", venueID='" + venueID + '\'' +
                ", showID='" + showID + '\'' +
                ", seatPOList=" + seatPOList +
                ", purchaseMethod='" + purchaseMethod + '\'' +
                ", ticketsAmount=" + ticketsAmount +
                ", orderState='" + orderState + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", totalPrice=" + totalPrice +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
