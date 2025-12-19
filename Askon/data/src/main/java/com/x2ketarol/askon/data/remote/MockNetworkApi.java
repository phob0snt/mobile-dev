package com.x2ketarol.askon.data.remote;

import com.x2ketarol.askon.data.remote.dto.BookingDto;
import com.x2ketarol.askon.data.remote.dto.ExpertDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock Network API для демонстрации работы с сетью
 * В production это был бы Retrofit интерфейс с реальными эндпоинтами
 * Демонстрирует паттерн DTO → Entity → Domain
 */
public class MockNetworkApi {
    
    /**
     * Получить список экспертов из "сети"
     * @return List<ExpertDto>
     */
    public List<ExpertDto> fetchExperts() {
        List<ExpertDto> experts = new ArrayList<>();
        
        experts.add(new ExpertDto(
            "1",
            "Dr. Sarah Johnson",
            "Psychologist",
            "https://randomuser.me/api/portraits/women/44.jpg",
            4.9,
            127,
            50.0,
            "Clinical psychologist with 10+ years experience",
            new String[]{"CBT", "Anxiety", "Depression"}
        ));
        
        experts.add(new ExpertDto(
            "2",
            "John Smith",
            "Business Consultant",
            "https://randomuser.me/api/portraits/men/32.jpg",
            4.7,
            89,
            75.0,
            "Business strategy and startup consulting",
            new String[]{"Strategy", "Marketing", "Finance"}
        ));
        
        experts.add(new ExpertDto(
            "3",
            "Maria Garcia",
            "Career Coach",
            "https://randomuser.me/api/portraits/women/65.jpg",
            4.8,
            156,
            45.0,
            "Helping professionals reach their career goals",
            new String[]{"Resume", "Interview", "Networking"}
        ));
        
        return experts;
    }
    
    /**
     * Поиск экспертов по запросу
     */
    public List<ExpertDto> searchExperts(String query) {
        // Mock: возвращаем всех экспертов, фильтруя по имени/специальности
        List<ExpertDto> allExperts = fetchExperts();
        if (query == null || query.isEmpty()) {
            return allExperts;
        }
        
        List<ExpertDto> filtered = new ArrayList<>();
        for (ExpertDto expert : allExperts) {
            if (expert.getFullName().toLowerCase().contains(query.toLowerCase()) ||
                expert.getSpecialization().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(expert);
            }
        }
        return filtered;
    }
    
    /**
     * Получить детали эксперта
     */
    public ExpertDto fetchExpertDetails(String expertId) {
        List<ExpertDto> experts = fetchExperts();
        for (ExpertDto expert : experts) {
            if (expert.getId().equals(expertId)) {
                return expert;
            }
        }
        return null;
    }
    
    /**
     * Получить бронирования пользователя
     */
    public List<BookingDto> fetchUserBookings(String userId) {
        List<BookingDto> bookings = new ArrayList<>();
        
        bookings.add(new BookingDto(
            "b1",
            userId,
            "1",
            "Dr. Sarah Johnson",
            "Psychologist",
            "2025-12-20",
            "14:00",
            "Confirmed",
            50.0
        ));
        
        bookings.add(new BookingDto(
            "b2",
            userId,
            "2",
            "John Smith",
            "Business Consultant",
            "2025-12-22",
            "10:00",
            "Pending",
            75.0
        ));
        
        return bookings;
    }
    
    /**
     * Создать новое бронирование
     */
    public BookingDto createBooking(String clientId, String expertId, String date, String time) {
        // Mock: возвращаем новое бронирование
        return new BookingDto(
            "b" + System.currentTimeMillis(),
            clientId,
            expertId,
            "Expert Name",
            "Specialty",
            date,
            time,
            "Pending",
            50.0
        );
    }
}
