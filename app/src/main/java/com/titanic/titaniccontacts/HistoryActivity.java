package com.titanic.titaniccontacts;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import Utils.HistoryAdapter;

public class  HistoryActivity extends AppCompatActivity {

    private static final String TAG = "HistoryActivity";

    List<CreditHistory> historyList;
    HistoryAdapter historyAdapter;
    ListView listViewHistory;
    public static ContactDatabase mContactDatabase;
    CreditDao creditDao;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: History Activity ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listViewHistory = findViewById(R.id.history_listView);

        String contact_mobile = getIntent().getStringExtra("history_mobile");
        Log.d(TAG, "onCreate: getting the unique mobile no."+ contact_mobile);

        mContactDatabase = Room.databaseBuilder(this,ContactDatabase.class,"dbContact").allowMainThreadQueries().build();
        Log.d(TAG, "onCreate: database initialized ");
        historyList = mContactDatabase.creditDao().getMobile(contact_mobile);
        historyAdapter = new HistoryAdapter(historyList,this);
        listViewHistory.setAdapter(historyAdapter);

    }
}
