package com.example.recycleview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    long insertContact(ContactModel contactModel);

    @Update
    int updateContact(ContactModel contactModel);

    @Delete
    int deleteContact(ContactModel contactModel);

    @Query("select * from contact_table")
    LiveData<List<ContactModel>> getAllContact();

    @Query("select * from contact_table where id = :id")
    LiveData<ContactModel> getContactbyId(long id);
}
