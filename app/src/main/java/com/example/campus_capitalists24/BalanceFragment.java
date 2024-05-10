package com.example.campus_capitalists24;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BalanceFragment extends Fragment {

    private ListView listView;
    private TextView balanceView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);

        listView = view.findViewById(R.id.fragment_transaction_list);
        balanceView = view.findViewById(R.id.textView12);
        updateTransactionsAndView();

        return view;
    }

    private void updateTransactionsAndView() {
        List<String> transactions = new ArrayList<>();
        double balance = 0;

        // Read and process incomes
        try {
            InputStream is = getActivity().getAssets().open("moneyIn.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String date = parts[0];
                double amount = Double.parseDouble(parts[1]);
                String category = parts[2];
                balance += amount;
                transactions.add("Income : " + date + " + $" + amount + " (" + category + ")");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read and process expenses
        try {
            InputStream is = getActivity().getAssets().open("expenses.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String date = parts[2];
                double amount = Double.parseDouble(parts[3]);
                String category = parts[4];
                String note = parts[5];
                balance -= amount;
                transactions.add("Expense: " + date + " - $" + amount + " (" + category + ") Note: " + note);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        CustomAdapter adapter = new CustomAdapter(getActivity(), android.R.layout.simple_list_item_1, transactions);
        listView.setAdapter(adapter);
        balanceView.setText(String.format("$%.2f", balance));
    }

    private class CustomAdapter extends ArrayAdapter<String> {
        public CustomAdapter(Context context, int resource, List<String> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
            String item = getItem(position);
            SpannableString spannableString = new SpannableString(item);
            if (item.startsWith("Income")) {
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#228B22")), 0, "Income".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (item.startsWith("Expense")) {
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#B22222")), 0, "Income".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            textView.setText(spannableString);
            return convertView;
        }
    }
}
