package rst.com.myflickr.models;

public class PhotoResult {

    PhotoSearchResponse photos;

    public PhotoResult(PhotoSearchResponse photos) {
        this.photos = photos;
    }

    public PhotoSearchResponse getPhotos() {
        return photos;
    }

    public void setPhotos(PhotoSearchResponse photos) {
        this.photos = photos;
    }
}
