package com.example.campus_capitalists24;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.widget.TextView;

import com.example.campus_capitalists24.databinding.DashboardBinding;

import java.io.IOException;
import java.util.Scanner;
public class DashboardActivity extends AppCompatActivity {
    private Accounts profileInfo;
    private AssetManager assets;

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
        fragmentTransaction.commit();
    }
    public void setupProfile() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);

        //profileInfo = new Accounts(id,assets);

        Scanner scan;
        String str = "";
        String[] arr = null;

        try {
            scan = new Scanner(assets.open("accounts.txt"));
            while(scan.hasNext()){
                str = scan.nextLine();
                arr = str.split(",");
                if(Integer.parseInt(arr[0]) == id){
                    profileInfo = new Accounts(id,arr[1],arr[2]);
                    break;
                }
            }
            scan.close();
        }
        catch (IOException e){
            System.out.println("Error " + e.getMessage());
        }

        TextView name = (TextView) findViewById(R.id.welcome);
        TextView email = (TextView) findViewById(R.id.home1);
        name.setText(profileInfo.getName());
        email.setText(profileInfo.getEmail());

    }
}
