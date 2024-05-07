package com.example.campus_capitalists24;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class RegisterActivity extends ComponentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        setupButtons();
    }

    private void setupButtons() {
        Button button1 = (Button) findViewById(R.id.submit_Button);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int id = -1;
                EditText unameInput = (EditText) findViewById(R.id.new_UserInput);
                EditText passInput = (EditText) findViewById(R.id.new_passwordInput);
                EditText nameInput = (EditText) findViewById(R.id.new_nameInput);
                EditText emailInput = (EditText) findViewById(R.id.new_emailInput);

                if(validateAccountInfo()) {
                    id = createLogin();
                    if(id > 0){
                        createAccount(id);
                    }
                    finish();
                }
                else{
                    unameInput.setText("");
                    passInput.setText("");
                    nameInput.setText("");
                    emailInput.setText("");
                    unameInput.setError("All Fields must be filled out");
                    passInput.setError("All Fields must be filled out");
                    nameInput.setError("All Fields must be filled out");
                    emailInput.setError("All Fields must be filled out");
                }
            }
        });
    }

    private boolean validateAccountInfo(){
        EditText unameInput = (EditText) findViewById(R.id.new_UserInput);
        EditText passInput = (EditText) findViewById(R.id. new_passwordInput);
        EditText nameInput = (EditText) findViewById(R.id.new_nameInput);
        EditText emailInput = (EditText) findViewById(R.id.new_emailInput);

        if(!unameInput.getText().toString().isEmpty() && !passInput.getText().toString().isEmpty()
                && !nameInput.getText().toString().isEmpty()  && !emailInput.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }

    private int createLogin()  {
        EditText unameInput = (EditText) findViewById(R.id.new_nameInput);
        EditText passInput = (EditText) findViewById(R.id.new_passwordInput);
        String username = unameInput.getText().toString();
        String password = passInput.getText().toString();

        File f = new File(getFilesDir().getAbsolutePath() + "/login.txt");
        OutputStreamWriter w = null;
        Scanner scan;
        int id = -1;
        String str = null;
        String[] arr;

        if(!f.exists()){
            id = 1;
            try {
                w = new OutputStreamWriter(openFileOutput("login.txt",MODE_PRIVATE));
                w.write(id + "," + username + "," + password);
                w.close();
            }
            catch (IOException e){
                Toast.makeText(getBaseContext(),"IOException Here",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            try {
                scan = new Scanner(openFileInput("login.txt"));
                while (scan.hasNextLine()) {
                    str = scan.nextLine();
                }
                if (str != null){
                    arr = str.split(",");
                    if (arr.length == 3){
                        id = Integer.parseInt(arr[0]) + 1;
                    }
                }
                scan.close();
                w = new OutputStreamWriter(openFileOutput("login.txt",MODE_APPEND));
                w.append("\n" + id + "," + username + "," + password);
                w.close();
            }
            catch (IOException e){
                Toast.makeText(getBaseContext(),"IOException 22",Toast.LENGTH_SHORT).show();
            }
        }
        return id;
    }

    private void createAccount(int id){
        EditText nameInput = (EditText) findViewById(R.id.new_nameInput);
        EditText emailInput = (EditText) findViewById(R.id.new_emailInput);
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();

        File f = new File(getFilesDir().getAbsolutePath() + "/accounts.txt");
        OutputStreamWriter w = null;
        if(!f.exists()){
            try {
                w = new OutputStreamWriter(openFileOutput("accounts.txt",MODE_PRIVATE));
                w.write(id + "," + name + "," + email);
                w.close();
            }
            catch (IOException e){
                Toast.makeText(getBaseContext(),"IOException 1",Toast.LENGTH_SHORT).show();
            }

        }
        else {
            try {
                w = new OutputStreamWriter(openFileOutput("accounts.txt", MODE_APPEND));
                w.append("\n" + id + "," + name + "," + email);
                w.close();
            }
            catch (IOException e){
                Toast.makeText(getBaseContext(),"IOException 2",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
