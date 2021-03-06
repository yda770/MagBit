package com.example.yehuda_da.magbit;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaActionSound;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yehuda_da.magbit.Controllers.MagbitController;
import com.example.yehuda_da.magbit.dummy.DummyContent;
import com.example.yehuda_da.magbit.dummy.DummyContent.DummyItem;
import com.example.yehuda_da.magbit.models.Charity;
import com.example.yehuda_da.magbit.models.Magbit;
import com.example.yehuda_da.magbit.models.Rate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MaibItitemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<Magbit> MagbitList;
    RecyclerView recyclerView;
    private ProgressDialog progressDialog;
//    private int lastFirstVisiblePosition;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MaibItitemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MaibItitemFragment newInstance() {
        MaibItitemFragment fragment = new MaibItitemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab =  getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, MabitFragment.newInstance(null))
                        .addToBackStack(null)
//                        .addToBackStack(MabitFragment.class.getSimpleName())
                        .commit();

                        FloatingActionButton fab =  getActivity().findViewById(R.id.fab);
                        fab.setVisibility(View.INVISIBLE);



            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        progressDialog = new ProgressDialog(container.getContext());

        View view = inflater.inflate(R.layout.fragment_maibititem_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }


            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("magbits");

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(MagbitList == null) {
                        MagbitList = new ArrayList<>();
                    }

                    for (DataSnapshot magbitdata: dataSnapshot.getChildren())
                    {

                        Magbit magbit = magbitdata.getValue(Magbit.class);

                        int rateCount = 0;
                        float avg = 0;
                        for (DataSnapshot rates:  magbitdata.child("Rates").getChildren())
                        {
                            Rate rate = rates.getValue(Rate.class);
                            avg += rate.getRate();
                            rateCount++;
                        }

                        if (rateCount > 0)
                        {
                            magbit.setRate_avg(avg / rateCount);
                        }

                        magbit.setRate_num(rateCount);

                        boolean isExist = false;
                        for (Magbit magbit1 : MagbitList)
                        {
                            if (magbit1.getId() == magbit.getId())
                            {
                                magbit1.setRate_num(magbit.getRate_num());
                                magbit1.setRate_avg(magbit.getRate_avg());
                                magbit1.setDescription(magbit.getDescription());
                                magbit1.setName(magbit.getName());
                                isExist = true;
                            }
                        }

                        if (isExist == false) {
                            MagbitList.add(magbit);
                        }

                    }

                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(new MyMaibItitemRecyclerViewAdapter(MagbitList, mListener));
                    progressDialog.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });


        }
        return view;


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStop() {
        super.onStop();

//        lastFirstVisiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        FloatingActionButton fab =  getActivity().findViewById(R.id.fab);
        if (fab != null) {
            fab.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);


    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Magbit item);

    }

}
