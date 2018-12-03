package com.titanic.titaniccontacts;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Utils.ContactAdapter;
import Utils.UserDetails;

public class ContactAddActivity extends AppCompatActivity{
    private static final String TAG = "ContactAddActivity";

    ListView contact_listview;
    AppCompatCheckBox compatCheckBox;
    ContactAdapter contactAdapter;
    ArrayList<UserDetails> arrayList = new ArrayList<>();
    int count;

    public static ContactDatabase mContactDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_add);

        contact_listview = findViewById(R.id.contact_listView);
        compatCheckBox = findViewById(R.id.checkbox);

        mContactDatabase = Room.databaseBuilder(this,ContactDatabase.class,"dbContact").allowMainThreadQueries().build();

        List<Contact> contacts = ContactAddActivity.mContactDatabase.contactDao().getUsers();


        contactAdapter = new ContactAdapter(contacts,this,  mContactDatabase);
        contact_listview.setAdapter(contactAdapter);

        //////////////
        /*
        SharedPreferences preferences = getSharedPreferences("saving",Context.MODE_PRIVATE);
        boolean isChecked = preferences.getBoolean("checkbox",false);
        if (isChecked){
            compatCheckBox.setChecked(true);
        }*/


    }


    @Override
    protected void onDestroy() {

        /*if (!compatCheckBox.isChecked()){
        }else {
            SharedPreferences preferences = getSharedPreferences("saving",Context.MODE_PRIVATE);
            preferences.edit().putBoolean("checkbox",true).commit();
        }*/

        super.onDestroy();

    }

//    @Override
//    protected void onPostResume() {
//
//        super.onPostResume();
//    }



//    public void onCheckboxClicked(View view) {
//        // Is the view now checked?
//        boolean checked = ((CheckBox) view).isChecked();
//
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.checkbox:
//                if (checked){
//
//                    Toast.makeText(ContactAddActivity.this, "The item is checked " + view.getId(), Toast.LENGTH_SHORT).show();
//
////                    Intent intent = new Intent(ContactAddActivity.this,MainActivity.class);
////                    intent.putStringArrayListExtra()
//
//                }
//                // Put some meat on the sandwich
//            else{
//                    Toast.makeText(this, "Item deselected", Toast.LENGTH_SHORT).show();
//
//                }
//                // Remove the meat
//        }
//    }



}
