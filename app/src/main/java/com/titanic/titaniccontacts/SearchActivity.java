package com.titanic.titaniccontacts;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.BaseAdapter;
import android.os.Bundle;
import android.widget.SearchView;


public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    static final String FLICKR_QUERY = "FLICKR_QUERY";
    private SearchView mSearchView;

    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: starts");
        getMenuInflater().inflate(R.menu.action_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.listview_search).getActionView();
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        mSearchView.setSearchableInfo(searchableInfo);
        Log.d(TAG, "onCreateOptionsMenu: " + getComponentName().toString());
        Log.d(TAG, "onCreateOptionsMenu: hint is " + mSearchView.getQueryHint());
        Log.d(TAG, "onCreateOptionsMenu: searchable info is " + searchableInfo.toString());

        mSearchView.setIconified(false);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: called");
//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                sharedPreferences.edit().putString(FLICKR_QUERY, query).apply();
                mSearchView.clearFocus();
                finish();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });



        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                finish();
                return false;
            }
        });

        Log.d(TAG, "onCreateOptionsMenu: returned " + true);
        return true;
    }
}