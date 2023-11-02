package com.example.hikermanagementapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.hikermanagementapp.Database.HikerAppDatabase;
import com.example.hikermanagementapp.Fragments.AddFragment;
import com.example.hikermanagementapp.Models.Hike;
import com.example.hikermanagementapp.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class EditActivity extends AppCompatActivity {
    private EditText inputName, inputLocation, inputDate, inputDescription, inputLength;
    private Spinner spinnerLevel;
    private RadioGroup parkingRadioGroup;
    private String newParkingValue;
    private Hike hike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        inputName = findViewById(R.id.field_name);
        inputLocation = findViewById(R.id.field_location);
        inputDate = findViewById(R.id.field_date);
        parkingRadioGroup = findViewById(R.id.parking_radio_group);
        inputLength = findViewById(R.id.field_length);
        spinnerLevel = findViewById(R.id.spinner_level);
        inputDescription = findViewById(R.id.field_description);

        hike = (Hike) Objects.requireNonNull(getIntent().getExtras()).get("object_hike");
        if (hike != null) {
            setDataToAllField(hike);
        }

        // Initialize radio group for parking option
        parkingRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = findViewById(checkedId);
            if (selectedRadioButton != null) {
                newParkingValue = selectedRadioButton.getText().toString();
            }
        });

        // Initialize spinner for level of difficulty
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.difficulty_levels, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLevel.setAdapter(spinnerAdapter);

        // Event for date picker
        inputDate.setOnClickListener(v -> {
            DialogFragment newFragment = new AddFragment.DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), "Date Picker");
        });

        // Event for button update
        Button saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(v -> updateHike());
    }


    // Method to update hike
    private void updateHike() {
        hike.setName(inputList().get(0));
        hike.setLocation(inputList().get(1));
        hike.setDate(inputList().get(2));
        hike.setParking(inputList().get(3));
        hike.setLength(Integer.parseInt(inputList().get(4)));
        hike.setLevel(inputList().get(5)) ;
        hike.setDescription(inputList().get(6));
        HikerAppDatabase.getInstance(this).hikeDao().updateHike(hike);
        Intent intentResult = new Intent();
        setResult(Activity.RESULT_OK,intentResult);
        finish();
    }

    public ArrayList<String> inputList() {
        ArrayList<String> inputList = new ArrayList<>();

        String newName = inputName.getText().toString();
        String newLocation = inputLocation.getText().toString();
        String newDate = inputDate.getText().toString();
        String newParking = getParkingOptionValues();
        String newLength = inputLength.getText().toString();
        String newLevel = getLevelValues();
        String newDescription = inputDescription.getText().toString();

        inputList.add(0, newName);
        inputList.add(1, newLocation);
        inputList.add(2, newDate);
        inputList.add(3, newParking);
        inputList.add(4, newLength);
        inputList.add(5, newLevel);
        inputList.add(6, newDescription);

        return inputList;
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


    // Method to set data from details view to edit view
    @SuppressLint("SetTextI18n")
    private void setDataToAllField(Hike hike) {
        Resources res = getResources();
        String[] difficultyLevels = res.getStringArray(R.array.difficulty_levels);


        inputName.setText(hike.getName());
        inputLocation.setText(hike.getLocation());
        inputDate.setText(hike.getDate());
        if (hike.getParking().equals(getResources().getString(R.string.parking_yes))) {
            parkingRadioGroup.check(R.id.radio_button_yes);
        } else {
            parkingRadioGroup.check(R.id.radio_button_no);
        }
        inputLength.setText(Integer.toString(hike.getLength()));
        if(hike.getLevel().equals(difficultyLevels[0])){
            spinnerLevel.setSelection(0);
        } else if (hike.getLevel().equals(difficultyLevels[1])) {
            spinnerLevel.setSelection(1);
        }else {
            spinnerLevel.setSelection(2);
        }
        inputDescription.setText(hike.getDescription());

    }

    // Method to get value of parking radio button
    public String getParkingOptionValues() {
        return newParkingValue;
    }

    // Method to get value of level spinner
    public String getLevelValues() {
        if (spinnerLevel != null && spinnerLevel.getSelectedItem() != null) {
            return spinnerLevel.getSelectedItem().toString();
        }
        return null;
    }

    // Show alert dialog
    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
        {
            LocalDate d = LocalDate.now();
            int year = d.getYear();
            int month = d.getMonthValue();
            int day = d.getDayOfMonth();
            return new DatePickerDialog(requireActivity(), this, year, --month, day);}
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day){
            LocalDate dob = LocalDate.of(year, ++month, day);
            ((EditActivity) requireActivity()).sendToDOB(dob);
        }
    }

    // Format date in edit text
    private String formatDate(LocalDate dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dob.format(formatter);
    }

    // Update edit text after user chose a date
    public void sendToDOB(LocalDate dob) {
        EditText dateOfHike = findViewById(R.id.field_date);
        dateOfHike.setText(formatDate(dob));
    }

}