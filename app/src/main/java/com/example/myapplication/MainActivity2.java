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

public class MainActivity2 extends AppCompatActivity {

    public String[] messageArray;
    public String[] messageArray1=new String[]{"數位邏輯設計","電路學","應用軟體設計"};
    public static final String[][] messageArray2=new String[][]
            {{"講義","作業","解答"},
                    {"講義","歷屆考題","解答"},
                    {"講義","功課","實驗"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ListView listView =findViewById(R.id.listView);
        Button btn_return=findViewById(R.id.btn_return);
        TextView tv_course=findViewById(R.id.textView);

        String course=getIntent().getExtras().getString("course");

        tv_course.setText("課程\\"+course+"\\");

        for(int i=0;i<messageArray1.length;i++) {
            if (course.equals(messageArray1[i])) {
                messageArray = messageArray2[i];
            }
        }
        ArrayAdapter<String> messageAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,messageArray);

        listView.setAdapter(messageAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(MainActivity2.this, MainActivity3.class);

                Bundle course_and_item=new Bundle();
                course_and_item.putString("course",course);

                switch(position)
                {
                    case 0: {
                        course_and_item.putString("item",messageArray[0]);
                        break;
                    }
                    case 1:{
                        course_and_item.putString("item",messageArray[1]);
                        break;
                    }
                    case 2:{
                        course_and_item.putString("item",messageArray[2]);
                        break;
                    }
                    case 3:{
                        course_and_item.putString("item",messageArray[3]);
                        break;
                    }
                }
                intent.putExtra("course_and_item",course_and_item);
                startActivity(intent);
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

    }
}