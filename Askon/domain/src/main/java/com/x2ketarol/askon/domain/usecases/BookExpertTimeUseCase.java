package com.x2ketarol.askon.domain.usecases;

import com.x2ketarol.askon.domain.model.Booking;
import com.x2ketarol.askon.domain.repository.BookingRepository;

public class BookExpertTimeUseCase {
    private final BookingRepository repository;

    public BookExpertTimeUseCase(BookingRepository repository) {
        this.repository = repository;
    }

    public Booking execute(String expertId, String date, String time) {
        return repository.bookExpertTime(expertId, date, time);
    }
}
