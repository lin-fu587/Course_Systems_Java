package com.example.myapplication;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity4 extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener
{
    private static final int MY_PERMISSION_RQUESY_STRAGE = 1;
    private static final String TAG = MainActivity4.class.getSimpleName();
    public static String filename = "Lab1.pdf";



    private Button btn_down,btn_return;
    private PDFView pdfView;
    private Integer pageNumber = 0;
    private String pdfFileName;
    private TextView tv_progress;
    private LinearLayout ll_progress;
    private ProgressBar progressBar2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        if(ContextCompat.checkSelfPermission(MainActivity4.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity4.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_RQUESY_STRAGE);
        }

        tv_progress=findViewById(R.id.tv_progress);
        ll_progress=findViewById(R.id.ll_progress);
        progressBar2=findViewById(R.id.progressBar2);
        btn_down = (Button) findViewById(R.id.btn_download);
        pdfView = (PDFView)findViewById(R.id.pdfView);
        btn_return=findViewById(R.id.btn_return);

        String[] messageArray1=new String[]{"數位邏輯設計","電路學","應用軟體設計"};

        Bundle course_item_order=getIntent().getExtras().getBundle("course_item_order");
        String course=course_item_order.getString("course");
        String[] list=course_item_order.getStringArray("list");
        String click_item_name=course_item_order.getString("click_item_name");

        for(int j=0;j<messageArray1.length;j++)
        {
            for(int k=0;k<list.length;k++)
            {
                if(course.equals(messageArray1[j]) && click_item_name.equals(list[k]))
                {
                    filename = list[k];
                    j=messageArray1.length+1;
                    break;
                }
            }
        }

        if((filename.substring(filename.length()-5)).equals(".docx"))
        {
            filename=filename.substring(0,(filename.length()-5))+".pdf";
            displayFromAsset(filename);
        }
        else
            displayFromAsset(filename);

        btn_down.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if((filename.substring(filename.length()-5)).equals(".docx"))
                {
                    filename=filename.substring(0,(filename.length()-5))+".pdf";
                    runAsyncTask();
                }
                else {
                    runAsyncTask();
                }
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

    }

    @SuppressLint("SaticFieldLeak")
    private void runAsyncTask() {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                progressBar2.setProgress(0);
                tv_progress.setText("0%");
                ll_progress.setVisibility(View.VISIBLE);
            }
            @Override
            protected Boolean doInBackground(Void... voids) {
                int progress =0;
                while (progress<=100) {
                    try {
                        Thread.sleep(50);
                        publishProgress(progress);
                        progress++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                progressBar2.setProgress(values[0]);
                tv_progress.setText(values[0]+"%");
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                ll_progress.setVisibility(View.GONE);
                copyasset(filename);
            }
        }.execute();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == MY_PERMISSION_RQUESY_STRAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(MainActivity4.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            } else {
                Toast.makeText(this, "NO per", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void copyasset(String file)
    {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download";
        File dir = new File(dirPath);
        if(!dir.exists())
        {
            dir.mkdirs();
        }
        AssetManager assetManager = getAssets();
        InputStream in = null;
        OutputStream out = null;
        try
        {
            in = assetManager.open(file);
            File outFile = new File(dirPath,file);
            out = new FileOutputStream(outFile);
            copyFile(in,out);
            Toast.makeText(this,"成功下載\t"+filename+"\t到手機中",Toast.LENGTH_SHORT).show();

        }
        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(this,"下載失敗",Toast.LENGTH_SHORT).show();

        }
        finally
        {
            if(in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if(out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void copyFile(InputStream in , OutputStream out)throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1)
        {
            out.write(buffer,0,read);
        }
    }
    @Override
    public void onPageChanged(int page, int pageCount)
    {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }


    private void displayFromAsset(String assetFileName)
    {
        pdfFileName = assetFileName;

        pdfView.fromAsset(filename)
                .defaultPage(pageNumber)
                .enableSwipe(true)

                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }

    @Override
    public void loadComplete(int nbPages)
    {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep)
    {
        for (PdfDocument.Bookmark b : tree)
        {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren())
            {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }
}