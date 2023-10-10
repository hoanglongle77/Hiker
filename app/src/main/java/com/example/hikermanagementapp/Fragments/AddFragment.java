package com.example.hikermanagementapp.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.hikermanagementapp.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddFragment extends Fragment {
    EditText inputName, inputLocation, inputDate, inputDescription, inputLength;
    Spinner spinnerLevel;
    ArrayAdapter<String> spinnerAdapter;
    RadioGroup parkingRadioGroup;
    String parkingValue;
    Button btnAdd,btnReset;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        // Initialize radio group for parking option
        parkingRadioGroup = view.findViewById(R.id.parking_radio_group);
        parkingRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = view.findViewById(checkedId);
            if (selectedRadioButton != null) {
                parkingValue = selectedRadioButton.getText().toString();
            }
        });

        // Initialize spinner for level of difficulty
        spinnerLevel = view.findViewById(R.id.spinner_level);
        spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.difficulty_levels));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLevel.setAdapter(spinnerAdapter);


        // Initialize date picker
        inputDate = view.findViewById(R.id.field_date);
        inputDate.setOnClickListener(v -> {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getChildFragmentManager(), "Date Picker");
        });

        // Button add event
        btnAdd = view.findViewById(R.id.button_add);

        // Button reset event
        btnReset = view.findViewById(R.id.button_reset);
        btnReset.setOnClickListener(v -> resetAllFields());

        return view;
    }

    // Class DatePickerFragment
    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            LocalDate d = LocalDate.now();
            int year = d.getYear();
            int month = d.getMonthValue();
            int day = d.getDayOfMonth();
            return new DatePickerDialog(getActivity(), this, year, --month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            LocalDate dob = LocalDate.of(year, ++month, dayOfMonth);
            assert getParentFragment() != null;
            ((AddFragment) getParentFragment()).sendToDOB(dob);
        }
    }

    // Format date in edit text
    private String formatDate(LocalDate dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dob.format(formatter);
    }

    // Update edit text after user chose a date
    public void sendToDOB(LocalDate dob) {
        EditText dateOfHike = requireView().findViewById(R.id.field_date);
        dateOfHike.setText(formatDate(dob));
    }

    public void resetAllFields() {
        inputName = requireView().findViewById(R.id.field_name);
        inputLocation = requireView().findViewById(R.id.field_location);
        inputDate = requireView().findViewById(R.id.field_date);
        parkingRadioGroup = requireView().findViewById(R.id.parking_radio_group);
        inputLength = requireView().findViewById(R.id.field_length);
        spinnerLevel = requireView().findViewById(R.id.spinner_level);
        inputDescription = requireView().findViewById(R.id.field_description);

        inputName.setText("");
        inputLocation.setText("");
        inputDate.setText("");
        parkingRadioGroup.clearCheck();
        inputLength.setText("");
        spinnerLevel.setSelection(0);
        inputDescription.setText("");
    }

    // Method to get values from all input fields
    public ArrayList<String> getInputValues() {
        ArrayList<String> hikeDetails = new ArrayList<>();

        inputName = requireView().findViewById(R.id.field_name);
        inputLocation = requireView().findViewById(R.id.field_location);
        inputDate = requireView().findViewById(R.id.field_date);
        inputLength = requireView().findViewById(R.id.field_length);
        inputDescription = requireView().findViewById(R.id.field_description);

        String name = inputName.getText().toString();
        String location = inputLocation.getText().toString();
        String date = inputDate.getText().toString();
        String parking = getParkingOptionValues();
        String length = inputLength.getText().toString();
        String level = getLevelValues();
        String description = inputDescription.getText().toString();

        hikeDetails.add(0, name);
        hikeDetails.add(1, location);
        hikeDetails.add(2, date);
        hikeDetails.add(3, parking);
        hikeDetails.add(4, length);
        hikeDetails.add(5, level);
        hikeDetails.add(6, description);

        return hikeDetails;
    }


    // Method to get value of parking radio button
    public String getParkingOptionValues() {
        return parkingValue;
    }

    // Method to get value of level spinner
    public String getLevelValues() {
        spinnerLevel = requireView().findViewById(R.id.spinner_level);
        if (spinnerLevel != null && spinnerLevel.getSelectedItem() != null) {
            return spinnerLevel.getSelectedItem().toString();
        }
        return null;
    }

}