package com.titanic.titaniccontacts;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.icu.text.Replaceable;

import java.util.List;

@Dao
public interface CreditDao {

    @Insert
    long addHistory(CreditHistory history);

//    @Query("INSERT INTO credit_history (amount,datestamp) VALUES(number,time) WHERE mobile =:mobile_number")
//    int transactDetails(String mobile_number,int number,String time);

    @Query("UPDATE credit_history SET amount=:number, dateStamp=:timeStamp WHERE mobile =:mobile_number")
    int addCredit(String mobile_number,int number, long timeStamp);

    @Query("select * from credit_history where mobile=:phone_no")
    List<CreditHistory> getMobile(String phone_no);
}
