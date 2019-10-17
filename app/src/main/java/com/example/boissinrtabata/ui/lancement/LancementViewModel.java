package com.example.boissinrtabata.ui.lancement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LancementViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LancementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is start fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
