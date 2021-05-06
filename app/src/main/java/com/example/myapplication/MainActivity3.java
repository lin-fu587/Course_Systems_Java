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

public class MainActivity3 extends AppCompatActivity {

    public String[] messageArray;
    public String[] messageArray1=new String[]{"數位邏輯設計","電路學","應用軟體設計"};
    public static final String[][] messageArray2=new String[][]
            {{"講義","作業","解答"},
                    {"講義","歷屆考題","解答"},
                    {"講義","功課","實驗"}};

    public static final String[][] filename_array0=new String[][]
            {{"ld_ch1.pdf","ld_ch2.pdf","ld_ch3.pdf","ld_ch4.pdf","ld_ch5.pdf","ld_ch6.pdf","ld_ch7.pdf","ld_ch8.pdf","ld_ch9.pdf","ld_ch10.pdf"},
                    {"ld_ch7_hw.pdf","ld_ch8_hw.pdf"},
                    {"ld_ch1_hw_ok.pdf","ld_ch2_hw_ok.pdf","ld_ch3_hw_ok.pdf","ld_ch5_hw_ok.pdf","ld_ch6_hw_ok.pdf","ld_ch9_hw_ok.pdf","ld_ch10_hw_ok.pdf"}};
    public static final String[][] filename_array1=new String[][]
            {{"EE_CT_108_L0.pdf","EE_CT_108_L1.pdf","EE_CT_108_L2.pdf","EE_CT_108_L3.pdf","EE_CT_108_L4.pdf"},
                    {"EE_102-1 Final.pdf","EE_102-1 Midterm.pdf","EE_103-1 Final.pdf","EE_103-1 Midterm.pdf","EE_104-1 Final.pdf","EE_104-1 Midterm.pdf","EE_105-1 Final.pdf","EE_105-1 Midterm.pdf","EE_106-1 Final.pdf","EE_106-1 Midterm.pdf","EE_107-1 Final.pdf","EE_107-1 Midterm.pdf"},
                    {"EE_2013.docx","EE_2014.docx","EE_2015.docx","EE_2016.docx","EE_2017.docx"}};
    public static final String[][] filename_array2=new String[][]
            {{"pd_Chapter0.pdf","pd_Chapter1.pdf","pd_Chapter2.pdf","pd_Chapter3.pdf","pd_Chapter4.pdf","pd_Chapter5.pdf"},
                    {"pd_Homework CH0.pdf","pd_Homework CH1.pdf","pd_Homework CH2.pdf","pd_Homework CH3.pdf","pd_Homework CH4.pdf"},
                    {"pd_Lab0.pdf","pd_Lab1.pdf","pd_Lab2.pdf","pd_Lab3.pdf","pd_Lab4.pdf","pd_Lab5.pdf","pd_Lab6.pdf"}};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ListView listView =findViewById(R.id.listView);
        Button btn_return=findViewById(R.id.btn_return);
        TextView tv_item=findViewById(R.id.textView);

        Bundle course_and_item=getIntent().getExtras().getBundle("course_and_item");
        String course=course_and_item.getString("course");
        String item=course_and_item.getString("item");

        tv_item.setText("課程\\"+course+"\\"+item+"\\");

        if(course.equals(messageArray1[0]))
        {
            for (int k = 0;k<messageArray2[0].length; k++) {
                if(item.equals(messageArray2[0][k]))
                    messageArray = filename_array0[k];
            }
        }
        else if(course.equals(messageArray1[1]))
        {
            for (int k = 0;k<messageArray2[1].length; k++) {
                if(item.equals(messageArray2[1][k]))
                    messageArray = filename_array1[k];
            }
        }
        else if(course.equals(messageArray1[2]))
        {
            for (int k = 0;k<messageArray2[2].length; k++) {
                if(item.equals(messageArray2[2][k]))
                    messageArray = filename_array2[k];
            }
        }

        ArrayAdapter<String> messageAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,messageArray);

        listView.setAdapter(messageAdapter);

        String[] finalMessageArray = messageArray;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(MainActivity3.this,MainActivity4.class);//切換到讀取的地方

                Bundle course_item_order=course_and_item;
                course_item_order.putStringArray("list",finalMessageArray);

                for(int i = 0; i< finalMessageArray.length; i++)
                {
                    if(position==i)
                    {
                        course_item_order.putString("click_item_name",finalMessageArray[i]);
                        break;
                    }
                }

                intent.putExtra("course_item_order",course_item_order);
                startActivity(intent);
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}