package com.example.yehuda_da.magbit.Controllers;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaActionSound;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.example.yehuda_da.magbit.R;
import com.example.yehuda_da.magbit.models.Magbit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class MagbitController {


    public static void createMagbit(View view){


        TextView MagbitName = view.findViewById(R.id.magbit_name);
        TextView MagbitDesc = view.findViewById(R.id.magbit_desc);
        ImageView MagbitImage = view.findViewById(R.id.magbit_image_create);

        Magbit magbit = new Magbit(MagbitName.getText().toString(), MagbitDesc.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getUid());
        magbit.create(MagbitImage);


    }

    public static List<Magbit> GetMagbitList() {


        List<Magbit> MagbitList = new ArrayList<>();

        return MagbitList;
    }
}
