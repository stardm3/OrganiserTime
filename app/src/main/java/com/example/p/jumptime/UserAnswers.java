package com.example.p.jumptime;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserAnswers extends Fragment {
    Button b_exit;
    String cat_day; // категория дня отличная, нормальная, плохая

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_answers, container, false);

        final EditText ed1 = view.findViewById(R.id.important_deal);
        final EditText ed2 = view.findViewById(R.id.remember);
        final EditText ed3 = view.findViewById(R.id.new_experience);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            cat_day = bundle.getString("key");
        }

        b_exit = view.findViewById(R.id.button_end);
        b_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed1.getText().toString().compareTo("") == 0 && ed2.getText().toString().compareTo("") == 0 && ed3.getText().toString().compareTo("") == 0) {
                } else {

                    // Текущее время
                    Date currentDate = new Date();
                    // Форматирование времени как "день.месяц.год"
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                    String data_day = dateFormat.format(currentDate);
                    ContentValues cvq = new ContentValues();
                    DataBase.DBHelper dbHelper = new DataBase.DBHelper(getContext());
                    // подключаемся к БД
                    SQLiteDatabase dbq = dbHelper.getWritableDatabase();

                    cvq.put("whatday", cat_day);
                    cvq.put("importantdeal", ed1.getText().toString());
                    cvq.put("remember", ed2.getText().toString());
                    cvq.put("newexpereance", ed3.getText().toString());
                    cvq.put("time", data_day + "");

                    // вставляем запись и получаем ее ID
                    long rowID = dbq.insert("table_user_day", null, cvq);
                    Toast.makeText(getContext(), rowID + "", Toast.LENGTH_SHORT).show();
                    ed1.setText("");
                    ed2.setText("");
                    ed3.setText("");

                }
            }
        });
        return view;

    }

}