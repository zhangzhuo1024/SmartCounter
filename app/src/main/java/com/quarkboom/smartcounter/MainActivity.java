package com.quarkboom.smartcounter;

import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author: zhuozhang6
 * @date: 2020/12/24
 * @email: zhangzhuo1024@163.com
 */
public class MainActivity extends BaseActivity {
    private TextView mCounterNumberText;
    private TextView mCurrentTime;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private int counter = 0;
    ArrayList<CounterBean> counterBeanList = new ArrayList<>();
    private String mStartTime;
    private String mEndTime;
    private Handler handler;
    private SoundPoolUtil soundPoolUtil;
    private Vibrator vibrator;

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
            soundPoolUtil.play(1);
            vibrator.vibrate(new long[]{0, 35}, -1);
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
                soundPoolUtil.play(2);
                vibrator.vibrate(new long[]{0, 80}, -1);
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

        soundPoolUtil = SoundPoolUtil.getInstance(this);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
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