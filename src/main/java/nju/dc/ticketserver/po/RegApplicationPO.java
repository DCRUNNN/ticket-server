package nju.dc.ticketserver.po;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//场馆注册申请
public class RegApplicationPO {

    private String venueName;
    private String venueID;
    private String city;
    private String password;
    private String venueAddress;
    private String area;  // A/B/C
    private int row;  //共有多少排
    private String seat; //如共3排，从低到高每排各有10,20,30个座位，请填写10/20/30
    private String venueInfo;
    private String applicationTime;
    private String state;

    public RegApplicationPO() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
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

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "RegApplicationPO{" +
                "venueName='" + venueName + '\'' +
                ", venueID='" + venueID + '\'' +
                ", city='" + city + '\'' +
                ", password='" + password + '\'' +
                ", venueAddress='" + venueAddress + '\'' +
                ", area='" + area + '\'' +
                ", row=" + row +
                ", seat='" + seat + '\'' +
                ", venueInfo='" + venueInfo + '\'' +
                ", applicationTime='" + applicationTime + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
