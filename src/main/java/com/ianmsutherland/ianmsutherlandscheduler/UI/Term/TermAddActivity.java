package com.ianmsutherland.ianmsutherlandscheduler.UI.Term;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ianmsutherland.ianmsutherlandscheduler.Database.Repository;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Term;
import com.ianmsutherland.ianmsutherlandscheduler.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class TermAddActivity extends AppCompatActivity {

    final Calendar termStartCalendar = Calendar.getInstance();
    final Calendar termEndCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener termStartDateListener;
    DatePickerDialog.OnDateSetListener termEndDateListener;

    private EditText editTermName;
    private EditText editTermStartDate;
    private EditText editTermEndDate;

    private String mTermName;
    private String mTermStartDate;
    private String mTermEndDate;



    String TAG = "SchedulerDatabaseLog";
    Repository repository;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);

        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTermName = findViewById(R.id.editTermName);
        editTermStartDate = findViewById(R.id.editTermStartDate);
        editTermEndDate = findViewById(R.id.editTermEndDate);


        editTermStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TermAddActivity.this, termStartDateListener, termStartCalendar
                        .get(Calendar.YEAR), termStartCalendar.get(Calendar.MONTH),
                        termStartCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        termStartDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                termStartCalendar.set(Calendar.YEAR, year);
                termStartCalendar.set(Calendar.MONTH, monthOfYear);
                termStartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText(editTermStartDate, termStartCalendar);
            }
        };

        termEndDateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                termEndCalendar.set(Calendar.YEAR, year);
                termEndCalendar.set(Calendar.MONTH, monthOfYear);
                termEndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText(editTermEndDate, termEndCalendar);
            }
        };
        editTermEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(TermAddActivity.this, termEndDateListener, termEndCalendar
                        .get(Calendar.YEAR), termEndCalendar.get(Calendar.MONTH),
                        termEndCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    private void updateDateText(EditText v, Calendar calendar) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        v.setText(sdf.format(calendar.getTime()));
    }

    public void addTerm(View view) {
        int lastId = 0;

        repository = new Repository(getApplication());


        mTermName = editTermName.getText().toString();
        mTermStartDate = editTermStartDate.getText().toString();
        mTermEndDate = editTermEndDate.getText().toString();


        Term term = new Term(mTermName, mTermStartDate, mTermEndDate);

        repository.insert(term);

        this.finish();

    }

    public void cancelTerm(View view) {
        this.finish();
    }
}
