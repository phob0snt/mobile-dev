package com.x2ketarol.askon.data.repository;

import com.x2ketarol.askon.domain.model.Booking;
import com.x2ketarol.askon.domain.repository.BookingRepository;
import java.util.ArrayList;
import java.util.List;

public class BookingRepositoryImpl implements BookingRepository {
    private List<Booking> bookings = new ArrayList<>();

    public BookingRepositoryImpl() {
        bookings.add(new Booking("1", "1", "Alex Smith", "2025-10-15", "10:00", "Confirmed"));
        bookings.add(new Booking("2", "2", "Maria Johnson", "2025-10-20", "14:00", "Pending"));
    }

    @Override
    public List<Booking> getUserBookings(String userId) {
        return new ArrayList<>(bookings);
    }

    @Override
    public Booking bookExpertTime(String expertId, String date, String time) {
        String id = String.valueOf(bookings.size() + 1);
        Booking newBooking = new Booking(id, expertId, "Expert " + expertId, date, time, "Confirmed");
        bookings.add(newBooking);
        return newBooking;
    }
}
