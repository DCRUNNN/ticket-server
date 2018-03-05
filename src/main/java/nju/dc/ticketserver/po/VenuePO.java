package nju.dc.ticketserver.po;

import java.util.*;

public class VenuePO {

    private String venueName;
    private String venueID;
    private String city;
    private String address;
    private String[] area; //区域 数据库中以varchar形式存储，以/隔开
    private List<SeatPO> seatPOList = new ArrayList<>();  //数据库中存入seat表，area row seat
    private String venueInfo;
    private String regDate; //场馆注册时间
    private double income;

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

    public String[] getArea() {
        return area;
    }

    public void setArea(String[] area) {
        this.area = area;
    }

    public List<SeatPO> getSeatPOList() {
        return seatPOList;
    }

    public void setSeatPOList(List<SeatPO> seatPOList) {
        this.seatPOList = seatPOList;
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

    @Override
    public String toString() {
        return "VenuePO{" +
                "venueName='" + venueName + '\'' +
                ", venueID='" + venueID + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", area=" + Arrays.toString(area) +
                ", seatPOList=" + seatPOList +
                ", venueInfo='" + venueInfo + '\'' +
                ", regDate='" + regDate + '\'' +
                ", income=" + income +
                '}';
    }
}
