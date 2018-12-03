package com.titanic.titaniccontacts;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface  ContactDao {

    @Insert
     void addUser(Contact user);

    @Update
    void updateUser(Contact user);

    @Query("UPDATE contact_details SET balance= balance +:number WHERE mobile =:mobile_number")
    int addCredit(String mobile_number,int number);

    @Query("UPDATE contact_details SET balance= balance -:number WHERE mobile =:mobile_number")
    int delCredit(String mobile_number, int number);

    @Query("select * from contact_details")
     List<Contact> getUsers();

    @Query("select balance from contact_details where mobile = :phone")
    int creditShow(String phone);

    @Query("select * from contact_details where account = 1")
    List<Contact> getAccounts();

    @Query("UPDATE contact_details SET timestamp= :dateStamp WHERE mobile = :mobile_number")
    void addTimestamp(String mobile_number, String dateStamp);

}
