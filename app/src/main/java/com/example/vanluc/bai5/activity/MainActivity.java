package com.example.vanluc.bai5.activity;

import android.app.Dialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vanluc.bai5.R;
import com.example.vanluc.bai5.adapter.ContactAdapter;
import com.example.vanluc.bai5.database.DatabaseManager;
import com.example.vanluc.bai5.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv_Contact;
    public static ArrayList<Contact> listContact;
    Button btn_Add, btn_Switch;
    int checkLayout = 1;
    Dialog digContactNew;
    public static ContactAdapter contactAdapter;
    public static DatabaseManager databaseContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initSetRecyclerView();

        initListener();

        getDataFromSQLite();
    }

    //Bắt sự kiện
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

    //Đổi định dạng
    private void switchRecylerview() {
        if (checkLayout == 1) {

            GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
            rv_Contact.setLayoutManager(layoutManager);
            checkLayout = 0;
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                    false);
            rv_Contact.setLayoutManager(layoutManager);
            checkLayout = 1;
        }
        ;
    }

    //Nút add
    private void addItem() {
        showDialog();
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
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.toastName), Toast.LENGTH_SHORT).show();
                } else if (number.isEmpty() == true) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.toastNumber), Toast.LENGTH_SHORT).show();
                } else {
                    addNewContact(name, number);
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.toastDone), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeNewDialog();
            }
        });

    }

    //Show diaglog
    private void showDialog() {
        digContactNew = new Dialog(this);
        digContactNew.setContentView(R.layout.dialog_contact_new);
        digContactNew.show();
    }

    //Thêm mới
    private void addNewContact(String name, String number) {
        databaseContact.queryData("INSERT INTO CONTACTS VALUES ( null, '" + name + "' ,'" + number + "')");
        getDataFromSQLite();
        contactAdapter.notifyDataSetChanged();
        digContactNew.dismiss();
    }

    //Đóng dialog
    private void closeNewDialog() {
        digContactNew.dismiss();
    }

    //Khai báo view
    private void initView() {
        createDatabase();
        createTableOnDatabase();
        rv_Contact = findViewById(R.id.rv_Contact);
        listContact = new ArrayList<>();
        btn_Add = findViewById(R.id.btn_Add);
        btn_Switch = findViewById(R.id.btn_Switch);
        contactAdapter = new ContactAdapter(listContact, getApplicationContext());
    }

    //Khởi tạo recylerview
    private void initSetRecyclerView() {
        rv_Contact.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        rv_Contact.setLayoutManager(layoutManager);
        rv_Contact.setAdapter(contactAdapter);
    }

    //Tạo database
    private void createDatabase() {
        databaseContact = new DatabaseManager(this, "Contact.sqlite", null, 1);

    }

    //Tạo bảng
    private void createTableOnDatabase() {
        databaseContact.queryData("CREATE TABLE IF NOT EXISTS CONTACTS ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME NVARCHAR(100) NOT NULL," +
                "NUMBER NVARCHAR(100) NOT NULL)");
    }

    //Lấy dữ liệu từ SQLITE
    public static void getDataFromSQLite() {
        Cursor cursor = databaseContact.getData("SELECT * FROM CONTACTS");
        listContact.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String number = cursor.getString(2);
            listContact.add(new Contact(id, name, number));
        }
        contactAdapter.notifyDataSetChanged();
    }

}
