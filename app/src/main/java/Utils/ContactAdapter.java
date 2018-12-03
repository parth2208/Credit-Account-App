package Utils;


import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.titanic.titaniccontacts.Contact;
import com.titanic.titaniccontacts.ContactDatabase;
import com.titanic.titaniccontacts.R;

import java.util.List;

public class ContactAdapter extends BaseAdapter {

    private static final String TAG = "ContactAdapter";

    List<Contact> contacts_details;
    Context context;
    ContactDatabase mContactDatabase;

    public ContactAdapter(List<Contact> contacts_details, Context context, ContactDatabase mContactDatabase) {
        this.contacts_details = contacts_details;
        this.context = context;
        this.mContactDatabase = mContactDatabase;
    }

    @Override
    public int getCount() {
        return contacts_details.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts_details.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {

        Log.d(TAG, "getView: getting the res ids");

        LvViewHolder lvViewHolder = null;

        final Contact contact = contacts_details.get(position);

        View view=View.inflate(context, R.layout.list_items,null);
        TextView textview_contact_Name= (TextView) view.findViewById(R.id.Contact_name);
        TextView textview_contact_TelefonNr= (TextView) view.findViewById(R.id.contact_number);
        final AppCompatCheckBox compatCheckBox = view.findViewById(R.id.checkbox);

        lvViewHolder = new LvViewHolder(view);

        textview_contact_Name.setText(contact.getName());
        textview_contact_TelefonNr.setText(contact.getMobile());
        compatCheckBox.setChecked(contact.isAccount());

        lvViewHolder.setCompatCheckBox(compatCheckBox);

        compatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            contact.setAccount(compatCheckBox.isChecked());
            mContactDatabase.contactDao().updateUser(contact);
            }
        });


        view.setTag(contact.getMobile());


//        compatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if ()
//            }
//        });
        lvViewHolder.getCompatCheckBox().setChecked(compatCheckBox.isChecked());

        return view;

    }

//    public void passDataActivity(String name,String number){
//        Intent intent = new Intent(context,MainActivity.class);
//
//        intent.putExtra("contact_name",name);
//        intent.putExtra("contact_number",number);
//
//    }
}