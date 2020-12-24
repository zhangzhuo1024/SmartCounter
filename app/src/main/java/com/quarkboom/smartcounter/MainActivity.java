package com.quarkboom.smartcounter;

import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private TextView mCounterNumber;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private int counter = 0;
    ArrayList<CounterBean> counterBeanList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerViewAdapter = new MyRecyclerViewAdapter();
        recyclerView.setAdapter(myRecyclerViewAdapter);
        mCounterNumber = ((TextView) findViewById(R.id.counter_number));
        findViewById(R.id.add).setOnClickListener(view -> {
            counter++;
            mCounterNumber.setText(counter + "");
        });
        findViewById(R.id.reset).setOnClickListener(view -> {
            CounterBean counterBean = new CounterBean();
            counterBean.setmName("1111");
            counterBean.setmTotal(counter + "");
            counterBeanList.add(counterBean);
            myRecyclerViewAdapter.updateData(counterBeanList);
            counter = 0;
        });
    }

}