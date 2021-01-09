package com.example.androidfiles;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String FILE_NAME="data.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button save=findViewById(R.id.save);
        Button load=findViewById(R.id.load);
        Button delete=findViewById(R.id.delete);
        save.setOnClickListener(this);
        load.setOnClickListener(this);
        delete.setOnClickListener(this);
    }
    private File getExternalPath()
    {
        return new File(getExternalFilesDir(null),FILE_NAME);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.save:
                saveExternal();
                break;
            case R.id.load:
                loadExternal();
                break;
            case R.id.delete:
                deleteExternal();
                break;
        }
    }
    private void save()
    {
        String text=((EditText)findViewById(R.id.text)).getText().toString();
        try {
            FileOutputStream out=openFileOutput(FILE_NAME,MODE_APPEND);
            out.write(text.getBytes());
            out.close();
            Toast.makeText(this,"В файл добавлены данные",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveExternal()
    {
        String text=((EditText)findViewById(R.id.text)).getText().toString();
        FileOutputStream out=null;
        try {
            out=new FileOutputStream(getExternalPath());
            out.write(text.getBytes());
            out.close();
            Toast.makeText(this,"В файл добавлены данные",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void load()
    {
        try {
            FileInputStream in=openFileInput(FILE_NAME);
            byte[] buffer=new byte[in.available()];
            in.read(buffer);
            in.close();
            String s=new String(buffer);
            ((TextView)findViewById(R.id.tv)).setText(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void loadExternal()
    {
        FileInputStream in=null;
        File file=getExternalPath();
        try {
            in=new FileInputStream(file);
            byte[] buffer=new byte[in.available()];
            in.read(buffer);
            in.close();
            String s=new String(buffer);
            ((TextView)findViewById(R.id.tv)).setText(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void delete()
    {
        if(deleteFile(FILE_NAME)) Toast.makeText(this,"Файл удален",Toast.LENGTH_LONG).show();
        else  Toast.makeText(this,"Ошибка удаления файла",Toast.LENGTH_LONG).show();
    }
    private void deleteExternal()
    {
        if(deleteFile(getExternalPath().getPath())) Toast.makeText(this,"Файл удален",Toast.LENGTH_LONG).show();
        else  Toast.makeText(this,"Ошибка удаления файла",Toast.LENGTH_LONG).show();
    }
}