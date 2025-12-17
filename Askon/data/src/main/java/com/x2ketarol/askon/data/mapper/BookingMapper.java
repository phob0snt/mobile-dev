package com.x2ketarol.askon.data.mapper;

import com.x2ketarol.askon.data.local.entity.BookingEntity;
import com.x2ketarol.askon.data.remote.dto.BookingDto;
import com.x2ketarol.askon.domain.model.Booking;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper для Booking: DTO → Entity → Domain
 */
public class BookingMapper {
    
    // DTO → Entity
    public static BookingEntity dtoToEntity(BookingDto dto) {
        return new BookingEntity(
            dto.getId(),
            dto.getClientId(),
            dto.getExpertId(),
            dto.getExpertName(),
            dto.getExpertSpecialty(),
            dto.getBookingDate(),
            dto.getBookingTime(),
            dto.getBookingStatus(),
            dto.getTotalPrice()
        );
    }
    
    // Entity → Domain
    public static Booking entityToDomain(BookingEntity entity) {
        return new Booking(
            entity.getId(),
            entity.getExpertId(),
            entity.getExpertName(),
            entity.getDate(),
            entity.getTime(),
            entity.getStatus()
        );
    }
    
    // Domain → Entity
    public static BookingEntity domainToEntity(Booking domain) {
        return new BookingEntity(
            domain.getId(),
            "currentUserId", // clientId
            domain.getExpertId(),
            domain.getExpertName(),
            "", // expertSpecialty
            domain.getDate(),
            domain.getTime(),
            domain.getStatus(),
            50.0 // price
        );
    }
    
    // DTO → Domain
    public static Booking dtoToDomain(BookingDto dto) {
        return entityToDomain(dtoToEntity(dto));
    }
    
    // List mappings
    public static List<Booking> entitiesToDomain(List<BookingEntity> entities) {
        List<Booking> bookings = new ArrayList<>();
        for (BookingEntity entity : entities) {
            bookings.add(entityToDomain(entity));
        }
        return bookings;
    }
    
    public static List<BookingEntity> dtosToEntities(List<BookingDto> dtos) {
        List<BookingEntity> entities = new ArrayList<>();
        for (BookingDto dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}
