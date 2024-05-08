package com.example.campus_capitalists24;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.accounts.Account;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.widget.TextView;

import com.example.campus_capitalists24.databinding.DashboardBinding;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class DashboardActivity extends AppCompatActivity {
    private Accounts profileInfo;
    //private AssetManager assets;

    DashboardBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            switch(menuItem.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.expense:
                    replaceFragment(new ExpenseListFragment());
                    break;
                case R.id.balance:
                    replaceFragment(new BalanceFragment());
                    break;
                case R.id.money:
                    replaceFragment(new MoneyFragment());
                    break;
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);

        // Pass user ID as an argument to the fragment
        Bundle bundle = new Bundle();
        bundle.putInt("id", getIntent().getIntExtra("id", -1));
        fragment.setArguments(bundle);

        fragmentTransaction.commit();
    }
    public void setupProfile() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);

        //profileInfo = new Account(id,assets);

        File f = new File(getFilesDir().getAbsolutePath() + "/accounts.txt");
        Scanner scan;
        String str = "";
        String[] arr = null;

        try {
            if(f.exists()) {
                scan = new Scanner(openFileInput("accounts.txt"));
                while (scan.hasNext()) {
                    str = scan.nextLine();
                    arr = str.split(",");
                    if (Integer.parseInt(arr[0]) == id) {
                        profileInfo = new Accounts(id, arr[1], arr[2]);
                        break;
                    }
                }
                scan.close();
            }
        }
        catch (IOException e){
            System.out.println("Error " + e.getMessage());
        }

        if(profileInfo != null) {
            TextView name = (TextView) findViewById(R.id.home1);
            TextView email = (TextView) findViewById(R.id.welcome);
            name.setText(profileInfo.getName());
            email.setText(profileInfo.getEmail());
        }
    }
}
