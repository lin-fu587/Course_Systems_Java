package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView =findViewById(R.id.listView);
        Button btn_return=findViewById(R.id.btn_return);
        TextView tv_class=findViewById(R.id.textView);

        String[] messageArray=new String[]{"數位邏輯設計","電路學","應用軟體設計"};

        tv_class.setText("課程\\");

        ArrayAdapter<String> messageAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,messageArray);

        listView.setAdapter(messageAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);

                switch(position)
                {
                    case 0: {
                        intent.putExtra("course", messageArray[0]);
                        break;
                    }
                    case 1:{
                        intent.putExtra("course", messageArray[1]);
                        break;
                    }
                    case 2:{
                        intent.putExtra("course", messageArray[2]);
                        break;
                    }
                }
                startActivity(intent);
            }
        });
    }
}