package com.example.flickflix.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flickflix.R;
import com.example.flickflix.model.Review;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ReviewListAdapter extends
        RecyclerView.Adapter<ReviewListAdapter.ReviewListViewHolder> {

    private List<Review> mReviewList;

    public List<Review> getmReviewList() {
        return mReviewList;
    }

    private LayoutInflater mInflater;

    public ReviewListAdapter(Context context, List<Review> mReviewList) {
        mInflater = LayoutInflater.from(context);
        this.mReviewList = mReviewList;
    }

    public void setmReviewList(List<Review> reviews){
        this.mReviewList = reviews;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // ViewHolder aanmaken

        // Inflate an item view.
        View mItemView = mInflater.inflate(R.layout.review_list_item, parent, false);
        return new ReviewListViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewListViewHolder holder, int position) {
        Review mReview = mReviewList.get(position);
        holder.tvReviewAuthor.setText(mReview.getAuthor());
        holder.tvReviewRating.setText(mReview.getAuthorDetails().getRating());
        holder.tvReviewContent.setText(mReview.getContent());
    }

    @Override
    public int getItemCount() {
        if(mReviewList != null) {
            return mReviewList.size();
        } else return 0;
    }

    class ReviewListViewHolder
            extends RecyclerView.ViewHolder
    {

        private final String LOG_TAG = this.getClass().getSimpleName();
        public TextView tvReviewAuthor;
        public TextView tvReviewRating;
        public TextView tvReviewContent;

        public ReviewListViewHolder(@NonNull View itemView, ReviewListAdapter reviewListAdapter) {
            super(itemView);
            tvReviewAuthor.findViewById(R.id.review_list_author);
            tvReviewRating.findViewById(R.id.review_list_rating);
            tvReviewContent.findViewById(R.id.review_list_content);
        }

    }

}
