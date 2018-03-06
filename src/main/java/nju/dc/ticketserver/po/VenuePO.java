package nju.dc.ticketserver.po;

import java.util.*;

public class VenuePO {

    private String venueName;
    private String venueID;
    private String city;
    private String address;
    private String area; //区域 数据库中以varchar形式存储，以/隔开
    private int row; //一共有几排
    private String seat;
    private String venueInfo;
    private String regDate; //场馆注册时间
    private double income;
    private String state; //需要经理审批

    public VenuePO() {
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueID() {
        return venueID;
    }

    public void setVenueID(String venueID) {
        this.venueID = venueID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getVenueInfo() {
        return venueInfo;
    }

    public void setVenueInfo(String venueInfo) {
        this.venueInfo = venueInfo;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "VenuePO{" +
                "venueName='" + venueName + '\'' +
                ", venueID='" + venueID + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", area=" + area +
                ", row=" + row +
                ", seat='" + seat + '\'' +
                ", venueInfo='" + venueInfo + '\'' +
                ", regDate='" + regDate + '\'' +
                ", income=" + income +
                ", state='" + state + '\'' +
                '}';
    }
}
