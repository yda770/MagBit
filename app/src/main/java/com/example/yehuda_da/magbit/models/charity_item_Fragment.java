package com.example.yehuda_da.magbit.models;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.example.yehuda_da.magbit.CharityFragment;
import com.example.yehuda_da.magbit.MabitFragment;
import com.example.yehuda_da.magbit.Main2Activity;
import com.example.yehuda_da.magbit.MyCharityRecyclerViewAdapter;
import com.example.yehuda_da.magbit.MyMaibItitemRecyclerViewAdapter;
import com.example.yehuda_da.magbit.R;
import com.example.yehuda_da.magbit.models.dummy.DummyContent;
import com.example.yehuda_da.magbit.models.dummy.DummyContent.DummyItem;
import com.google.firebase.auth.FirebaseAuth;
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
public class charity_item_Fragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<magbit_charity> CharitiesList;
    private static Magbit mMagbit;
    private ProgressDialog progressDialog;
    RecyclerView recyclerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public charity_item_Fragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static charity_item_Fragment newInstance(Magbit magbit) {
        charity_item_Fragment fragment = new charity_item_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        mMagbit = magbit;
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
                        .replace(R.id.content, CharityFragment.newInstance(mMagbit))
                        .addToBackStack(MabitFragment.class.getSimpleName())
                        .commit();

                FloatingActionButton fab =  getActivity().findViewById(R.id.fab);
                fab.setVisibility(View.INVISIBLE);

            }
        });

            }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charity_list, container, false);
        CharitiesList  = new ArrayList<>();
        progressDialog = new ProgressDialog(container.getContext());

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
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("magbits").child(mMagbit.getId()).child("Charities");

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    CharitiesList  = new ArrayList<>();
                    CharitiesList.add(new magbit_charity(null, mMagbit,true,false));
                    for (DataSnapshot charitydata: dataSnapshot.getChildren())
                    {
                        Charity charity = charitydata.getValue(Charity.class);
                        magbit_charity magbit_charity = new magbit_charity(charity,null,false,true);

                        CharitiesList.add(magbit_charity);
                    }

                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(new MyCharityRecyclerViewAdapter(CharitiesList, mListener));
                    progressDialog.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });

            recyclerView.setAdapter(new MyCharityRecyclerViewAdapter(CharitiesList, mListener));

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
        FloatingActionButton fab =  getActivity().findViewById(R.id.fab);
        if (fab != null){
        fab.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (mMagbit.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
        {
            MenuItem Edit =  menu.findItem(R.id.action_edit);
            Edit.setVisible(true);
            Edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, MabitFragment.newInstance(mMagbit))
//                            .addToBackStack(MabitFragment.class.getSimpleName())
                            .addToBackStack(null)
                            .commit();

                    menuItem.setVisible(false);

                    return false;
                }
            });
        }
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
        void onListFragmentInteraction(magbit_charity item);

    }
}
