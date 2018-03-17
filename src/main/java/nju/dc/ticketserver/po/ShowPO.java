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
    private String photoSrc;  //图片路径
    private String showDate;
    private String price;  //数据库中以varchar形式存储，数字之间以/隔开
    private String description;
    private String state;

    private String area;   // A-3/B-4/C-6
    private int allRow; //数字
    private String seat; // 10/20/30

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

    public String getPhotoSrc() {
        return photoSrc;
    }

    public void setPhotoSrc(String photoSrc) {
        this.photoSrc = photoSrc;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getAllRow() {
        return allRow;
    }

    public void setAllRow(int allRow) {
        this.allRow = allRow;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
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
                ", photoSrc='" + photoSrc + '\'' +
                ", showDate='" + showDate + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", area='" + area + '\'' +
                ", allRow=" + allRow +
                ", seat='" + seat + '\'' +
                '}';
    }
}
