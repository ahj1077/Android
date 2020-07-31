package com.an.cinemaheaven.ui.userSetting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class userSettingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public userSettingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}