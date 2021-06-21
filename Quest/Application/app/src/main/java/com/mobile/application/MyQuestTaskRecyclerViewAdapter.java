package com.mobile.application;

import android.app.Application;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.application.database.QuestTaskItem;
import com.mobile.application.databinding.QuestTaskItemBinding;

import java.util.List;
import java.util.zip.Inflater;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyQuestTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyQuestTaskRecyclerViewAdapter.ViewHolder> {

    private final List<QuestTaskItem> mValues;
    private Inflater FragmentItemBinding;
    private QuestTasksActivity.OnListFragmentInteractionListener mListener;

    public MyQuestTaskRecyclerViewAdapter(List<QuestTaskItem> items, QuestTasksActivity.OnListFragmentInteractionListener mListener) {
        mValues = items;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(QuestTaskItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.questTaskItem = mValues.get(position);
        holder.binding.itemTitle.setText(holder.questTaskItem.number + ". " + holder.questTaskItem.title);
        holder.binding.itemDescription.setText(holder.questTaskItem.description);
        holder.binding.itemImage.setImageBitmap(holder.questTaskItem.image);
        holder.binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.questTaskItem.gps_long != 0.0 && holder.questTaskItem.gps_long != 0.0) {
                    Intent intent = new Intent(v.getContext(), MapsActivity.class);
                    intent.putExtra ("gps_long", holder.questTaskItem.gps_long);
                    intent.putExtra ("gps_lat", holder.questTaskItem.gps_lat);
                    v.getContext().startActivity(intent);
                }
            }
        });
        holder.binding.button.setEnabled(holder.questTaskItem.gps_long != 0.0 && holder.questTaskItem.gps_long != 0.0);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public QuestTaskItem questTaskItem;
        public QuestTaskItemBinding binding;

        public ViewHolder(QuestTaskItemBinding binding, QuestTasksActivity.OnListFragmentInteractionListener mListener) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onListFragmentInteraction(ViewHolder.this.questTaskItem);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + binding.itemTitle.getText() + "'";
        }
    }
}