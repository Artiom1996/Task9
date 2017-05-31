package com.epam.androidlab.task9;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    static final int READ_BLOCK_SIZE = 100;
    private String s;

    @BindView(R.id.btn_load)
    Button btnLoad;

    @BindView(R.id.btn_save)
    Button btnSave;

    @BindView(R.id.edit_text)
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_load)
    public void load() {
        readDocument();
    }

    @OnClick(R.id.btn_save)
    public void save() {
    writeDocument();
    }


    public void writeDocument(){

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    FileOutputStream fileOut=openFileOutput("mytextfile.txt", MODE_PRIVATE);
                    OutputStreamWriter outputWriter=new OutputStreamWriter(fileOut);
                    outputWriter.write(editText.getText().toString());
                    outputWriter.close();

                    Toast.makeText(getBaseContext(), "File saved successfully!",
                            Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public void readDocument(){

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    FileInputStream fileIn=openFileInput("mytextfile.txt");
                    InputStreamReader InputRead= new InputStreamReader(fileIn);

                    char[] inputBuffer= new char[READ_BLOCK_SIZE];
                     s="";
                    int charRead;

                    while ((charRead=InputRead.read(inputBuffer))>0) {

                        String readString=String.copyValueOf(inputBuffer,0,charRead);
                        s +=readString;
                    }
                    InputRead.close();
                    editText.setText(s);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
