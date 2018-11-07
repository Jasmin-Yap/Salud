package mapp.com.sg.salud.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mapp.com.sg.salud.Activities.MainActivity;
import mapp.com.sg.salud.R;
import mapp.com.sg.salud.model.userData;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile extends Fragment implements View.OnClickListener {

    private Button btnEdit;
    private Button btnDoneEdit;
    private Button btnLogOut;
    private EditText ETname;
    private EditText ETphoneNumber;
    private EditText ETmail;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth firebaseAuth;
    private String userID;

    private void showData(DataSnapshot dataSnapshot) {

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            userData userData = new userData();
            // setting user data
            userData.setName(ds.child(userID).getValue(userData.class).getName());
            userData.setEmail(ds.child(userID).getValue(userData.class).getEmail());
            userData.setPhoneNumber(ds.child(userID).getValue(userData.class).getPhoneNumber());
            ETname.setText(userData.getName());
            ETphoneNumber.setText(userData.getPhoneNumber());
            ETmail.setText(userData.getEmail());

        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public profile() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnEdit = (Button) view.findViewById(R.id.btnEdit);
        btnDoneEdit = (Button)view.findViewById(R.id.btnDoneEdit);
        btnLogOut = (Button) view.findViewById(R.id.btnLogOut);
        ETname = (EditText) view.findViewById(R.id.ETname);
        ETphoneNumber = (EditText) view.findViewById(R.id.ETphNo);
        ETmail = (EditText) view.findViewById(R.id.ETmail);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        btnEdit.setOnClickListener(this);
        btnDoneEdit.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);

        userData userData = new userData();
        userID = firebaseAuth.getCurrentUser().getUid();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // Inflate the layout for this fragment
        return view;
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

    @Override
    public void onClick(View view) {
        Intent i;
                switch (view.getId()){
            case(R.id.btnLogOut):
                i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                break;
            case(R.id.btnEdit):
                ETname.setFocusableInTouchMode(true);
                ETname.setFocusable(true);
                ETname.requestFocus();
                ETphoneNumber.setFocusableInTouchMode(true);
                ETphoneNumber.setFocusable(true);
                btnDoneEdit.setVisibility(View.VISIBLE);
                btnLogOut.setVisibility(View.INVISIBLE);
                btnEdit.setVisibility(View.INVISIBLE);
                break;
            case(R.id.btnDoneEdit):
                ETname.setFocusable(false);
                ETphoneNumber.setFocusable(false);
                btnDoneEdit.setVisibility(View.INVISIBLE);
                btnLogOut.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.VISIBLE);
                String phoneNo = ETphoneNumber.getText().toString();
                myRef.child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("phoneNumber").setValue(phoneNo);
                String Usern = ETname.getText().toString();
                myRef.child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("name").setValue(Usern);
                String email = ETmail.getText().toString();
                myRef.child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("email").setValue(email);
                break;
        }
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
