package Utils;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.titanic.titaniccontacts.R;

public class itemHolder extends RecyclerView.ViewHolder {

    public TextView user_name;
    public TextView contact;
    public AppCompatImageButton addbtn;


    public itemHolder(View itemView) {
        super(itemView);

        this.user_name = itemView.findViewById(R.id.user_name);
        this.contact = itemView.findViewById(R.id.contact);
        this.addbtn = itemView.findViewById(R.id.add_imgButton);
    }
}
