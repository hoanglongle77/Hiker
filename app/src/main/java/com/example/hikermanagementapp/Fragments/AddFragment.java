package com.example.hikermanagementapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.hikermanagementapp.R;

import java.util.ArrayList;

public class AddFragment extends Fragment {
    EditText inputName,inputLocation,inputDate,inputDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    public void resetAllFields(){
        ArrayList<String> hikeDetails = new ArrayList<>();
        inputName = requireView().findViewById(R.id.field_name);
        inputLocation = requireView().findViewById(R.id.field_location);
        inputDate = requireView().findViewById(R.id.field_date);
        // 3 fields need to implement Parking,Length,Level
        inputDescription = requireView().findViewById(R.id.field_description);

        inputName.setText("");
        inputLocation.setText("");
        inputDate.setText("");
        // 3 fields need to implement Parking,Length,Level
        inputDescription.setText("");
    }

    public ArrayList<String> getInputValues(){
        ArrayList<String> hikeDetails = new ArrayList<>();
        inputName = requireView().findViewById(R.id.field_name);
        inputLocation = requireView().findViewById(R.id.field_location);
        inputDate = requireView().findViewById(R.id.field_date);
        // 3 fields need to implement Parking,Length,Level
        inputDescription = requireView().findViewById(R.id.field_description);

        String name = inputName.getText().toString();
        String location = inputLocation.getText().toString();
        String date = inputDate.getText().toString();
        // 3 fields need to implement Parking,Length,Level
        String description = inputDescription.getText().toString();

        hikeDetails.add(0,name);
        hikeDetails.add(1,location);
        hikeDetails.add(2,date);
//        hikeDetails.add(3,parking);
//        hikeDetails.add(4,length);
//        hikeDetails.add(5,level);
        hikeDetails.add(6,description);

        return hikeDetails;
    }
}