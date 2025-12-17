package com.x2ketarol.askon.data.repository;

import android.content.Context;

import com.x2ketarol.askon.data.local.AskonDatabase;
import com.x2ketarol.askon.data.local.entity.ExpertEntity;
import com.x2ketarol.askon.data.mapper.ExpertMapper;
import com.x2ketarol.askon.data.remote.MockNetworkApi;
import com.x2ketarol.askon.data.remote.dto.ExpertDto;
import com.x2ketarol.askon.domain.model.Expert;
import com.x2ketarol.askon.domain.model.ExpertProfile;
import com.x2ketarol.askon.domain.repository.MLRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация MLRepository
 * Использует три источника данных:
 * 1. Room Database (локальное хранилище)
 * 2. Network API (mock) с маппингом DTO → Entity → Domain
 * 3. Логика ML для распознавания
 */
public class MLRepositoryImpl implements MLRepository {

    private final AskonDatabase database;
    private final MockNetworkApi networkApi;

    public MLRepositoryImpl(Context context) {
        this.database = AskonDatabase.getInstance(context);
        this.networkApi = new MockNetworkApi();
    }

    @Override
    public List<Expert> getExperts(String query) {
        // 1. Пробуем получить из Room (кэш)
        List<ExpertEntity> cachedExperts = database.expertDao().getAllExperts();
        
        // 2. Если кэш пустой, получаем из Network API
        if (cachedExperts.isEmpty()) {
            List<ExpertDto> dtos = networkApi.fetchExperts();
            // Маппинг DTO → Entity
            List<ExpertEntity> entities = ExpertMapper.dtosToEntities(dtos);
            // Сохраняем в Room
            database.expertDao().insertExperts(entities);
            // Маппинг Entity → Domain
            return ExpertMapper.entitiesToDomain(entities);
        }
        
        // 3. Если есть query, ищем в Room
        if (query != null && !query.isEmpty()) {
            List<ExpertEntity> searchResults = database.expertDao().searchExperts(query);
            return ExpertMapper.entitiesToDomain(searchResults);
        }
        
        // 4. Возвращаем из кэша с маппингом Entity → Domain
        return ExpertMapper.entitiesToDomain(cachedExperts);
    }

    @Override
    public ExpertProfile getExpertProfile(String expertId) {
        // Получаем из Room
        ExpertEntity entity = database.expertDao().getExpertById(expertId);
        
        // Если нет в Room, получаем из Network
        if (entity == null) {
            ExpertDto dto = networkApi.fetchExpertDetails(expertId);
            if (dto != null) {
                entity = ExpertMapper.dtoToEntity(dto);
                database.expertDao().insertExpert(entity);
            }
        }
        
        if (entity != null) {
            // Маппинг Entity → Domain
            Expert expert = ExpertMapper.entityToDomain(entity);
            return new ExpertProfile(
                expert.getId(),
                expert.getName(),
                expert.getSpecialty(),
                entity.getBio(),
                expert.getPhotoUrl(),
                expert.getRating(),
                expert.getReviewCount()
            );
        }
        
        return null;
    }

    @Override
    public boolean recognizePhoto(String photoPath) {
        // Mock ML recognition - returns random result
        return Math.random() < 0.5;
    }

    @Override
    public String getCategoryFromPhoto(String photoPath) {
        // Mock ML category detection
        String[] categories = {"Plumbing", "Electrical", "Carpentry", "Painting", "HVAC"};
        int randomIndex = (int) (Math.random() * categories.length);
        return categories[randomIndex];
    }
}
