package Utils;

public class UserDetails {

    public String user_name ="";
    public String mobile_no="";
    public boolean account = false;

    public UserDetails(String user_name, String mobile_no, boolean account) {
        this.user_name = user_name;
        this.mobile_no = mobile_no;
        this.account = account;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public boolean isAccount() {
        return account;
    }

    public void setAccount(boolean account) {
        this.account = account;
    }
}

