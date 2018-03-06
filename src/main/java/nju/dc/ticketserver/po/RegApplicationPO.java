package nju.dc.ticketserver.po;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//场馆注册申请
public class RegApplicationPO {

    private String venueName;
    private String venueID;
    private String city;
    private String venueAddress;
    private String area;  // A/B/C
    private int row;
    private String seat;
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
                ", venueAddress='" + venueAddress + '\'' +
                ", area=" + area +
                ", row=" + row +
                ", seat='" + seat + '\'' +
                ", venueInfo='" + venueInfo + '\'' +
                ", applicationTime='" + applicationTime + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
