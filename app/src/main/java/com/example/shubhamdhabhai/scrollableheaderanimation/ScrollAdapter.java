package com.example.shubhamdhabhai.scrollableheaderanimation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubhamdhabhai on 13/01/18.
 */

public class ScrollAdapter extends RecyclerView.Adapter {

    private static final int VIEW_HEADER = 0;
    private static final int VIEW_OTHER = 1;

    List<String> list;

    public ScrollAdapter() {
        list = new ArrayList<>();
    }

    public void add(String item) {
        list.add(item);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_HEADER) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout, parent, false));
        } else {
            return new ScrollViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position != 0) {
            ((ScrollViewHolder) holder).bind(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_HEADER;
        } else {
            return VIEW_OTHER;
        }
    }

    public class ScrollViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ScrollViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.tv_text);
        }

        public void bind(String value) {
            textView.setText(value);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public HeaderViewHolder(View view) {
            super(view);
        }
    }
}

