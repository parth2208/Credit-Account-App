package com.titanic.titaniccontacts;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";

    public ImageView imageView;
    public TextView name,mobile_no,credit;
    public EditText increament, decrement;
    public Button history_btn, add_btn, del_btn;

    public static ContactDatabase mContactDatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        imageView = findViewById(R.id.fragment_display_picture);
        name = findViewById(R.id.fragment_Contact_name);
        mobile_no = findViewById(R.id.fragment_contact_number);
        credit = findViewById(R.id.user_credit_details);
        increament = findViewById(R.id.editText_addNumber);
        decrement = findViewById(R.id.editText_substractNumber);
        history_btn = findViewById(R.id.btn_history);
        add_btn = findViewById(R.id.btn_addNumber);
        del_btn = findViewById(R.id.btn_substractNumber);
        //Getting data form MainActivity
        final String contact_name = getIntent().getStringExtra("name");
        final String contact_number = getIntent().getStringExtra("mobile");
        //Passing data to the TextViews
        name.setText(contact_name);
        mobile_no.setText(contact_number);

        final CreditHistory history = new CreditHistory();
        history.setMobile(contact_number);


        mContactDatabase = Room.databaseBuilder(this,ContactDatabase.class,"dbContact").allowMainThreadQueries().build();

        int balance = mContactDatabase.contactDao().creditShow(contact_number);
        credit.setText(String.valueOf(balance));

        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this,HistoryActivity.class);
                intent.putExtra("history_mobile",contact_number);
                Log.d(TAG, "onClick: Passing contact no. is "+ contact_number);
                startActivity(intent);
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String add_num = increament.getText().toString();
                int add_credit = Integer.parseInt(add_num);

                String currentTime = DateFormat.getDateTimeInstance().format(new Date());
                //passing the data to the db table
                DetailsActivity.mContactDatabase.contactDao().addCredit(contact_number,add_credit);

                history.setAmount(add_credit);


                //To store the time of Room database updation in a separate table.
                DetailsActivity.mContactDatabase.contactDao().addTimestamp(contact_number,currentTime);

                history.setDateStamp(currentTime);
                DetailsActivity.mContactDatabase.creditDao().addHistory(history);
                Toast.makeText(DetailsActivity.this, "The credit amount added", Toast.LENGTH_SHORT).show();

                increament.setText("");

                int balance = mContactDatabase.contactDao().creditShow(contact_number);
                credit.setText(String.valueOf(balance));
            }
        });

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String del_num = decrement.getText().toString();
                int del_credit = Integer.parseInt(del_num);
                String currentTime = DateFormat.getDateTimeInstance().format(new Date());
                history.setAmount(del_credit);
                history.setDateStamp(currentTime);
                Log.d(TAG, "onClick: The time stamp is updated"+currentTime);
                //passing the data to the db table
                DetailsActivity.mContactDatabase.contactDao().delCredit(contact_number,del_credit);
                //To store the time of Room database updation in a separate table.

                DetailsActivity.mContactDatabase.contactDao().addTimestamp(contact_number,currentTime);
                Log.d(TAG, "onClick: Its also stored in the history table "+ history.getAmount());
                Log.d(TAG, "onClick: Its also stored in the history table "+ history.getDateStamp());
                Log.d(TAG, "onClick: Its also stored in the history table "+ history.getMobile());


                Toast.makeText(DetailsActivity.this, "The credit amount deleted", Toast.LENGTH_SHORT).show();

                DetailsActivity.mContactDatabase.creditDao().addHistory(history);
                decrement.setText("");

                int balance = mContactDatabase.contactDao().creditShow(contact_number);
                credit.setText(String.valueOf(balance));

            }
        });


    }

}
