package com.example.vanluc.bai5.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vanluc.bai5.R;
import com.example.vanluc.bai5.activity.MainActivity;
import com.example.vanluc.bai5.model.Contact;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> implements View.OnLongClickListener {
    ArrayList<Contact> dataContact;
    Context context;


    public ContactAdapter(ArrayList<Contact> dataContact, Context context) {
        this.dataContact = dataContact;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_Name.setText(dataContact.get(position).getName());
        holder.tv_Number.setText(dataContact.get(position).getNumber());

    }

    @Override
    public int getItemCount() {
        if (dataContact.size() == 0) {
            return 0;
        }
        return dataContact.size();
    }

    @Override
    public boolean onLongClick(View v) {

        return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Name, tv_Number;

        public ViewHolder(final View itemView) {
            super(itemView);
            tv_Name = itemView.findViewById(R.id.tv_Name);
            tv_Number = itemView.findViewById(R.id.tv_Number);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dig_ContactRepair = new Dialog(itemView.getContext());
                    dig_ContactRepair.setContentView(R.layout.dialog_contact_repair);
                    dig_ContactRepair.show();
                    Button btn_Delete = dig_ContactRepair.findViewById(R.id.btn_Delete);
                    Button btn_Save = dig_ContactRepair.findViewById(R.id.btn_Save);
                    final EditText et_Name = dig_ContactRepair.findViewById(R.id.et_Name);
                    final EditText et_Number = dig_ContactRepair.findViewById(R.id.et_Number);
                    et_Name.setText(dataContact.get(getAdapterPosition()).getName());
                    et_Number.setText(dataContact.get(getAdapterPosition()).getNumber());
                    btn_Save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (et_Name.getText().toString().isEmpty() == true) {
                                Toast.makeText(context, itemView.getResources().getString(R.string.toastName), Toast.LENGTH_SHORT).show();
                            } else if (et_Number.getText().toString().isEmpty() == true) {
                                Toast.makeText(context, itemView.getResources().getString(R.string.toastNumber), Toast.LENGTH_SHORT).show();
                            } else {
                                int id = MainActivity.listContact.get(getAdapterPosition()).getID();
                                updateItem(id, et_Name.getText().toString(), et_Number.getText().toString());
                                Toast.makeText(context, itemView.getResources().getString(R.string.toastSave), Toast.LENGTH_SHORT).show();
                                dig_ContactRepair.dismiss();
                            }
                        }
                    });
                    btn_Delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removeItem(MainActivity.listContact.get(getAdapterPosition()).getID());
                            dig_ContactRepair.dismiss();
                            Toast.makeText(context, itemView.getResources().getString(R.string.toastDelete), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        }
    }

    private void removeItem(int id) {
        MainActivity.databaseContact.queryData("DELETE FROM CONTACTS WHERE ID = " + id);
        MainActivity.getDataFromSQLite();

    }

    private void updateItem(int id, String name, String number) {
        MainActivity.databaseContact.queryData("UPDATE CONTACTS SET NAME = '" + name + "' ," +
                " NUMBER = '" + number + "'" +
                "WHERE ID =" + id);
        MainActivity.getDataFromSQLite();
    }

}
