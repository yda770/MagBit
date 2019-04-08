package com.example.yehuda_da.magbit;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;

import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.graphics.BitmapFactory;
import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.LruCache;
import com.example.yehuda_da.magbit.MaibItitemFragment.OnListFragmentInteractionListener;
import com.example.yehuda_da.magbit.dummy.DummyContent.DummyItem;
import com.example.yehuda_da.magbit.models.Magbit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMaibItitemRecyclerViewAdapter extends RecyclerView.Adapter<MyMaibItitemRecyclerViewAdapter.ViewHolder> {

    private final List<Magbit> mValues;
    private final OnListFragmentInteractionListener mListener;
    private LruCache<String, Bitmap> memoryCache;

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId());
        holder.mMagbitName.setText(mValues.get(position).getName());
        holder.mMagbitDesc.setText(mValues.get(position).getDescription());
        holder.mRating.setRating(mValues.get(position).getRate_avg());
        holder.mRateNum.setText("(" + mValues.get(position).getRate_num() + ")");

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {

            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };

        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mStorageRef = mStorageRef.child("images/" + mValues.get(position).getId() + ".jpg");

        Bitmap cache = getBitmapFromMemCache(mValues.get(position).getId());

        if (mValues.get(position).getMy_image() != null)
        {
            holder.mMagbitImage.setImageBitmap(mValues.get(position).getMy_image());
            holder.mMagbitImage.setVisibility(View.VISIBLE);
            holder.mWheel.setVisibility(View.INVISIBLE);
            Glide.with(holder.mMagbitImage.getContext())
                    .load(holder.mMagbitImage.getDrawable())
                    .apply(RequestOptions.centerCropTransform())
                    .into(holder.mMagbitImage);
        }
        else if(cache != null)
        {
            holder.mMagbitImage.setImageBitmap(cache);
            holder.mMagbitImage.setVisibility(View.VISIBLE);
            holder.mWheel.setVisibility(View.INVISIBLE);

            Glide.with(holder.mMagbitImage.getContext())
                .load(holder.mMagbitImage.getDrawable())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.mMagbitImage);

        }


        try {
            final File localFile = File.createTempFile("Images", "bmp");
            mStorageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener< FileDownloadTask.TaskSnapshot >() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    if(holder.mMagbitImage.getContext() != null) {
                        try {
                            holder.mMagbitImage.setImageBitmap(BitmapFactory.decodeFile(localFile.getAbsolutePath()));
                            Glide.with(holder.mMagbitImage.getContext())
                                    .load(holder.mMagbitImage.getDrawable())
                                    .apply(RequestOptions.centerCropTransform())
                                    .into(holder.mMagbitImage);

                            mValues.get(position).setMy_image(BitmapFactory.decodeFile(localFile.getAbsolutePath()));
                            holder.mMagbitImage.setVisibility(View.VISIBLE);
                            holder.mWheel.setVisibility(View.INVISIBLE);

                            addBitmapToMemoryCache(mValues.get(position).getId(),mValues.get(position).getMy_image());
                        }
                        catch (Exception e)
                        {

                        }
                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                    holder.mItem.getId();

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
        public final ImageView mWheel;
        public final TextView mMagbitName;
        public final TextView mMagbitDesc;
        public final RatingBar mRating;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mRateNum;
        public Magbit mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mMagbitImage = (ImageView) view.findViewById(R.id.magbit_image);
            mWheel = (ImageView) view.findViewById(R.id.wheel);
            mMagbitName  = (TextView) view.findViewById(R.id.magbit_name_item);
            mMagbitDesc  = (TextView) view.findViewById(R.id.magbit_desc_item);
            mRating      = (RatingBar) view.findViewById(R.id.magbit_rating);
            mIdView      = (TextView) view.findViewById(R.id.item_number);
            mRateNum     = (TextView) view.findViewById(R.id.num_rates);
            mContentView = (TextView) view.findViewById(R.id.content);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }







    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return memoryCache.get(key);
    }
}
