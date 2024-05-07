package com.example.campus_capitalists24;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.campus_capitalists24.databinding.DashboardBinding;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends ComponentActivity {
    //private Button button;
   // private AssetManager assets;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //assets = getAssets();
        //Toast.makeText(this,"Hello World", Toast.LENGTH_SHORT).show();
        setupButtons();
    }

    private void setupButtons() {
        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText uText = (EditText) findViewById(R.id.Username);
                EditText pText = (EditText) findViewById(R.id.Password);
                int id = authenticate(uText.getText().toString(), pText.getText().toString());
                if (id > 0) {
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else {
                    uText.setText("");
                    pText.setText("");
                    uText.setError("Incorrect username and password combination ");
                    pText.setError("Incorrect username and password combination");
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
    private int authenticate(String username, String password) {
        Scanner scan;
        String str = "";
        String[] arr = null;
        //boolean authenticated = false;
        int id = -1;
        File f = new File(getFilesDir().getAbsolutePath() + "/login.txt");

        try {
            if(f.exists()) {
                scan = new Scanner(openFileInput("login.txt"));
                while (scan.hasNext()) {
                    str = scan.nextLine();
                    arr = str.split(",");
                    if (username.equalsIgnoreCase(arr[1]) && password.equals(arr[2])) {
                        //authenticated = true;
                        id = Integer.parseInt(arr[0]);
                        break;
                    }
                }
                scan.close();
            }
        }
        catch (IOException e){
            System.out.println("Error " + e.getMessage());
        }
        return id;
    }
}
