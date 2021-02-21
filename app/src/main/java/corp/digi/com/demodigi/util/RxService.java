package corp.digi.com.demodigi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import corp.digi.com.demodigi.response.ServerResponse;
import corp.digi.com.demodigi.response.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by sharukhb on 10/26/2018.
 */

public interface RxService {
    @Headers("Content-Type: application/json")
    @GET("api/")
    Call<UserResponse> getData(@Query("results") int value);
}
