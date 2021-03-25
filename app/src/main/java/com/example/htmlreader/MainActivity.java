package com.example.htmlreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    TextView htmlCodesTw;
    TextInputLayout textInputLayout;

    Button submitBtn;

    LinearLayout codesLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        htmlCodesTw = findViewById(R.id.htmlCodesTw);
        textInputLayout = findViewById(R.id.textInputLayout);
        submitBtn = findViewById(R.id.button);
        codesLinear = findViewById(R.id.codesLinear);

        codesLinear.setVisibility(View.GONE);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                htmlCodes(textInputLayout.getEditText().getText().toString());

                codesLinear.setVisibility(View.VISIBLE);

            }
        });

    }


    public void htmlCodes(String web) {



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(web);

        StringBuilder sb = null;

        try {
            HttpResponse response;
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream inputStream  = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"windows-1251"),8);
            sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            inputStream.close();


        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        htmlCodesTw.setText(sb.toString());

    }


}