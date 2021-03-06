package com.example.recycleview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recycleview.databinding.FragmentContactListBinding;

import java.util.List;


public class ContactListFragment extends Fragment implements ContactAdapter.editDeleteContact {
    private FragmentContactListBinding binding;
    private ContactAdapter adapter;
    private ContactViewModel contactViewModel;
    public ContactListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentContactListBinding.inflate(inflater);
     contactViewModel = new ViewModelProvider(getActivity()).get(ContactViewModel.class);
       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
contactViewModel.getContact().observe(getViewLifecycleOwner(), new Observer<List<ContactModel>>() {
    @Override
    public void onChanged(List<ContactModel> contactModels) {

        if (contactModels.size() == 0)
        {

            binding.emptyTextId.setVisibility(View.VISIBLE);
        }
        else {
            binding.emptyTextId.setVisibility(View.GONE);
        }
        adapter = new ContactAdapter(getActivity(), contactModels,ContactListFragment.this);
        LinearLayoutManager llm = new LinearLayoutManager(requireContext());
        binding.recycleViewId.setLayoutManager(llm);
        binding.recycleViewId.setAdapter(adapter);
    }
});


        binding.fabId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_contactListFragment_to_addContactList);
            }
        });
    }

    @Override
    public void edit(ContactModel contactModel) {

    }

    @Override
    public void delete(ContactModel contactModel) {
contactViewModel.delete(contactModel);
    }
}