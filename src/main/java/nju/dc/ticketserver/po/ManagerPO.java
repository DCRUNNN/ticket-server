package nju.dc.ticketserver.po;

public class ManagerPO {

    private String name;
    private String email;
    private String password;
    private double paymentRatio;

    public ManagerPO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getPaymentRatio() {
        return paymentRatio;
    }

    public void setPaymentRatio(double paymentRatio) {
        this.paymentRatio = paymentRatio;
    }

    @Override
    public String toString() {
        return "ManagerPO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", paymentRatio=" + paymentRatio +
                '}';
    }
}
