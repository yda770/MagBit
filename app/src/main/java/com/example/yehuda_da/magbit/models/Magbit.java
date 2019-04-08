package com.example.yehuda_da.magbit.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Magbit {
    private String Id;
    private String Name;
    private String Description;
    private String ImageUrl;
    private String UserId;
    @Exclude private Bitmap my_image ;
    @Exclude private float rate_avg;
    @Exclude private int rate_num;
//    @Exclude private List<Rate> Rates = new ArrayList<>();
//    @Exclude private List<Charity> Charities = new ArrayList<>();

    public Magbit() {
    }

    public Magbit(String name, String description, String userId) {
        Name = name;
        Description = description;
        UserId = userId;
    }


    public int getRate_num() {
        return rate_num;
    }

    public void setRate_num(int rate_num) {
        this.rate_num = rate_num;
    }

    public float getRate_avg() {
        return rate_avg;
    }

    public void setRate_avg(float rate_avg) {
        this.rate_avg = rate_avg;
    }

    //    public List<Charity> getCharities() {
//        return Charities;
//    }
//
//
//    public void setCharities(List<Charity> charities) {
//        Charities = charities;
//    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

//    public void setRates(List<Rate> rates) {
//        Rates = rates;
//    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

//    public List<Rate> getRates() {
//        return Rates;
//    }

    public String getUserId() {
        return UserId;
    }

    public Bitmap getMy_image() {
        return my_image;
    }

    public void setMy_image(Bitmap my_image) {
        this.my_image = my_image;
    }

    public void create(ImageView image) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("magbits");
        this.setId(myRef.push().getKey());
        myRef.child(this.getId()).setValue(this);

        // Get the data from an ImageView as bytes
        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        this.ImageUrl = "images/" + this.getId() + ".jpg";
        StorageReference Ref = mStorageRef.child(this.ImageUrl);
        image.setDrawingCacheEnabled(true);
        image.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = Ref.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    taskSnapshot.getMetadata().getPath();
                }
            });
        }
        catch (Exception e)
        {

        }



    }

    public void Change(ImageView image)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("magbits");
        myRef.child(this.getId()).child("name").setValue(this.getName());
        myRef.child(this.getId()).child("description").setValue(this.getDescription());

        // Get the data from an ImageView as bytes
        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        this.ImageUrl = "images/" + this.getId() + ".jpg";
        StorageReference Ref = mStorageRef.child(this.ImageUrl);
        image.setDrawingCacheEnabled(true);
        image.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        this.setMy_image(bitmap);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = Ref.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    taskSnapshot.getMetadata().getPath();
                }
            });
        }
        catch (Exception e)
        {

        }


    }

    public void Rate(float rating) {
        Rate rate = new Rate(FirebaseAuth.getInstance().getCurrentUser().getUid(),rating);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("magbits").child(this.getId()).child("Rates");
        myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(rate);
    }

    public float getRate() {
        float Rate;

        Rate = 0;
        return Rate;
    }



//    public Bitmap getImage() {
//
//        StorageReference mStorageRef;
//        mStorageRef = FirebaseStorage.getInstance().getReference();
//        mStorageRef = mStorageRef.child("images/" + this.getId() + ".jpg");
//        try {
//            final File localFile = File.createTempFile("Images", "bmp");
//            mStorageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener< FileDownloadTask.TaskSnapshot >() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    my_image = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return my_image;
//    }
//
//    public float getCalcRate()
//    {
//        float CalceRate = 0;
//
//        for (Rate rate : Rates)
//        {
//            CalceRate += rate.getRate();
//        }
//
//        CalceRate /= Rates.size();
//
//        return CalceRate;
//    }

}
