package com.example.recycleview;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ContactModel.class},version = 2)
public abstract class ContactDatabase extends RoomDatabase {
    private static ContactDatabase db;
    public abstract ContactDao getContactDao();
    public static ExecutorService  dbExecutor = Executors.newFixedThreadPool(4);

   private static Migration MIGRATION_1_2 = new Migration(1,2) {
       @Override
       public void migrate(@NonNull SupportSQLiteDatabase database) {

           database.execSQL("alter table 'contact_table' add column 'bloodgroup' text");
       }
   };
    public static ContactDatabase getInstance(Context context)
    {
        if (db != null)
        {
            return db;
        }

        synchronized (ContactDatabase.class) {
            db = Room.databaseBuilder(context, ContactDatabase.class, "contact-db").addMigrations(MIGRATION_1_2).build();
            return db;
        }
    }
}
