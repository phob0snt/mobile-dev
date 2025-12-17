package com.x2ketarol.askon.domain.repository;

import com.x2ketarol.askon.domain.model.Booking;
import java.util.List;

public interface BookingRepository {
    List<Booking> getUserBookings(String userId);
    Booking bookExpertTime(String expertId, String date, String time);
}
