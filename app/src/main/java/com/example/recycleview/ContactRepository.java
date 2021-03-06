package com.example.recycleview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class ContactRepository {

    LiveData<List<ContactModel>> contactModelList;
    private ContactDao contactDao;

   public ContactRepository(ContactDao contactDao)
    {
        contactModelList = new MutableLiveData<>();
        this.contactDao = contactDao;
        contactModelList = contactDao.getAllContact();
    }

    public void addNewContact(ContactModel contactModel)
    {
        ContactDatabase.dbExecutor.execute(() -> contactDao.insertContact(contactModel));
    }

    public LiveData<List<ContactModel>> getAllContact()
    {

        return contactModelList;
    }

    public void updateCotact(ContactModel contactModel)
    {
        ContactDatabase.dbExecutor.execute(()->
                contactDao.updateContact(contactModel));
    }

    public LiveData<ContactModel>getContactById(long id)
    {
        return contactDao.getContactbyId(id);
    }

    public void deleteContact(ContactModel contactModel)

    {
                ContactDatabase.dbExecutor.execute(() -> {
                    contactDao.deleteContact(contactModel);
                });
    }
}
