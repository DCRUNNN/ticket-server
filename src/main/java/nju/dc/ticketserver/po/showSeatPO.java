package nju.dc.ticketserver.po;

//演出时的座位查询对象
public class ShowSeatPO {

    private String showID;
    private String venueID;
    private String area;  // 区域
    private int row;  // 排号
    private int seat; //座位号
    private String state;

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

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ShowSeatPO{" +
                "showID='" + showID + '\'' +
                ", venueID='" + venueID + '\'' +
                ", area='" + area + '\'' +
                ", row=" + row +
                ", seat=" + seat +
                ", state='" + state + '\'' +
                '}';
    }
}
