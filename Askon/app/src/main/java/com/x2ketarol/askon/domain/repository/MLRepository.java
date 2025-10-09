package com.x2ketarol.askon.domain.repository;

public interface MLRepository {
    // ML Recognition operations
    boolean recognizePhoto(String photoPath);
    String getCategoryFromPhoto(String photoPath);
}
