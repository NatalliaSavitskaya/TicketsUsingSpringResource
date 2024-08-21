package myApp.service;

public class BusTicket {

    private String ticketClass;
    private String ticketType;
    private String startDate;
    private int price;

    public BusTicket() {}

    public BusTicket(String ticketClass, String ticketType, String startDate, int price) {
        this.ticketClass = ticketClass;
        this.ticketType = ticketType;
        this.startDate = startDate;
        this.price = price;
    }

    // Getters and Setters
    public String getTicketClass() {return ticketClass;}

    public void setTicketClass(String ticketClass) {this.ticketClass = ticketClass;}

    public String getTicketType() {return ticketType;}

    public void setTicketType(String ticketType) {this.ticketType = ticketType;}

    public String getStartDate() {return startDate;}

    public void setStartDate(String startDate) {this.startDate = startDate;}

    public int getPrice() {return price;}

    public void setPrice(int price) {this.price = price;}

    @Override
    public String toString() {
        return "BusTicket{ticketClass=" + ticketClass + ", ticketType='" + ticketType +
                "', startDate=" + startDate + ", price=" + price +"}";
    }
}