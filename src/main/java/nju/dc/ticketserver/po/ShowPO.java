package nju.dc.ticketserver.po;

import java.util.Arrays;

public class ShowPO {

    private String showID;
    private String category; //演出类型
    private String city;
    private String venueID;
    private String venueName;
    private String performer;
    private String showName;
    private String date;
    private double[] price;  //数据库中以varchar形式存储，数字之间以/隔开
    private String description;
    private String state;

    public ShowPO() {
    }

    public String getShowID() {
        return showID;
    }

    public void setShowID(String showID) {
        this.showID = showID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVenueID() {
        return venueID;
    }

    public void setVenueID(String venueID) {
        this.venueID = venueID;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double[] getPrice() {
        return price;
    }

    public void setPrice(double[] price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ShowPO{" +
                "showID='" + showID + '\'' +
                ", category='" + category + '\'' +
                ", city='" + city + '\'' +
                ", venueID='" + venueID + '\'' +
                ", venueName='" + venueName + '\'' +
                ", performer='" + performer + '\'' +
                ", showName='" + showName + '\'' +
                ", date='" + date + '\'' +
                ", price=" + Arrays.toString(price) +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
