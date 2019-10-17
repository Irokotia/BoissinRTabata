package com.example.boissinrtabata.ui.seance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SeanceViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    public SeanceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is start fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
