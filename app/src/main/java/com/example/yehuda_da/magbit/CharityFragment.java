package com.example.yehuda_da.magbit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.example.yehuda_da.magbit.Controllers.CharityController;
import com.example.yehuda_da.magbit.Controllers.MagbitController;
import com.example.yehuda_da.magbit.models.Magbit;

import javax.xml.transform.Templates;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CharityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CharityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharityFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static  Magbit mMagbit;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View CharityView;
    private OnFragmentInteractionListener mListener;

    public CharityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CharityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CharityFragment newInstance(Magbit magbit) {
        CharityFragment fragment = new CharityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        mMagbit = magbit;
        return fragment;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CharityView = view;
        FloatingActionButton fab = view.findViewById(R.id.create_charity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit = (EditText) CharityView.findViewById(R.id.amount);
                int num = Integer.parseInt(edit.getText().toString());
                if (num == 0){
                    Snackbar.make(view, "חובה להזין סכום", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    CharityController.createCharity(CharityView, mMagbit);

                    Snackbar.make(view, "תודה על תרומתך!!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    getFragmentManager().popBackStackImmediate();
                }
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_charity, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
