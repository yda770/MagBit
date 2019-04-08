package com.example.yehuda_da.magbit.Controllers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yehuda_da.magbit.R;
import com.example.yehuda_da.magbit.models.Charity;
import com.example.yehuda_da.magbit.models.Magbit;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class CharityController {


    public static void createCharity(View view, Magbit magbit){


        TextView HonorText = view.findViewById(R.id.honor_text);
        TextView AmountText = view.findViewById(R.id.amount);
        Charity charity = new Charity(Integer.parseInt(AmountText.getText().toString()),HonorText.getText().toString());
        charity.create(magbit);


    }

    public static List<Magbit> GetMagbitList() {


        List<Magbit> MagbitList = new ArrayList<>();

        return MagbitList;
    }
}
