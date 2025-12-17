package com.x2ketarol.askon.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookings")
public class BookingEntity {
    @PrimaryKey
    @NonNull
    private String id;
    private String clientId;
    private String expertId;
    private String expertName;
    private String expertSpecialty;
    private String date;
    private String time;
    private String status;
    private double price;

    public BookingEntity(String id, String clientId, String expertId, String expertName,
                        String expertSpecialty, String date, String time, String status, double price) {
        this.id = id;
        this.clientId = clientId;
        this.expertId = expertId;
        this.expertName = expertName;
        this.expertSpecialty = expertSpecialty;
        this.date = date;
        this.time = time;
        this.status = status;
        this.price = price;
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
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
