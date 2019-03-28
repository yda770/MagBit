package com.example.yehuda_da.magbit.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Magbit {
    private String Id;
    private String Name;
    private String Description;
    private String ImageUrl;
    private String UserId;
    private List<Integer> Rates;
    private Bitmap my_image;

    public Magbit() {
    }

    public Magbit(String name, String description, String userId) {
        Name = name;
        Description = description;
        UserId = userId;
    }

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

    public void setRates(List<Integer> rates) {
        Rates = rates;
    }

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

    public List<Integer> getRates() {
        return Rates;
    }

    public String getUserId() {
        return UserId;
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

    public Bitmap getImage() {



        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mStorageRef = mStorageRef.child("images/" + this.getId() + ".jpg");
        try {
            final File localFile = File.createTempFile("Images", "bmp");
            mStorageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener< FileDownloadTask.TaskSnapshot >() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    my_image = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return my_image;
    }
}
