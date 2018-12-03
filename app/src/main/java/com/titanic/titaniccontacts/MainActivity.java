package com.titanic.titaniccontacts;



import android.app.SearchManager;
import android.app.SearchableInfo;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.Toast;


import java.util.List;

import Utils.ContactContentObserver;
import Utils.MainAdapter;

import static android.Manifest.permission.READ_CONTACTS;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "MainActivity";
    private static boolean READ_CONTACT_GRANTED =false;

    ListView main_listview;
    MainAdapter mainAdapter;
    ContactContentObserver contentObserver = new ContactContentObserver(new Handler());
    List<Contact> contacts;
    public SearchView searchView;
    public static ContactDatabase mContactDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Checking Read Contact Permission
        int hasReadContactPermission = ContextCompat.checkSelfPermission(this, READ_CONTACTS);

        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {

            READ_CONTACT_GRANTED = true;
            // Permission is granted
        }else {
            ActivityCompat.requestPermissions(this,new String[]{READ_CONTACTS},1);
        }

        /////////////////////////////
        Log.d(TAG, "onCreate: Database is initialized");
        mContactDatabase = Room.databaseBuilder(this,ContactDatabase.class,"dbContact").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        main_listview = findViewById(R.id.main_listView);


        /////////////Reading phone contact for and storing in the rOOm//////////////////
        Contact user = new Contact();
        CreditHistory history = new CreditHistory();
        Cursor cursor_Contacts = null;
        ContentResolver contentResolver = getContentResolver();
        try {
            cursor_Contacts = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        } catch (Exception ex) {
            Log.e("contact", ex.getMessage());
        }
        Log.d(TAG, "retrieveContactList: ");

        if (cursor_Contacts!= null) {
            if (cursor_Contacts.getCount() > 0) {

                while (cursor_Contacts.moveToNext()) {

                    //final user_contact contact = new user_contact();

                    String contact_id = cursor_Contacts.getString(cursor_Contacts.getColumnIndex(ContactsContract.Contacts._ID));
                    final String contact_display_name = cursor_Contacts.getString(cursor_Contacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    //Assigning the contacts
                    // contact.user_name = contact_display_name;

                    // Storing into the database ===== Conatact Name

                    user.setName(contact_display_name);


                    Log.d(TAG, "namelist: The stored name" + user.getName());


                    Log.d(TAG, "retrieveContactList: names");


                    int hasPhoneNumber = Integer.parseInt(cursor_Contacts.getString(cursor_Contacts.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                    if (hasPhoneNumber > 0) {

                        Cursor phoneCursor = contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                                , null
                                , ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?"
                                , new String[]{contact_id}
                                , null);

                        while (phoneCursor.moveToNext()) {
                            final String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                            //contact.mobile_no = phoneNumber;
                            Log.d(TAG, "retrieveContactList: mobile no.");

                            ///Storing into the database==== Mobile No.
                            if (phoneNumber != null) {
                                user.setMobile(phoneNumber);
//                                history.setMobile(phoneNumber);

                            }
                            Log.d(TAG, "retrieveContactList: The stored contact is " + user.getMobile());
//                            Log.d(TAG, "retrieveMobileList: The stored mobile in history table is" +history.getMobile());
                            user.setAccount(false);
                            try {
                                mContactDatabase.contactDao().addUser(user);
                            } catch (Exception e) {
                                // e.printStackTrace();
                            }

                        }
                        phoneCursor.close();
                    }
                }
            }
        }

        final List<Contact> contacts = mContactDatabase.contactDao().getAccounts();
        mainAdapter = new MainAdapter(contacts, this);
        main_listview.setAdapter(mainAdapter);

        Log.d(TAG, "onCreateMainActivity: THe stored data in the table is " + contacts);

        main_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        for (int i = 0; i <= position; i++) {

                            switch (i){
                                case 0:
                                    //Toast.makeText(MainActivity.this, "The clicked position is"+ position, Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                                    intent.putExtra("mobile",contacts.get(position).getMobile());
                                    intent.putExtra("name",contacts.get(position).getName());

//                                    Intent intent1 = new Intent(MainActivity.this,HistoryActivity.class);
//                                    intent1.putExtra("history_mobile",contacts.get(position).getMobile());
//                                    Log.d(TAG, "onItemClick: The transfer data"+ intent1);
                                    startActivity(intent);
                        }
                }
            }
        });
    }


//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
//                    + "`name` TEXT, PRIMARY KEY(`id`))");
//        }
//    };



    @Override
    protected void onResume() {
        super.onResume();
        //Registering the content observer.
        Log.d(TAG, "onResume: Registering the Content Observer");
        getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI,true,contentObserver);
        Log.d(TAG, "onResume: This will retreive the data while resuming the MainActivity");
        main_listview.setAdapter(mainAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(contentObserver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    READ_CONTACT_GRANTED = true;
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }



    //--------------------------SearchView Implementation-------------------------------------------


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.listview_search);
        Log.d(TAG, "onCreateOptionsMenu() returned: " + menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if(id == R.id.listview_search) {
////            Intent intent = new Intent(this, SearchActivity.class);
////            startActivity(intent);
//            return true;
//        }

        if(id == R.id.add_contact) {
            Intent intent = new Intent(this, ContactAddActivity.class);
            startActivity(intent);
            return true;
        }

        Log.d(TAG, "onOptionsItemSelected() returned: returned");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        for (Contact contact_details :
                contacts) {
            String name = contact_details.getName().toLowerCase();
            if (name.contains(newText)){
                contacts.add(contact_details);
            }
        }

        mainAdapter.filter(contacts);

        return true;
    }


}
