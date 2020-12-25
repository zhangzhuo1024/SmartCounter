package com.quarkboom.smartcounter;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends BaseActivity {
    private TextView mCounterNumberText;
    private TextView mCurrentTime;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private int counter = 0;
    ArrayList<CounterBean> counterBeanList = new ArrayList<>();
    private String mStartTime;
    private String mEndTime;
    private Handler handler;

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
        mCounterNumberText = ((TextView) findViewById(R.id.counter_number));
        mCurrentTime = ((TextView) findViewById(R.id.current_time));

        findViewById(R.id.add).setOnClickListener(view -> {
            if (counter == 0) {
                mStartTime = getLocalTime();
            }
            counter++;
            mCounterNumberText.setText(counter + "");
        });
        findViewById(R.id.reset).setOnClickListener(view -> {
            if (counter > 0) {
                mEndTime = getLocalTime();
                CounterBean counterBean = new CounterBean();
                counterBean.setmName("1111");
                counterBean.setmTotal(counter + "次");
                counterBean.setmStartTime(mStartTime);
                counterBean.setmEndTime(mEndTime);
                counterBeanList.add(counterBean);
                myRecyclerViewAdapter.updateData(counterBeanList);
                counter = 0;
                mCounterNumberText.setText(counter + "");
            }
        });

        // 时间变化
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mCurrentTime.setText((String) msg.obj);
            }
        };
        Threads thread = new Threads();
        thread.start();
    }


    private String getNetTime() {
        URL url = null;//取得资源对象
        try {
            url = new URL("http://www.baidu.com");
            //url = new URL("http://www.ntsc.ac.cn");//中国科学院国家授时中心
            //url = new URL("http://www.bjtime.cn");
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            long ld = uc.getDate(); //取得网站日期时间
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(ld);
            String format = formatter.format(calendar.getTime());
            return format;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getLocalTime() {
        URL url = null;//取得资源对象
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            String format = formatter.format(calendar.getTime());
            return format;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    class Threads extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                    String time = (sdf.format(System.currentTimeMillis())).split(" ")[1];
                    handler.sendMessage(handler.obtainMessage(100, time));
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}