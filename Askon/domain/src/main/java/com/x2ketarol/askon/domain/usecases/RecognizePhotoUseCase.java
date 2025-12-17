package com.x2ketarol.askon.domain.usecases;

import com.x2ketarol.askon.domain.repository.MLRepository;

public class RecognizePhotoUseCase {
    private final MLRepository repository;

    public RecognizePhotoUseCase(MLRepository repository) {
        this.repository = repository;
    }

    public boolean execute(String photoPath) {
        return repository.recognizePhoto(photoPath);
    }
}
