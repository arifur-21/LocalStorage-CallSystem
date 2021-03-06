package com.example.recycleview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.recycleview.databinding.FragmentAddContactListBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class addContactList extends Fragment {
private FragmentAddContactListBinding binding;
private String[] cities = {"Feni","Dahaka","Cumilla"};
public static List<String> cities2 = Arrays.asList("Feni","Dahaka","Cumilla");
public static List<String> bloodGroups = Arrays.asList("A+","A-","B+", "B-","AB+","AB-","O+","O-");
private ContactViewModel contactViewModel;
private ContactModel contactModel;
private String gender= "Male";
private long id = 0;
private String bloogGroup;
private String city ;


    public addContactList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentAddContactListBinding.inflate(inflater);
       contactViewModel = new ViewModelProvider(getActivity()).get(ContactViewModel.class);
       contactModel = new ContactModel();
       binding.setContact(contactModel);
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
                public void onChanged(ContactModel contact) {
                    contactModel = contact;
                    setInfoIntoViews();

                }
            });
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,cities);
        final ArrayAdapter<String> bloodGroupAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, bloodGroups);

        binding.spinnerId.setAdapter(arrayAdapter);
        binding.bloodGroupId.setAdapter(bloodGroupAdapter);

        binding.saveId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactModel.setGender(gender);
                contactModel.setCity(city);
                contactModel.setBloodGroup(bloogGroup);
                contactViewModel.setContact(contactModel);
                Navigation.findNavController(view).navigate(R.id.action_addContactList_to_contactListFragment);
            }
        });
        binding.updateId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactModel.setGender(gender);
                contactModel.setCity(city);
                contactModel.setBloodGroup(bloogGroup);
               // contactViewModel.setContact(contactModel);
                contactModel.setId(id);
                contactViewModel.update(contactModel);
                Navigation.findNavController(view).navigate(R.id.action_addContactList_to_contactListFragment);
            }
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = new RadioButton(getContext());
                gender = radioButton.getText().toString();

            }
        });

        binding.bloodGroupId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bloogGroup = bloodGroups.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city = cities[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    private void setInfoIntoViews() {
       binding.setContact(contactModel);

        binding.saveId.setVisibility(View.GONE);
        binding.updateId.setVisibility(View.VISIBLE);

        final String gender = contactModel.getGender();
        if (gender.equals("Female")){
            binding.femailId.setChecked(true);
            city = contactModel.getCity();
            final int position = cities2.indexOf(city);
            binding.spinnerId.setSelection(position);

        }
        binding.bloodGroupId.setSelection(bloodGroups.indexOf(contactModel.getBloodGroup()));
    }


}