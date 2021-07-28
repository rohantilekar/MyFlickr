package rst.com.myflickr.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rst.com.myflickr.R;
import rst.com.myflickr.ui.OnPhotoClickListener;
import rst.com.myflickr.databinding.PhotoItemBinding;
import rst.com.myflickr.models.PhotoModel;

public class PhotoSearchAdapter extends RecyclerView.Adapter<PhotoSearchAdapter.PhotoViewHolder> {

    private List<PhotoModel> photoModelList;
    private final OnPhotoClickListener onPhotoClickListener;

    public PhotoSearchAdapter(OnPhotoClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        PhotoItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.photo_item,
                parent,
                false);

        return new PhotoViewHolder(binding, onPhotoClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PhotoViewHolder holder, int position) {
        PhotoModel photoModel = photoModelList.get(position);
        holder.binding.setPhotoModel(photoModel);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (photoModelList != null) return photoModelList.size();

        return 0;
    }

    public void setPhotoModelList(List<PhotoModel> photoModelList) {
        this.photoModelList = photoModelList;
        notifyDataSetChanged();
    }

    public PhotoModel getSelectedPhoto(int position) {
        if (photoModelList != null) {
            if (photoModelList.size() > 0) {
                return photoModelList.get(position);
            }
        }
        return null;
    }


    public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        PhotoItemBinding binding;
        OnPhotoClickListener photoClickListener;

        public PhotoViewHolder(PhotoItemBinding binding, OnPhotoClickListener photoClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.photoClickListener = photoClickListener;

            binding.imageView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            photoClickListener.onPhotoClick(getAdapterPosition());

        }
    }

}
