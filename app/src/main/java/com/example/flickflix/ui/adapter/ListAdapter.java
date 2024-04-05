package com.example.flickflix.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flickflix.R;
import com.example.flickflix.model.MovieList;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private final ArrayList<MovieList> results = new ArrayList<>();
    private Boolean isLoadingAdded = false;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View mItemView = inflater.inflate(R.layout.list_list_item, parent, false);
                viewHolder = new ListVH(mItemView);
                break;
            case LOADING:
                View mItemViewLoading = inflater.inflate(R.layout.movie_item_progress, parent, false);
                viewHolder = new LoadingVH(mItemViewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieList movieList = results.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final ListVH listVH = (ListVH) holder;

                // Show the info in the UI
                listVH.tvListName.setText(movieList.getName());
                listVH.tvListDescription.setText(movieList.getDescription());

                break;
            case LOADING:
                // Do nothing
                break;
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == results.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /**
     * Helpers
     */
    public void add(MovieList movieList) {
        results.add(movieList);
        notifyItemInserted(results.size() - 1);
    }

    public void addAll(List<MovieList> movieLists) {
        for (MovieList movieList : movieLists) {
            add((MovieList) movieList);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new MovieList());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = results.size() - 1;
        MovieList list = getItem(position);

        if (list != null) {
            results.remove(position);
            notifyItemRemoved(position);
        }
    }

    public MovieList getItem(int position) {
        return results.get(position);
    }

    protected static class ListVH extends RecyclerView.ViewHolder {
        public TextView tvListName;
        public TextView tvListDescription;

        public ListVH(View itemView) {
            super(itemView);
            tvListName = itemView.findViewById(R.id.tv_list_name);
            tvListDescription = itemView.findViewById(R.id.tv_list_description);
        }
    }

    protected static class LoadingVH extends RecyclerView.ViewHolder {
        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
