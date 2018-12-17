package com.jiujiu.movieShow.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ErrorUtils {

    public static String getHttpErrorMessage(Throwable t) {
        String errorMessage = null;
        try {
            if (t instanceof HttpException) {
                HttpException exception = (HttpException) t;
                ResponseBody responseBody = exception.response().errorBody();
                if (responseBody != null) {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    errorMessage = jsonObject.getString("status_message");
                }else{
                    errorMessage = exception.getMessage();
                }
            }else{
                errorMessage = t.getMessage();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return errorMessage;
    }
}
