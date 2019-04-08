package com.example.yehuda_da.magbit;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yehuda_da.magbit.models.Magbit;
import com.example.yehuda_da.magbit.models.Rate;
import com.example.yehuda_da.magbit.models.charity_item_Fragment.OnListFragmentInteractionListener;
import com.example.yehuda_da.magbit.models.dummy.DummyContent.DummyItem;
import com.example.yehuda_da.magbit.models.magbit_charity;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCharityRecyclerViewAdapter extends RecyclerView.Adapter<MyCharityRecyclerViewAdapter.ViewHolder> {

    private final List<magbit_charity> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyCharityRecyclerViewAdapter(List<magbit_charity> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_charity_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        if (mValues.get(position).isIs_magbit())
        {
            holder.mCharityContainet.setVisibility(View.GONE);
            holder.mMagbitContainet.setVisibility(View.VISIBLE);
            holder.mMagbitImage.setImageBitmap(mValues.get(position).getMagbit().getMy_image());
            holder.mMagbitName.setText(mValues.get(position).getMagbit().getName());
            holder.mMagbitDesc.setText(mValues.get(position).getMagbit().getDescription());


            holder.mMagbitRate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    mValues.get(position).getMagbit().Rate(ratingBar.getRating());
                }
            });

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("magbits")
                    .child(mValues.get(position).getMagbit().getId())
                    .child("Rates");

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ratingdata: dataSnapshot.getChildren())
                    {
                        Rate rate = ratingdata.getValue(Rate.class);
                        if (rate.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                        {
                            holder.mMagbitRate.setRating(rate.getRate());
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

//            holder.mMagbitRate.setRating(mValues.get(position).getMagbit().getCalcRate());
        }

        if (mValues.get(position).isIs_charity())
        {
            holder.mCharityContainet.setVisibility(View.VISIBLE);
            holder.mMagbitContainet.setVisibility(View.GONE);
            holder.mCharityAmount.setText(String.valueOf(mValues.get(position).getCharity().getAmount()));
            holder.mCharityUserName.setText(mValues.get(position).getCharity().getUserName());
            holder.mCharityHonor.setText(mValues.get(position).getCharity().getToHonor());

            Glide.with(holder.mCharityUserImage.getContext())
                    .load(mValues.get(position).getCharity().getUserImageUrl())
                    .apply(RequestOptions.circleCropTransform()).into(holder.mCharityUserImage);
        }

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
        public final TextView mMagbitName;
        public final TextView mMagbitDesc;
        public final ImageView mMagbitImage;
        public final RatingBar mMagbitRate;
        public final ImageView mCharityUserImage;
        public final TextView mCharityUserName;
        public final TextView mCharityHonor;
        public final TextView mCharityAmount;
        public final ConstraintLayout mCharityContainet;
        public final ConstraintLayout mMagbitContainet;

        public magbit_charity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMagbitName = (TextView)view.findViewById(R.id.magbit_name);
            mMagbitDesc =  (TextView)view.findViewById(R.id.magbit_desc);
            mMagbitImage = (ImageView) view.findViewById(R.id.magbit_image);
            mMagbitRate = (RatingBar) view.findViewById(R.id.magbit_rating);
            mCharityUserImage = (ImageView) view.findViewById(R.id.user_image);
            mCharityUserName =  (TextView) view.findViewById(R.id.user_name);
            mCharityHonor =  (TextView) view.findViewById(R.id.honot_text);
            mCharityAmount =  (TextView) view.findViewById(R.id.amount_view);
            mCharityContainet = (ConstraintLayout)view.findViewById(R.id.charity_item);
            mMagbitContainet = (ConstraintLayout)view.findViewById(R.id.magbit_item);
        }

        @Override
        public String toString() {
            return super.toString() + " '"  + "'";
        }
    }
}
