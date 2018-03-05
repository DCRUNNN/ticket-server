package nju.dc.ticketserver.po;

public class SeatPO {

    private String venueID;
    private String area; //区域
    private String row; //排
    private String seat; //座
    private String state;

    public SeatPO() {
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

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
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
        return "SeatPO{" +
                "venueID='" + venueID + '\'' +
                ", area='" + area + '\'' +
                ", row='" + row + '\'' +
                ", seat='" + seat + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
