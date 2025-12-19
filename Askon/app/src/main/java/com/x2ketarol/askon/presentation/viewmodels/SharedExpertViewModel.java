package com.x2ketarol.askon.presentation.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.x2ketarol.askon.domain.model.Expert;

public class SharedExpertViewModel extends ViewModel {
    
    private final MutableLiveData<Expert> selectedExpert = new MutableLiveData<>();
    
    public void selectExpert(Expert expert) {
        selectedExpert.setValue(expert);
    }
    
    public LiveData<Expert> getSelectedExpert() {
        return selectedExpert;
    }
}
