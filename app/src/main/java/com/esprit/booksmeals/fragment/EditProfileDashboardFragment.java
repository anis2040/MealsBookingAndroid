package com.esprit.booksmeals.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import booksmeals.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileDashboardFragment extends Fragment {
    EditText name ,email, password;
    TextView lastname;
    Button btnEdit;
    private static final int SELECT_PICTURE = 0;
    private ImageView restoImage;
    private String mSelectedImagePath;
    public View view;

    public EditProfileDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_profile_dashboard, container, false);
        email = view.findViewById(R.id.editEmail);
        name= view.findViewById(R.id.editNom);
        password= view.findViewById(R.id.editPassword);
        lastname= view.findViewById(R.id.lastname);
        btnEdit= view.findViewById(R.id.btnEdit);

        return view;
    }

}
