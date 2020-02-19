package com.talkover.api;

import com.talkover.model.AppEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by heleninsa on 2019-07-13.
 *
 * @author heleninsa
 */
public interface TemplateApi {

    @GET("/all")
    Call<List<AppEntity>> getTemplateList();

    @GET("/get")
    Call<AppEntity> downloadTemplate(@Query("appId") long appId);

    @GET("/query")
    Call<List<AppEntity>> query(@Query("keyword") String keyword);

}
