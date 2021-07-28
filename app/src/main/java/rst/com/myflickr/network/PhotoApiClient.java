package rst.com.myflickr.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;
import rst.com.myflickr.models.PhotoModel;
import rst.com.myflickr.models.PhotoResult;

public class PhotoApiClient {

    private static final String TAG = PhotoApiClient.class.getSimpleName();

    private static PhotoApiClient instance;
    private final MutableLiveData<List<PhotoModel>> listMutableLiveData;

    private RetrievePhotoRunnable retrievePhotoRunnable;

    private PhotoApiClient() {
        listMutableLiveData = new MutableLiveData<>();
    }

    public static PhotoApiClient getInstance() {
        if (instance == null) {
            instance = new PhotoApiClient();
        }
        return instance;
    }

    public LiveData<List<PhotoModel>> getPhotos() {
        return listMutableLiveData;
    }


    //This method will fetch data from remote API.
    public void searchPhotos(String tag, int page) {

        if (retrievePhotoRunnable != null) {
            retrievePhotoRunnable = null;
        }

        retrievePhotoRunnable = new RetrievePhotoRunnable(tag, page);
        final Future myHandler = AppExecutors.getInstance().executorService().submit(retrievePhotoRunnable);

        AppExecutors.getInstance().executorService().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);

    }

    //Retrieve data from RestAPI by runnable class
    private class RetrievePhotoRunnable implements Runnable {

        private final String tag;
        private final int page;
        boolean cancelRequest;

        public RetrievePhotoRunnable(String tag, int page) {
            this.tag = tag;
            this.page = page;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {

                Response response = getPhotos(tag, page).execute();
                Log.v(TAG, "Code " + response.code());

                if (response.code() == 200) {

                    List<PhotoModel> list = new ArrayList<>(((PhotoResult) response.body()).getPhotos().getPhotos());
                    if (page == 1) {
                        listMutableLiveData.postValue(list);

                    } else {
                        List<PhotoModel> currentMoviesList = listMutableLiveData.getValue();
                        currentMoviesList.addAll(list);
                        listMutableLiveData.postValue(currentMoviesList);
                    }

                } else {
                    String error = response.errorBody().string();
                    Log.v(TAG, "Error :RetrievePhotoRunnable " + error);
                    listMutableLiveData.postValue(null);
                }
            } catch (IOException e) {

                e.printStackTrace();
                listMutableLiveData.postValue(null);
            }
            if (cancelRequest) {
                return;
            }
        }

        private Call<PhotoResult> getPhotos(String tag, int page) {

            return PhotoService.getPhotoApi()
                    .searchPhoto(Credentials.SEARCH_METHOD, tag, page);
        }

        private void cancelRequest() {

            Log.v(TAG, "Search Request cancelled");
            cancelRequest = true;
        }


    }


}
