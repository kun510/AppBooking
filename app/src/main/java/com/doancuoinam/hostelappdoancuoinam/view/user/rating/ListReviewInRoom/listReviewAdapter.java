package com.doancuoinam.hostelappdoancuoinam.view.user.rating.ListReviewInRoom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Review;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.user.room.listImgInRoom.AdapterOverview;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class listReviewAdapter extends RecyclerView.Adapter<listReviewAdapter.ViewHolder> {
    List<Review> reviewList;

    public void setData(List<Review> reviewList) {
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public listReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new listReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listReviewAdapter.ViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.onBind(review);
    }

    @Override
    public int getItemCount() {
        return reviewList != null ? reviewList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView avtProfile;
        TextView nameUser,contentReview,timeReview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avtProfile = itemView.findViewById(R.id.avtProfile);
            nameUser = itemView.findViewById(R.id.nameUser);
            contentReview = itemView.findViewById(R.id.contentReview);
            timeReview = itemView.findViewById(R.id.timeReview);
        }
        void onBind(Review review){
            String avt = review.getUser().getImg();
            Picasso.get().load(avt).into(avtProfile);
            nameUser.setText(review.getUser().getName());
            contentReview.setText(review.getEvaluate());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            String formattedDate = sdf.format(review.getDate());
            timeReview.setText(formattedDate);
        }
    }
}
