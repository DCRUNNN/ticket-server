package nju.dc.ticketserver.po;

//演出时的座位查询对象
public class showSeatPO {

    private String showID;
    private String venueID;
    private String area;
    private String row;
    private String seat;  //以分号隔开数字 如1;2;3;4;5

    public showSeatPO() {
    }

    @Override
    public String toString() {
        return "showSeatPO{" +
                "showID='" + showID + '\'' +
                ", venueID='" + venueID + '\'' +
                ", area='" + area + '\'' +
                ", row='" + row + '\'' +
                ", seat='" + seat + '\'' +
                '}';
    }
}
