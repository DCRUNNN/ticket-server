package nju.dc.ticketserver.po;

public class TicketsFinancePO {

    private String financialID;
    private String date;
    private String venueID;
    private String venueName;
    private String venueAddress;
    private double totalIncome;
    private double venueIncome;
    private double ticketsIncome;
    private double paymentRatio;

    public TicketsFinancePO() {
    }

    public String getFinancialID() {
        return financialID;
    }

    public void setFinancialID(String financialID) {
        this.financialID = financialID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getVenueIncome() {
        return venueIncome;
    }

    public void setVenueIncome(double venueIncome) {
        this.venueIncome = venueIncome;
    }

    public double getTicketsIncome() {
        return ticketsIncome;
    }

    public void setTicketsIncome(double ticketsIncome) {
        this.ticketsIncome = ticketsIncome;
    }

    public double getPaymentRatio() {
        return paymentRatio;
    }

    public void setPaymentRatio(double paymentRatio) {
        this.paymentRatio = paymentRatio;
    }

    @Override
    public String toString() {
        return "TicketsFinancePO{" +
                "financialID='" + financialID + '\'' +
                ", date='" + date + '\'' +
                ", venueID='" + venueID + '\'' +
                ", venueName='" + venueName + '\'' +
                ", venueAddress='" + venueAddress + '\'' +
                ", totalIncome=" + totalIncome +
                ", venueIncome=" + venueIncome +
                ", ticketsIncome=" + ticketsIncome +
                ", paymentRatio=" + paymentRatio +
                '}';
    }
}
