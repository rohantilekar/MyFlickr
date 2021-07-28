package rst.com.myflickr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rst.com.myflickr.ui.OnPhotoClickListener;
import rst.com.myflickr.adapters.PhotoSearchAdapter;
import rst.com.myflickr.databinding.ActivityMainBinding;
import rst.com.myflickr.models.PhotoModel;
import rst.com.myflickr.utils.NetworkConnectionUtil;
import rst.com.myflickr.viewModels.PhotoSearchViewModel;

public class MainActivity extends AppCompatActivity implements OnPhotoClickListener {

    public static final String SEND_PHOTO ="photo_data";
    ActivityMainBinding binding;
    GridLayoutManager manager;
    PhotoSearchViewModel viewModel;
    PhotoSearchAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        searchByUser();

        viewModel = new ViewModelProvider(this).get(PhotoSearchViewModel.class);
        performSearch("nyc", 1);
        setUpRecyclerView();
        observeUpdatedPhotos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.searchView.clearFocus();

    }

    //Searching by entering tag by user
    private void searchByUser() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query, 1);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                binding.gridRecyclerView.scrollToPosition(0);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    //Observing any update in the photo change
    private void observeUpdatedPhotos() {
        viewModel.getmPhotos().observe(this, new Observer<List<PhotoModel>>() {
            @Override
            public void onChanged(List<PhotoModel> photoModels) {
                //observe any data change
                if (photoModels != null) {
                    for (PhotoModel photoModel : photoModels) {
                        Log.v("Updated data", "onChanged : " + photoModel.getTitle());
                        adapter.setPhotoModelList(photoModels);

                    }
                }
            }
        });

    }

    private void setUpRecyclerView() {
        adapter = new PhotoSearchAdapter(this::onPhotoClick);
        manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        binding.gridRecyclerView.setLayoutManager(manager);
        binding.gridRecyclerView.setAdapter(adapter);

        //Pagination : load next page on next page
        binding.gridRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                if (!binding.gridRecyclerView.canScrollVertically(1)) {

                    viewModel.loadNextPage();
                }
            }
        });
    }

    @Override
    public void onPhotoClick(int position) {
        Intent intent = new Intent(this, PhotoFullscreenActivity.class);
        intent.putExtra(SEND_PHOTO, adapter.getSelectedPhoto(position));
        startActivity(intent);
    }

    private void performSearch(String tag, int page) {

        if (new NetworkConnectionUtil(this).isConnected()) {
            viewModel.searchPhotos(tag, page);
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }

}