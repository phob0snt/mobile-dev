package com.x2ketarol.askon.data.repository;

import android.content.Context;

import com.x2ketarol.askon.data.local.AskonDatabase;
import com.x2ketarol.askon.data.local.ProfilePreferences;
import com.x2ketarol.askon.data.local.entity.BookingEntity;
import com.x2ketarol.askon.data.mapper.BookingMapper;
import com.x2ketarol.askon.data.remote.MockNetworkApi;
import com.x2ketarol.askon.data.remote.dto.BookingDto;
import com.x2ketarol.askon.domain.model.Booking;
import com.x2ketarol.askon.domain.repository.BookingRepository;

import java.util.List;

/**
 * Реализация BookingRepository
 * Использует Room Database и Network API с маппингом DTO → Entity → Domain
 */
public class BookingRepositoryImpl implements BookingRepository {
    
    private final AskonDatabase database;
    private final MockNetworkApi networkApi;
    private final ProfilePreferences profilePreferences;

    public BookingRepositoryImpl(Context context) {
        this.database = AskonDatabase.getInstance(context);
        this.networkApi = new MockNetworkApi();
        this.profilePreferences = new ProfilePreferences(context);
    }

    @Override
    public List<Booking> getUserBookings(String userId) {
        // 1. Пробуем получить из Room
        List<BookingEntity> cachedBookings = database.bookingDao().getBookingsByClientId(userId);
        
        // 2. Если кэш пустой, получаем из Network API
        if (cachedBookings.isEmpty()) {
            List<BookingDto> dtos = networkApi.fetchUserBookings(userId);
            // Маппинг DTO → Entity
            List<BookingEntity> entities = BookingMapper.dtosToEntities(dtos);
            // Сохраняем в Room
            database.bookingDao().insertBookings(entities);
            // Маппинг Entity → Domain
            return BookingMapper.entitiesToDomain(entities);
        }
        
        // 3. Возвращаем из кэша с маппингом Entity → Domain
        return BookingMapper.entitiesToDomain(cachedBookings);
    }

    @Override
    public Booking bookExpertTime(String expertId, String date, String time) {
        // Получаем userId из SharedPreferences
        String userId = profilePreferences.getUserId();
        if (userId == null) {
            userId = "currentUserId"; // fallback
        }
        
        // 1. Создаем бронирование через Network API
        BookingDto dto = networkApi.createBooking(userId, expertId, date, time);
        
        // 2. Маппинг DTO → Entity → Domain
        BookingEntity entity = BookingMapper.dtoToEntity(dto);
        
        // 3. Сохраняем в Room
        database.bookingDao().insertBooking(entity);
        
        // 4. Возвращаем Domain модель
        return BookingMapper.entityToDomain(entity);
    }
}
