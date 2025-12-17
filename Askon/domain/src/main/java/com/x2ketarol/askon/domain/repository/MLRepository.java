package com.x2ketarol.askon.domain.repository;

import com.x2ketarol.askon.domain.model.Expert;
import com.x2ketarol.askon.domain.model.ExpertProfile;

import java.util.List;

public interface MLRepository {
    // Expert operations
    List<Expert> getExperts(String query);
    ExpertProfile getExpertProfile(String expertId);
    
    // ML Recognition operations
    boolean recognizePhoto(String photoPath);
    String getCategoryFromPhoto(String photoPath);
}
