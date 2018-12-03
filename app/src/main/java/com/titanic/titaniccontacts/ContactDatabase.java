package com.titanic.titaniccontacts;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {Contact.class,CreditHistory.class},version = 1,exportSchema = false)
public abstract class ContactDatabase extends RoomDatabase {


    public abstract ContactDao contactDao();
    public abstract CreditDao creditDao();

}
