package com.hgm.haritagenelmudurlugu.Admin.SendNotif;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAvlZ3OgU:APA91bFSaFtw8bhAyIV967Occ4BplCB8k2fIaFBVfAh74Q304y_Tz6gzucOvf2peq64ACXSJXT-tdEa6iSdrFGHAVsO03XnTAscFQooiGzeoZzhlsaWnhzD8TRaGB2xNGbqiYdDL-mlF"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationSender body);
}
