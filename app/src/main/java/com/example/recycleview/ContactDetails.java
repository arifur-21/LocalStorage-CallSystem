package com.example.recycleview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recycleview.databinding.FragmentContactDetailsBinding;


public class ContactDetails extends Fragment {
private FragmentContactDetailsBinding binding;
private long id = 0;
private ContactViewModel contactViewModel;

    public ContactDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactDetailsBinding.inflate(inflater);
        contactViewModel = new ViewModelProvider(requireActivity()).get(ContactViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Bundle bundle = getArguments();
        if (bundle != null)
        {
            id = bundle.getLong("id");



           contactViewModel.getContactByid(id).observe(getViewLifecycleOwner(), new Observer<ContactModel>() {
               @Override
               public void onChanged(ContactModel contactModel) {
                 binding.setContact(contactModel);

               }
           });

        }
    }
}