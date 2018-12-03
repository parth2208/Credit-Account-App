package Utils;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class LvViewHolder extends RecyclerView.ViewHolder {

    public AppCompatCheckBox compatCheckBox;
    public TextView contact_name, mobile_no;


    public LvViewHolder(View itemView) {
        super(itemView);
    }

    public AppCompatCheckBox getCompatCheckBox() {
        return compatCheckBox;
    }

    public void setCompatCheckBox(AppCompatCheckBox compatCheckBox) {
        this.compatCheckBox = compatCheckBox;
    }

    public TextView getContact_name() {
        return contact_name;
    }

    public void setContact_name(TextView contact_name) {
        this.contact_name = contact_name;
    }

    public TextView getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(TextView mobile_no) {
        this.mobile_no = mobile_no;
    }
}
