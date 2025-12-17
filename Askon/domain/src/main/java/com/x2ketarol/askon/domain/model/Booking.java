package com.x2ketarol.askon.domain.model;

public class Booking {
    private String id;
    private String expertId;
    private String expertName;
    private String date;
    private String time;
    private String status;

    public Booking(String id, String expertId, String expertName, String date, String time, String status) {
        this.id = id;
        this.expertId = expertId;
        this.expertName = expertName;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getExpertId() {
        return expertId;
    }

    public String getExpertName() {
        return expertName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }
}
