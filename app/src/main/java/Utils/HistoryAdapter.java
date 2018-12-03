package Utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.titanic.titaniccontacts.ContactDatabase;
import com.titanic.titaniccontacts.CreditHistory;
import com.titanic.titaniccontacts.R;

import java.util.List;

public class HistoryAdapter extends BaseAdapter {

    private static final String TAG = "HistoryAdapter";

    List<CreditHistory> transactions;
    Context context;
    public static ContactDatabase contactDatabase;

    public HistoryAdapter(List<CreditHistory> transactions, Context context) {
        Log.d(TAG, "HistoryAdapter: constructor is working fine");
        this.transactions = transactions;
        this.context = context;
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int position) {
        return transactions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(TAG, "getView: The adapter is working fine");

        final CreditHistory history = transactions.get(position);

        View view = View.inflate(context,R.layout.history_list_item,null);
        TextView transaction_amount = view.findViewById(R.id._history_transact);
        TextView transact_date = view.findViewById(R.id.history_date);

        transaction_amount.setText(String.valueOf(history.getAmount()));
        Log.d(TAG, "getView: History "+ history.getAmount());
        transact_date.setText(String.valueOf(history.getDateStamp()));
        Log.d(TAG, "getView: History "+ history.getDateStamp());
        return view;
    }
}
