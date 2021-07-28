package rst.com.myflickr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoSearchResponse {

    @SerializedName("photo")
    @Expose
    private List<PhotoModel> photos;

    public List<PhotoModel> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoModel> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "PhotoResponse{" +
                ", photos=" + photos +
                '}';
    }
}
