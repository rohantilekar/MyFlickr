package rst.com.myflickr.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import rst.com.myflickr.models.PhotoModel;
import rst.com.myflickr.respositories.PhotoRepository;

public class PhotoSearchViewModel extends ViewModel {


    private PhotoRepository photoRepository;

    public PhotoSearchViewModel() {
        photoRepository = PhotoRepository.getInstance();
    }

    public LiveData<List<PhotoModel>> getmPhotos() {
        return photoRepository.getPhotos();
    }

    public void  searchPhotos(String tag, int page){
        photoRepository.searchPhotos(tag,page);
    }

    public void loadNextPage(){

        photoRepository.loadNextPage();
    }


}
