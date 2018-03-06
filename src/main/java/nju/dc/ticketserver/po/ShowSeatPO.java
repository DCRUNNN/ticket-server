package nju.dc.ticketserver.po;

//演出时的座位查询对象
public class ShowSeatPO {

    private String showID;
    private String venueID;
    private String area;  // 如A/B/C/D
    private int row;  //一共有几排

    //记录每排的开始座位号，然后是该排的最大偏差
    //如 A-01-01,100;A-02-01,100/B-03-01,200;B-04-01,200
    private String seat;

    public ShowSeatPO() {
    }

    public String getShowID() {
        return showID;
    }

    public void setShowID(String showID) {
        this.showID = showID;
    }

    public String getVenueID() {
        return venueID;
    }

    public void setVenueID(String venueID) {
        this.venueID = venueID;
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

    @Override
    public String toString() {
        return "ShowSeatPO{" +
                "showID='" + showID + '\'' +
                ", venueID='" + venueID + '\'' +
                ", area='" + area + '\'' +
                ", row='" + row + '\'' +
                ", seat='" + seat + '\'' +
                '}';
    }
}
