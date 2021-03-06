package com.example.recycleview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
private Context context;
private List<ContactModel> contactModelList;
private editDeleteContact listener;


    public ContactAdapter(Context context, List<ContactModel> contactModelList, Fragment fragment) {
        this.context = context;
        this.contactModelList = contactModelList;
        listener = (editDeleteContact) fragment;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.contact_row,parent,false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        holder.nameText.setText(contactModelList.get(position).getName());

        holder.callView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Uri phoneUri = Uri.parse("tel:"+contactModelList.get(position).getPhone());
                final Intent callIntent = new Intent(Intent.ACTION_DIAL,phoneUri);
                if (callIntent.resolveActivity(context.getPackageManager()) != null)
                {
                    context.startActivity(callIntent);
                }
            }
        });
        holder.smsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] message = {};
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("smsto:"));  // This ensures only SMS apps respond
                intent.putExtra("sms_body", message);
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            }

        });

        holder.emailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] addresses = {};
                String[] subject = {};
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bundle bundle = new Bundle();
                bundle.putLong("id", contactModelList.get(position).getId());

                Navigation.findNavController(view).navigate(R.id.action_contactListFragment_to_contactDetails,bundle);
            }
        });



        holder.menuText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(context,view);
                popupMenu.getMenuInflater().inflate(R.menu.menu,popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId())
                        {
                            case R.id.editId:

                                final Bundle bundle = new Bundle();
                                bundle.putLong("id",contactModelList.get(position).getId());
                                Navigation.findNavController(view).navigate(R.id.action_contactListFragment_to_addContactList,bundle);
                                break;

                            case R.id.deleteId:

                                deleteContact(position);
                                break;
                        }
                        return false;
                    }
                });
            }
        });

    }

    private void deleteContact(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete");
       builder.setIcon(R.drawable.question);
       builder.setMessage("Are you sure delete this contact");
       builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {

               listener.delete(contactModelList.get(position));

           }
       });

       builder.setNegativeButton("Cancel", null);
       AlertDialog dialog = builder.create();
       dialog.show();
    }

    @Override
    public int getItemCount() {
        return contactModelList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView nameText,menuText;
        ImageView callView,smsView,emailView;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.nameTextId);
            menuText = itemView.findViewById(R.id.menuId);
            callView = itemView.findViewById(R.id.callId);
            smsView = itemView.findViewById(R.id.smsId);
            emailView = itemView.findViewById(R.id.emailId);
        }
    }
    public interface editDeleteContact{
        void edit(ContactModel contactModel);
        void delete(ContactModel contactModel);
    }
}
