package com.x2ketarol.askon.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.x2ketarol.askon.data.local.entity.BookingEntity;

import java.util.List;

@Dao
public interface BookingDao {
    
    @Query("SELECT * FROM bookings WHERE clientId = :clientId")
    List<BookingEntity> getBookingsByClientId(String clientId);
    
    @Query("SELECT * FROM bookings WHERE expertId = :expertId")
    List<BookingEntity> getBookingsByExpertId(String expertId);
    
    @Query("SELECT * FROM bookings WHERE id = :bookingId")
    BookingEntity getBookingById(String bookingId);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBooking(BookingEntity booking);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBookings(List<BookingEntity> bookings);
    
    @Query("DELETE FROM bookings WHERE id = :bookingId")
    void deleteBooking(String bookingId);
}
