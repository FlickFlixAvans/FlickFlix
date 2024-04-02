package com.example.flickflix.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flickflix.R;
import com.example.flickflix.data.model.Genre;
import com.example.flickflix.data.model.List;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private final List<List> results = new ArrayList<>();
    private Boolean isLoadingAdded = false;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                // Return the List ViewHolder
                View mItemView = inflater.inflate(R.layout.list_list_item, parent, false);
                viewHolder = new ListVH(mItemView);
                break;
            case LOADING:
                // Return the Loading ViewHolder
                View mitemview = inflater.inflate(R.layout.list_item_progress, parent, false);
                viewHolder = new LoadingVH(mitemview);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        List mList = results.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final ListVH listVH = (ListVH) holder;

                // Show the info in the UI
                listVH.tvListName.setText(mList.getName());
                listVH.tvListDescription.setText(mList.getDescription());

                break;
            case LOADING:
                // Do nothing
                break;
        }
    }

    @Override
    public int getItemCount() {
        return results == null ? 0 : results.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == results.size() - 1 && isLoadingAdded) ? 1 : 0;
    }

    /**
     * Helpers
     */
    public void add(List list) {
        results.add(list);
        notifyItemInserted(results.size() - 1);
    }

    public void addAll(List<List> lists) {
        for (List list : lists) {
            add(list);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new List());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = results.size() - 1;
        List list = getItem(position);

        if (list != null) {
            results.remove(position);
            notifyItemRemoved(position);
        }
    }

    public List getItem(int position) {
        return results.get(position);
    }

    /**
     * View Holders
     */
    protected static class ListVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvListName;
        public TextView tvListDescription;

        public ListVH(View itemView) {
            super(itemView);

            tvListName = itemView.findViewById(R.id.tv_list_name);
            tvListDescription = itemView.findViewById(R.id.tv_list_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Do nothing
        }
    }

    protected static class LoadingVH extends RecyclerView.ViewHolder {
        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}