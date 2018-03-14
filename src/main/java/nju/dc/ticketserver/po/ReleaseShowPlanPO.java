package nju.dc.ticketserver.po;

public class ReleaseShowPlanPO {

    private ShowPO showPO;
    private String area;
    private int row;
    private String seatInfo;

    public ShowPO getShowPO() {
        return showPO;
    }

    public void setShowPO(ShowPO showPO) {
        this.showPO = showPO;
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

    public String getSeatInfo() {
        return seatInfo;
    }

    public void setSeatInfo(String seatInfo) {
        this.seatInfo = seatInfo;
    }

    @Override
    public String toString() {
        return "ReleaseShowPlanPO{" +
                "showPO=" + showPO +
                ", area='" + area + '\'' +
                ", row=" + row +
                ", seatInfo='" + seatInfo + '\'' +
                '}';
    }
}
