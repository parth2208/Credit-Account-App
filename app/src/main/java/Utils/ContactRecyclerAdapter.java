package Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ContactRecyclerAdapter extends RecyclerView.Adapter<itemHolder> {


    public ArrayList<UserDetails> name;
    public ArrayList<user_number> contact;
    Context context;

    public ContactRecyclerAdapter(ArrayList<UserDetails> name, ArrayList<user_number> contact, Context context) {
        this.name = name;
        this.contact = contact;
        this.context = context;
    }

    @NonNull
    @Override
    public itemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull itemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
