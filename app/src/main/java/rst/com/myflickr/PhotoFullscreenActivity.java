package rst.com.myflickr;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import rst.com.myflickr.databinding.ActivityPhotoFullscreenBinding;
import rst.com.myflickr.models.PhotoModel;

public class PhotoFullscreenActivity extends AppCompatActivity {

    public static final String GET_PHOTO ="photo_data";

    private ActivityPhotoFullscreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPhotoFullscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        getDatafromIntent();
    }


    private void getDatafromIntent() {
        if (getIntent().hasExtra(GET_PHOTO)) {
            PhotoModel photoModel = getIntent().getParcelableExtra(GET_PHOTO);
            binding.fullscreenContent.setText(photoModel.getTitle());

            Glide.with(this)
                    .load(photoModel.getImgURL_large())
                    .into(binding.imageFullscreen);
        }

    }
}

