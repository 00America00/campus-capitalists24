package com.example.campus_capitalists24;
// Import the required libraries
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class  MoneyFragment extends Fragment {

    PieChart pieChart;
    EditText editExpenses;
    EditText editIncome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_money, container, false);
        pieChart = view.findViewById(R.id.piechart);
        editExpenses = view.findViewById(R.id.editExpenses);
        editIncome = view.findViewById(R.id.editIncome);
        setData();
        //return inflater.inflate(R.layout.fragment_money, container, false);
        return view;
    }

    private void setData()
    {
        //int expenses = Integer.parseInt(editExpenses.getText().toString());
        //int income = Integer.parseInt(editIncome.getText().toString());
        int expenses = 100;
        int income = 30;
        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "Expenses",
                        expenses,
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Income",
                        income,
                        Color.parseColor("#66BB6A")));

        // To animate the pie chart
        pieChart.startAnimation();
    }
}