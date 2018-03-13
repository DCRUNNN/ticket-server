package nju.dc.ticketserver.po;

public class UserPO {

    private String username;
    private String userID;
    private String password;
    private String email;
    private boolean isVIP;
    private int vipLevel;
    private String phoneNumber;
    private double balance;
    private double totalConsumption; //总消费金额
    private String state; //未激活，激活
    private String activeCode; //激活码
    private int memberPoints;  //会员积分

    public UserPO() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean VIP) {
        isVIP = VIP;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    public int getMemberPoints() {
        return memberPoints;
    }

    public void setMemberPoints(int memberPoints) {
        this.memberPoints = memberPoints;
    }

    @Override
    public String toString() {
        return "UserPO{" +
                "username='" + username + '\'' +
                ", userID='" + userID + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", isVIP=" + isVIP +
                ", vipLevel=" + vipLevel +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", balance=" + balance +
                ", totalConsumption=" + totalConsumption +
                ", state='" + state + '\'' +
                ", activeCode='" + activeCode + '\'' +
                ", memberPoints=" + memberPoints +
                '}';
    }
}
