package com.example.hikermanagementapp.Fragments;

import android.app.AlertDialog;
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

import com.example.hikermanagementapp.Database.HikerAppDatabase;
import com.example.hikermanagementapp.Models.Hike;
import com.example.hikermanagementapp.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddFragment extends Fragment {
    private EditText inputName, inputLocation, inputDate, inputDescription, inputLength;
    private Spinner spinnerLevel;
    private RadioGroup parkingRadioGroup;
    private String parkingValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        // Initialize all components
        inputName = view.findViewById(R.id.field_name);
        inputLocation = view.findViewById(R.id.field_location);
        inputDate = view.findViewById(R.id.field_date);
        parkingRadioGroup = view.findViewById(R.id.parking_radio_group);
        inputLength = view.findViewById(R.id.field_length);
        spinnerLevel = view.findViewById(R.id.spinner_level);
        inputDescription = view.findViewById(R.id.field_description);
        Button btnAdd = view.findViewById(R.id.button_add);


        // Initialize radio group for parking option
        parkingRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = view.findViewById(checkedId);
            if (selectedRadioButton != null) {
                parkingValue = selectedRadioButton.getText().toString();
            }
        });

        // Initialize spinner for level of difficulty
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.difficulty_levels));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLevel.setAdapter(spinnerAdapter);


        // Initialize date picker
        inputDate.setOnClickListener(v -> {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getChildFragmentManager(), "Date Picker");
        });

        // Button add event
        btnAdd.setOnClickListener(v -> {
            if (formValidate()) {
                insertNewHike();
                showAlertDialog("Success", "New hike is saved");
                resetAllFields();
            } else {
                showAlertDialog("Error", "Please enter all fields");
            }
        });

        return view;
    }

    public void resetAllFields() {
        inputName.setText("");
        inputLocation.setText("");
        inputDate.setText("");
        parkingRadioGroup.clearCheck();
        inputLength.setText("");
        spinnerLevel.setSelection(0);
        inputDescription.setText("");
    }

    public boolean formValidate() {
        ArrayList<String> valueToCheck = inputList();
        for (int i = 0; i < valueToCheck.size(); i++) {
            // Access each element using myList.get(i)
            String field = valueToCheck.get(i);
            if (field.isEmpty() && i != 6) {
                return false;
            }
        }
        return true;
    }

    // Method to get value of parking radio button
    public String getParkingOptionValues() {
        return parkingValue;
    }

    // Method to get value of level spinner
    public String getLevelValues() {
        if (spinnerLevel != null && spinnerLevel.getSelectedItem() != null) {
            return spinnerLevel.getSelectedItem().toString();
        }
        return null;
    }

    // Method to get values from all input fields
    public ArrayList<String> inputList() {
        ArrayList<String> inputList = new ArrayList<>();

        String name = inputName.getText().toString();
        String location = inputLocation.getText().toString();
        String date = inputDate.getText().toString();
        String parking = getParkingOptionValues();
        String length = inputLength.getText().toString();
        String level = getLevelValues();
        String description = inputDescription.getText().toString();

        inputList.add(0, name);
        inputList.add(1, location);
        inputList.add(2, date);
        inputList.add(3, parking);
        inputList.add(4, length);
        inputList.add(5, level);
        inputList.add(6, description);

        return inputList;
    }

    // Method to insert new hike
    public void insertNewHike() {
        Hike newHike = new Hike();
        newHike.setName(inputList().get(0));
        newHike.setLocation(inputList().get(1));
        newHike.setDate(inputList().get(2));
        newHike.setParking(inputList().get(3));
        newHike.setLength(Integer.parseInt(inputList().get(4)));
        newHike.setLevel(inputList().get(5)) ;
        newHike.setDescription(inputList().get(6));
        HikerAppDatabase.getInstance(requireContext()).hikeDao().insertHike(newHike);
    }

    // Show alert dialog
    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
            return new DatePickerDialog(requireContext(), this, year, --month, day);
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


}