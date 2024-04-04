package com.example.flickflix.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flickflix.R;
import com.example.flickflix.model.Review;

import java.util.List;

import io.noties.markwon.Markwon;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewListViewHolder> {
    private List<Review> mReviewList;
    private LayoutInflater mInflater;

    public ReviewListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void seMReviewList(List<Review> reviews){
        this.mReviewList = reviews;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // ViewHolder aanmaken
        View mItemView = mInflater.inflate(R.layout.review_list_item, parent, false);

        // Inflate an item view.
        return new ReviewListViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewListViewHolder holder, int position) {
        Review mReview = mReviewList.get(position);

        holder.tvReviewAuthor.setText(mReview.getAuthor());
        holder.tvReviewRating.setText(mReview.getAuthorDetails().getRating());

        // Convert the markdown content
        Markwon markwon = Markwon.create(mInflater.getContext());
        markwon.setMarkdown(holder.tvReviewContent, mReview.getContent());
    }

    @Override
    public int getItemCount() {
        if(mReviewList != null) {
            return mReviewList.size();
        } else {
            return 0;
        }
    }

    static class ReviewListViewHolder extends RecyclerView.ViewHolder {
        public TextView tvReviewAuthor;
        public TextView tvReviewRating;
        public TextView tvReviewContent;

        public ReviewListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvReviewAuthor = itemView.findViewById(R.id.review_list_author);
            tvReviewRating = itemView.findViewById(R.id.review_list_rating);
            tvReviewContent = itemView.findViewById(R.id.review_list_content);
        }
    }
}
