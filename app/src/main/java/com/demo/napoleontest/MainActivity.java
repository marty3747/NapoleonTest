package com.demo.napoleontest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DataBaseHelper myDb;
    EditText editName, editSurname, editMarks, editTextId;
    Button btnAddData;
    Button btnViewAll;
    Button btnUpdate;
    Button btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DataBaseHelper(this);
        editName = (EditText)findViewById(R.id.editTextName);
        editSurname = (EditText)findViewById(R.id.editTextSurname);
        editMarks = (EditText)findViewById(R.id.editTextMarks);
        editTextId = (EditText)findViewById(R.id.editTextId);
        btnAddData = (Button)findViewById(R.id.btnAddData);
        btnViewAll = (Button)findViewById(R.id.btnViewData);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        addData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    public void DeleteData() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = myDb.deleteData(editTextId.getText().toString());
                if(deleteRows > 0) {
                    Toast.makeText(MainActivity.this, "Информация удалена", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Информация не удалена", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void UpdateData() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateData(editTextId.getText().toString(),
                                        editName.getText().toString(), editSurname.getText().toString(),
                                        editMarks.getText().toString());
                if(isUpdate == true) {
                    Toast.makeText(MainActivity.this, "Информация вставлена успешно", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Информация не вставлена", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void addData() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString());
                if(isInserted == true)
                    Toast.makeText(MainActivity.this, "Информация добавлена", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Информация не добавлена", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void viewAll() {
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0) {
                    //показать сообщение
                    showMessage("Error", "Ничего не найдено");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id: "+ res.getString(0) + "\n");
                    buffer.append("Имя: "+ res.getString(1) + "\n");
                    buffer.append("Фамилия: "+ res.getString(2) + "\n");
                    buffer.append("Количество выпитой воды: "+ res.getString(3) + "\n\n");
                }

                // Показать всю информацию
                showMessage("Data", buffer.toString());
            }
        });
    }
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
