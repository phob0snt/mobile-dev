package com.x2ketarol.askon.data.mapper;

import com.google.gson.Gson;
import com.x2ketarol.askon.data.local.entity.ExpertEntity;
import com.x2ketarol.askon.data.remote.dto.ExpertDto;
import com.x2ketarol.askon.domain.model.Expert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Mapper для преобразований: DTO → Entity → Domain
 * Обеспечивает изоляцию domain от деталей реализации data слоя
 */
public class ExpertMapper {
    
    private static final Gson gson = new Gson();
    
    // DTO → Entity
    public static ExpertEntity dtoToEntity(ExpertDto dto) {
        String skillsJson = gson.toJson(dto.getSkillsList());
        return new ExpertEntity(
            dto.getId(),
            dto.getFullName(),
            dto.getSpecialization(),
            dto.getImageUrl(),
            dto.getRatingValue(),
            dto.getTotalReviews(),
            dto.getPricePerHour(),
            dto.getDescription(),
            skillsJson
        );
    }
    
    // Entity → Domain
    public static Expert entityToDomain(ExpertEntity entity) {
        return new Expert(
            entity.getId(),
            entity.getName(),
            entity.getSpecialty(),
            entity.getPhotoUrl(),
            entity.getRating()
        );
    }
    
    // Domain → Entity
    public static ExpertEntity domainToEntity(Expert domain) {
        String[] skills = domain.getSkills() != null ? 
            domain.getSkills().toArray(new String[0]) : new String[0];
        String skillsJson = gson.toJson(skills);
        return new ExpertEntity(
            domain.getId(),
            domain.getName(),
            domain.getSpecialty(),
            domain.getPhotoUrl(),
            domain.getRating(),
            domain.getReviewCount(),
            domain.getHourlyRate(),
            "",  // bio
            skillsJson
        );
    }
    
    // DTO → Domain (прямой маппинг через Entity)
    public static Expert dtoToDomain(ExpertDto dto) {
        ExpertEntity entity = dtoToEntity(dto);
        return entityToDomain(entity);
    }
    
    // List mappings
    public static List<Expert> entitiesToDomain(List<ExpertEntity> entities) {
        List<Expert> experts = new ArrayList<>();
        for (ExpertEntity entity : entities) {
            experts.add(entityToDomain(entity));
        }
        return experts;
    }
    
    public static List<ExpertEntity> dtosToEntities(List<ExpertDto> dtos) {
        List<ExpertEntity> entities = new ArrayList<>();
        for (ExpertDto dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}
