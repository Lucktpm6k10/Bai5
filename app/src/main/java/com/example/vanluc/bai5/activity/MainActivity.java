package com.example.vanluc.bai5.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vanluc.bai5.R;
import com.example.vanluc.bai5.adapter.ContactAdapter;
import com.example.vanluc.bai5.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv_Contact;
    ArrayList<Contact> listContact;
    Button btn_Add,btn_Switch,btn_Close;
    int checkLayout = 1;
    Dialog digContactNew;
    ContactAdapter contactAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initSetRecyclerView();
        initListener();
    }

    private void initListener() {
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
        btn_Switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchRecylerview();
            }
        });
    }

    private void switchRecylerview() {
        if(checkLayout == 1) {

            GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
            rv_Contact.setLayoutManager(layoutManager);
            checkLayout =0 ;
        }else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,
                    false);
            rv_Contact.setLayoutManager(layoutManager);
            checkLayout = 1;
        }
       ;
    }

    private void addItem() {
        digContactNew = new Dialog(this);
        digContactNew.setContentView(R.layout.dialog_contact_new);
        digContactNew.show();
        Button btn_SaveNew = digContactNew.findViewById(R.id.btn_Save_New);
        Button btn_Close = digContactNew.findViewById(R.id.btn_Close);
        final EditText et_NumberNew = digContactNew.findViewById(R.id.et_NumberNew);
        final EditText et_NameNew = digContactNew.findViewById(R.id.et_NameNew);


        btn_SaveNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_NameNew.getText().toString();
                String number = et_NumberNew.getText().toString();
                if (name.isEmpty() == true) {
                    Toast.makeText(MainActivity.this, "Không được để trống name", Toast.LENGTH_SHORT).show();
                } else if (number.isEmpty() == true) {
                    Toast.makeText(MainActivity.this, "Không được để trống number", Toast.LENGTH_SHORT).show();
                } else {
                    addNewContact(name, number);
                    Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseNewDialog();
            }
        });

    }

    private void addNewContact(String name,String number) {
        /*if(name.isEmpty() == true || number.isEmpty() == true)
        {
            Toast.makeText(this, "Bạn phải điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }else {*/
            listContact.add(new Contact(name,number));
            contactAdapter.notifyDataSetChanged();
            digContactNew.dismiss();
       // }
    }

    private void CloseNewDialog() {
        digContactNew.dismiss();
    }

    private void initView()
    {
        rv_Contact = findViewById(R.id.rv_Contact);
        listContact = new ArrayList<>();
        btn_Add = findViewById(R.id.btn_Add);
        btn_Switch = findViewById(R.id.btn_Switch);
        contactAdapter = new ContactAdapter(listContact,getApplicationContext());
    }
    private void initAddDataTest()
    {
        listContact.add(new Contact("Văn Lực","01634349627"));
        listContact.add(new Contact("Quốc Nam","0160012300"));
        listContact.add(new Contact("Hoài Linh","01634883210"));
        listContact.add(new Contact("Minh Hiền","01634943527"));
        listContact.add(new Contact("Ngọc Bích","01634311111"));
    }
    private void initSetRecyclerView()
    {
        rv_Contact.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,
                                                                      false);
        rv_Contact.setLayoutManager(layoutManager);
        initAddDataTest();
        rv_Contact.setAdapter(contactAdapter);
    }

}
