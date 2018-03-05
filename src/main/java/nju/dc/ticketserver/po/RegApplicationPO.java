package nju.dc.ticketserver.po;

import java.util.ArrayList;
import java.util.List;

//场馆注册申请
public class RegApplicationPO {

    private String venueName;
    private String venueID;
    private String city;
    private String venueAddress;
    private List<SeatPO> seatPOList = new ArrayList<>();
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
                ", seat=" + seatPOList +
                ", venueInfo='" + venueInfo + '\'' +
                ", applicationTime='" + applicationTime + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
