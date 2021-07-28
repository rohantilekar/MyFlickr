package rst.com.myflickr.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rst.com.myflickr.models.PhotoResult;

/*
sample url:
https://api.flickr.com/services/rest/?api_key=492ef8d31fc7b79aa389d16e2e23710b&method=flickr.photos.search&format=json&tags=dog
*/

public interface PhotoApi {

    @GET("services/rest/?")
    Call<PhotoResult> searchPhoto(
            @Query("method") String method,
            @Query("tags") String tag,
             @Query("page") int page

    );
}
