package com.jiujiu.movieShow.data.model.vo;

import android.support.annotation.Nullable;

import java.io.IOException;

import retrofit2.Response;


public class ApiResponse<T> {

    public final int code;
    @Nullable
    public final T body;
    @Nullable
    public final String errorMessage;

    public ApiResponse(Response<T> response){
        code = response.code();
        if(response.isSuccessful()){
            body = response.body();
            errorMessage = null;
        }else{
            String message = null;
            if(response.errorBody() != null){
                try {
                   message  =  response.errorBody().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            body = null;
        }
    }

    public boolean isSuccess(){
        return this.code > 200 & this.code<300;
    }

    public ApiResponse(Throwable throwable){
        code = 500;
        body = null;
        errorMessage = throwable.getMessage();
    }
}
