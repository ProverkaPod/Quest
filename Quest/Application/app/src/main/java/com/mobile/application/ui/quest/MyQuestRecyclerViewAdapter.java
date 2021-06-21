package com.mobile.application.ui.quest;

import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.application.R;
import com.mobile.application.database.QuestItem;
import com.mobile.application.placeholder.PlaceholderContent.PlaceholderItem;
import com.mobile.application.databinding.QuestFragmentItemBinding;

import java.util.List;
import java.util.zip.Inflater;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyQuestRecyclerViewAdapter extends RecyclerView.Adapter<MyQuestRecyclerViewAdapter.ViewHolder> {

    private final List<QuestItem> mValues;
    private Inflater FragmentItemBinding;
    private QuestFragment.OnListFragmentInteractionListener mListener;

    public MyQuestRecyclerViewAdapter(List<QuestItem> items, QuestFragment.OnListFragmentInteractionListener mListener) {
        mValues = items;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(QuestFragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.questItem = mValues.get(position);
        holder.mTitle.setText(holder.questItem.title);
        holder.mImage.setImageBitmap(holder.questItem.image);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTitle;
        public final ImageView mImage;
        public QuestItem questItem;

        public ViewHolder(QuestFragmentItemBinding binding, QuestFragment.OnListFragmentInteractionListener mListener) {
            super(binding.getRoot());
            mTitle = binding.itemTitle;
            mImage = binding.itemImage;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onListFragmentInteraction(ViewHolder.this.questItem);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }
}