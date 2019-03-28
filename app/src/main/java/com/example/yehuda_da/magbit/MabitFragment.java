package com.example.yehuda_da.magbit;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.yehuda_da.magbit.Controllers.MagbitController;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MabitFragment extends Fragment {

    private static final int PICK_IMAGE = 345;
    private static final int REQUEST_IMAGE_CAPTURE = 346;

    private String tweetTag;

    private OnFragmentInteractionListener mListener;
    private Uri imageUri;
    private ProgressBar loader;
    private ImageView new_maigmit_image;

    private String currentPhotoPath;

    private MabitViewModel mViewModel;
    View MagbitView;
    public static MabitFragment newInstance() {
        return new MabitFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mabit_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(MabitViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MagbitView = view;
        new_maigmit_image = view.findViewById(R.id.magbit_image_create);
        FloatingActionButton fab = view.findViewById(R.id.create_magbit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MagbitController.createMagbit(MagbitView);
            }
        });


        FloatingActionButton fab_image = view.findViewById(R.id.get_photo);
        fab_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.choose_image))
                        .setMessage(R.string.choose_image_message)
                        .setPositiveButton(getString(R.string.camera), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                    // Create the File where the photo should go
                                    File photoFile = null;
                                    try {
                                        photoFile = createImageFile();
                                    } catch (IOException ex) {
                                        // Error occurred while creating the File
                                    }
                                    // Continue only if the File was successfully created
                                    if (photoFile != null) {
                                        imageUri = FileProvider.getUriForFile(getActivity(),
                                                "com.example.android.fileprovider",
                                                photoFile);
                                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                                    }
                                }

                            }
                        }).setNegativeButton(getString(R.string.gallery), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                            }
                        }).create();
                alertDialog.show();
            }
        });

    }



        private File createImageFile() throws IOException {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = image.getAbsolutePath();
            return image;
        }

    public interface OnFragmentInteractionListener {


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            Glide.with(this).load(imageUri).into(new_maigmit_image);
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            if (imageUri != null) {
                Glide.with(this).load(imageUri).into(new_maigmit_image);
            }
        }
    }
}