package com.esprit.booksmeals.utils;

import com.esprit.booksmeals.ApiError;
import com.esprit.booksmeals.network.RetroFitBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class Utils  {
    public static ApiError convertErrors(ResponseBody responseBody) {
        Converter<ResponseBody, ApiError> converter = RetroFitBuilder.getRetrofit().responseBodyConverter(ApiError.class,new Annotation[0]);
        ApiError apiError = null;
        try {
             apiError = converter.convert(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiError;
    }
}
