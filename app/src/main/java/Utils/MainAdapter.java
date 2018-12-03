package Utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.titanic.titaniccontacts.Contact;
import com.titanic.titaniccontacts.DetailsActivity;
import com.titanic.titaniccontacts.MainActivity;
import com.titanic.titaniccontacts.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class MainAdapter extends BaseAdapter {

    private static final String TAG = "MainAdapter";

    List<Contact> accounts;
    Context context;

    public MainAdapter(List<Contact> accounts, Context context) {
        this.accounts = accounts;
        this.context = context;
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount: th no is "+ accounts.size());
        return accounts.size();
    }

    @Override
    public Object getItem(int position) {
        return accounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Log.d(TAG, "getView: The adapter is working fine ");

        final int count = position;
        Contact contact = accounts.get(position);

        View view = View.inflate(context,R.layout.main_list_items,null);
        TextView textview_contact_Name= (TextView) view.findViewById(R.id._main_contact_name);
        TextView textview_contact_TelefonNr= (TextView) view.findViewById(R.id.main_contact_number);
//        final Button account_btn = view.findViewById(R.id.acc_btn);

        textview_contact_Name.setText(accounts.get(position).getName());
        textview_contact_TelefonNr.setText(accounts.get(position).getMobile());
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context,DetailsActivity.class);
//                intent.putExtra("mobileNumber",accounts.get(count).getMobile());
//                intent.putExtra("userName", accounts.get(count).getName());
//
//            }
//        });


        return view;
    }

    public void filter(List<Contact> searchList){

        accounts.addAll(searchList);
        notifyDataSetChanged();
    }
}
