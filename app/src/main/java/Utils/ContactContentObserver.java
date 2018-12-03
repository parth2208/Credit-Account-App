package Utils;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;

public class ContactContentObserver extends ContentObserver {
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */

    private Context context;
    public ContactContentObserver(Handler handler) {
        super(handler);
    }

    public ContactContentObserver(Handler handler, Context context) {
        super(handler);
        this.context = context;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);

        if (!selfChange){

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_DENIED){
                ContentResolver contentResolver = context.getContentResolver();
                Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
                if (cursor != null && cursor.getCount() > 0) {
                    //moving cursor to last position
                    //to get last element added
                    cursor.moveToLast();
                    String contactName, contactNumber = null;
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                    if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                        if (phoneCursor != null) {
                            while (phoneCursor.moveToNext()) {
                                contactNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                if (contactNumber != null && contactNumber.length() > 0) {
                                    contactNumber = contactNumber.replace(" ", "");
                                }
                                contactName = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                //String msg = "Name : " + contactName + " Contact No. : " + contactNumber;
                            }
                            phoneCursor.close();
                        }
                    }
                    cursor.close();
                }
            }
        }
    }
}
