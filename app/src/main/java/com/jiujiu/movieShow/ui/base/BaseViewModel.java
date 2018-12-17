package com.jiujiu.movieShow.ui.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {

    private CompositeDisposable mDisposable;
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public BaseViewModel() {
        mDisposable = new CompositeDisposable();
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void postErrorMessage(String message){
        this.errorMessage.postValue(message);
    }

    public void setErrorMessage(String message){
        this.errorMessage.setValue(message);
    }

    public CompositeDisposable getCompositeDisposable() {
        return mDisposable;
    }

    @Override
    protected void onCleared() {
        mDisposable.dispose();
        super.onCleared();
    }
}
