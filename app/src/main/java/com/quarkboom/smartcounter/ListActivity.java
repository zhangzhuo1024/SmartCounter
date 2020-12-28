package com.quarkboom.smartcounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhuozhang6
 * @date: 2020/12/28
 * @email: zhangzhuo1024@163.com
 */
public class ListActivity extends BaseActivity {
    List<CounterBean> listAll = new ArrayList<>();
    ArrayList<CounterBean> counterBeanList = new ArrayList<>();
    private MyRecyclerViewAdapter myRecyclerViewAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerViewAdapter = new MyRecyclerViewAdapter();
        recyclerView.setAdapter(myRecyclerViewAdapter);
        listAll = DaoUtilsStore.getInstance().getUserDaoUtils().queryAll();
        myRecyclerViewAdapter.updateData(listAll);
    }

}