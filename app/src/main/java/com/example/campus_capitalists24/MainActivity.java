package com.example.campus_capitalists24;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends ComponentActivity {
    //private Button button;
    //private AssetManager assets;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //assets = getAssets();
        //Toast.makeText(this,"Hello World", Toast.LENGTH_SHORT).show();
       // setupButtons();
    }
}