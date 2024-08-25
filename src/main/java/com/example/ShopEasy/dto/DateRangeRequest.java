package com.example.ShopEasy.dto;



import java.time.LocalDateTime;

public class DateRangeRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Getters and setters
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}

