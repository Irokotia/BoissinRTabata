package com.example.boissinrtabata.ui.selection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SelectionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is selection fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}