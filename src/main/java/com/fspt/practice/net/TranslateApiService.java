package com.fspt.practice.net;


import com.fspt.practice.bean.Fanyi_En_ZH_YouDao;
import com.fspt.practice.bean.Fanyi_ZH_EN_YouDao;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TranslateApiService { // 这里试了不能使用泛型

    // Query String Parameters c=trans&m=fy&client=6&auth_user=key_ciba&sign=494a2d8a5fc22f50
    // Form Data
    @Headers({
            "Host: ifanyi.iciba.com",
            "Content-Type: application/x-www-form-urlencoded"}
    )
    @FormUrlEncoded
    @POST("/index.php")
    Observable<Object> getFanyiResult_En_ZH_V2(
            @Query("c") String trans, @Query("m") String fy, @Query("client") String client,
            @Query("auth_user") String key_ciba, @Query("sign") String sign,
            @Field("from") String from, @Field("to") String to, @Field("q") String word
    );


    //有道翻译
    // Request URL: http://fanyi.youdao.com/translate_o?smartresult=dict&smartresult=rule
    // Query Parameters  :   smartresult=dict&smartresult=rule
    // Form Data:            i=red&from=AUTO&to=AUTO
    @Headers(
            {"Accept-Language: zh-CN,zh;q=0.9",
                    "Connection: keep-alive",
                    "Content-Type: application/x-www-form-urlencoded; charset=UTF-8",
                    "Host: fanyi.youdao.com"}
    )
    @FormUrlEncoded
//    @POST("translate_o?doctype=json&smartresult=dict&smartresult=rule")
  @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    Observable<Fanyi_En_ZH_YouDao> getYouDao(@Field("i") String word);


    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Call<Fanyi_ZH_EN_YouDao> getFanyiResult_ZH_EN(@Field("i") String targetSentence);

}
