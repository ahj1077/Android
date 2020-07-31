package com.an.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistoryActivity extends AppCompatActivity {

    ListView historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyList = findViewById(R.id.lv_hist);
        HistoryAdapter adapter = new HistoryAdapter(this, R.layout.history_item) ;
        adapter.setHistory(History.history);
        historyList.setAdapter(adapter) ;
    }
}
