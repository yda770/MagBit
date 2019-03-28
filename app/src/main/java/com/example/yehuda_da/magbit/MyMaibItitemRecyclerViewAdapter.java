package com.example.yehuda_da.magbit;

import android.media.Rating;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yehuda_da.magbit.MaibItitemFragment.OnListFragmentInteractionListener;
import com.example.yehuda_da.magbit.dummy.DummyContent.DummyItem;
import com.example.yehuda_da.magbit.models.Magbit;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMaibItitemRecyclerViewAdapter extends RecyclerView.Adapter<MyMaibItitemRecyclerViewAdapter.ViewHolder> {

    private final List<Magbit> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyMaibItitemRecyclerViewAdapter(List<Magbit> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_maibititem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId());
        holder.mMagbitName.setText(mValues.get(position).getName());
        holder.mMagbitDesc.setText(mValues.get(position).getDescription());
        holder.mMagbitImage.setImageBitmap(mValues.get(position).getImage());


//        Glide.with(holder.mMagbitImage.getContext())
//                .load(mValues.get(position).getImage())
//                .apply(RequestOptions.circleCropTransform())
//                .into(holder.mMagbitImage);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mMagbitImage;
        public final TextView mMagbitName;
        public final TextView mMagbitDesc;
        public final RatingBar mRating;
        public final TextView mIdView;
        public final TextView mContentView;
        public Magbit mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMagbitImage = (ImageView) view.findViewById(R.id.magbit_image);
            mMagbitName = (TextView) view.findViewById(R.id.magbit_name_item);
            mMagbitDesc = (TextView) view.findViewById(R.id.magbit_desc_item);
            mRating = (RatingBar) view.findViewById(R.id.magbit_rating);
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
