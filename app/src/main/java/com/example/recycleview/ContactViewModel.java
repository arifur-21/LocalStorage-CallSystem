package com.example.recycleview;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private ContactRepository repository;
    private LiveData<List<ContactModel>> mutableLiveData;
    private ContactDatabase db;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        db = ContactDatabase.getInstance(application);
        repository = new ContactRepository(db.getContactDao());
        mutableLiveData = new MutableLiveData<>();
        mutableLiveData = repository.getAllContact();


    }

    public void setContact(ContactModel contact)
    {
         repository.addNewContact(contact);
    }
    public void update(ContactModel contactModel)
    {
        repository.updateCotact(contactModel);
    }
    public LiveData<ContactModel>getContactByid(long id)
    {
        return repository.getContactById(id);
    }

    public LiveData<List<ContactModel>>getContact()
    {
        return mutableLiveData;
    }

    public void delete(ContactModel contactModel)
    {

        repository.deleteContact(contactModel);
    }
}
