package rst.com.myflickr.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class PhotoModel implements Parcelable {

    public static final Creator<PhotoModel> CREATOR = new Creator<PhotoModel>() {
        @Override
        public PhotoModel createFromParcel(Parcel in) {
            return new PhotoModel(in);
        }

        @Override
        public PhotoModel[] newArray(int size) {
            return new PhotoModel[size];
        }
    };
    private static final String IMAGE_URL = "https://live.staticflickr.com/%s/%s_%s_b.jpg";
    String id;
    String secret;
    String server;
    String title;

    public PhotoModel() {
    }

    public PhotoModel(String id, String secret, String server, String title) {
        this.id = id;
        this.secret = secret;
        this.server = server;
        this.title = title;

    }

    protected PhotoModel(Parcel in) {
        id = in.readString();
        secret = in.readString();
        server = in.readString();
        title = in.readString();

    }

    @BindingAdapter("android:loadImage")
    public static void loadImage(View imageview, String imgUrl) {
        Glide.with(imageview)
                .load(imgUrl)
                .into((ImageView) imageview);
    }

    public String getImgURL() {

        return String.format(IMAGE_URL, server, id, secret);
    }

    public String getImgURL_large() {

        return String.format(IMAGE_URL, server, id, secret);
    }

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "PhotoModel{" +
                "id='" + id + '\'' +
                ", secret='" + secret + '\'' +
                ", server='" + server + '\'' +
                ", title='" + title + '\'' +

                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(secret);
        dest.writeString(server);
        dest.writeString(title);

    }

}
