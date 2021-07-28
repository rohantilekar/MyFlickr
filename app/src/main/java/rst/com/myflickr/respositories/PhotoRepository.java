package rst.com.myflickr.respositories;

import androidx.lifecycle.LiveData;

import java.util.List;

import rst.com.myflickr.models.PhotoModel;
import rst.com.myflickr.network.PhotoApiClient;


public class PhotoRepository {

    private static PhotoRepository instance;

    private static PhotoApiClient photoApiClient;
    private String mQuery;
    private int pageNumber;

    public PhotoRepository() {
        photoApiClient = PhotoApiClient.getInstance();
    }

    public static PhotoRepository getInstance() {
        if (instance == null) {
            instance = new PhotoRepository();
        }
        return instance;
    }

    public LiveData<List<PhotoModel>> getPhotos() {
        return photoApiClient.getPhotos();
    }

    public void searchPhotos(String tag, int page) {

        mQuery = tag;
        pageNumber = page;
        photoApiClient.searchPhotos(tag, page);

    }

    public void loadNextPage() {
        searchPhotos(mQuery, pageNumber + 1);
    }


}
