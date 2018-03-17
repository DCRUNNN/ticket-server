package nju.dc.ticketserver.po;

public class CouponPO {

    private String couponID;
    private String userID;
    private String couponName;
    private String description;
    private String lastTerm;  //最晚使用日期
    private String state;
    private String orderID; //若使用了，则记录使用的那个订单号
    private String usedTime; //使用优惠券的时间
    private int usedMemberPoint;
    private double value; //价值多少钱

    public String getCouponID() {
        return couponID;
    }

    public void setCouponID(String couponID) {
        this.couponID = couponID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastTerm() {
        return lastTerm;
    }

    public void setLastTerm(String lastTerm) {
        this.lastTerm = lastTerm;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }

    public int getUsedMemberPoint() {
        return usedMemberPoint;
    }

    public void setUsedMemberPoint(int usedMemberPoint) {
        this.usedMemberPoint = usedMemberPoint;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CouponPO{" +
                "couponID='" + couponID + '\'' +
                ", userID='" + userID + '\'' +
                ", couponName='" + couponName + '\'' +
                ", description='" + description + '\'' +
                ", lastTerm='" + lastTerm + '\'' +
                ", state='" + state + '\'' +
                ", orderID='" + orderID + '\'' +
                ", usedTime='" + usedTime + '\'' +
                ", usedMemberPoint=" + usedMemberPoint +
                ", value=" + value +
                '}';
    }
}
