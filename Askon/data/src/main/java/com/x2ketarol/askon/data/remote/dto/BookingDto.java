package com.x2ketarol.askon.data.remote.dto;

public class BookingDto {
    private String id;
    private String clientId;
    private String expertId;
    private String expertName;
    private String expertSpecialty;
    private String bookingDate;
    private String bookingTime;
    private String bookingStatus;
    private double totalPrice;
    
    // Конструкторы
    public BookingDto() {}
    
    public BookingDto(String id, String clientId, String expertId, String expertName,
                     String expertSpecialty, String bookingDate, String bookingTime,
                     String bookingStatus, double totalPrice) {
        this.id = id;
        this.clientId = clientId;
        this.expertId = expertId;
        this.expertName = expertName;
        this.expertSpecialty = expertSpecialty;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.bookingStatus = bookingStatus;
        this.totalPrice = totalPrice;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    
    public String getExpertId() { return expertId; }
    public void setExpertId(String expertId) { this.expertId = expertId; }
    
    public String getExpertName() { return expertName; }
    public void setExpertName(String expertName) { this.expertName = expertName; }
    
    public String getExpertSpecialty() { return expertSpecialty; }
    public void setExpertSpecialty(String expertSpecialty) { this.expertSpecialty = expertSpecialty; }
    
    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }
    
    public String getBookingTime() { return bookingTime; }
    public void setBookingTime(String bookingTime) { this.bookingTime = bookingTime; }
    
    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }
    
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
