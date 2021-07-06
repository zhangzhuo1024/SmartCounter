package com.quarkboom.smartcounter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: zhuozhang6
 * @date: 2020/12/24
 * @email: zhangzhuo1024@163.com
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.current_time)
    LedText currentTime;
    @BindView(R.id.settings_layout)
    RelativeLayout settingsLayout;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.start_text)
    TextView startText;
    @BindView(R.id.end_text)
    TextView endText;
    @BindView(R.id.end_layout)
    LinearLayout endLayout;
    @BindView(R.id.use_time)
    TextView useTime;
    @BindView(R.id.count_total)
    TextView countTotal;
    @BindView(R.id.counter_number)
    TextView counterNumber;
    @BindView(R.id.subtract)
    ImageView subtract;
    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.reset)
    ImageView reset;
    @BindView(R.id.list_icon)
    ImageView listIcon;

    private int counter = 0;
    ArrayList<CounterBean> counterBeanList = new ArrayList<>();
    private String mStartTime;
    private String mEndTime;
    private Handler handler;
    private SoundPoolUtil soundPoolUtil;
    private Vibrator vibrator;
    private long startTimeMillis;
    private long endTimeMillis;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {


        // 时间变化
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                currentTime.setText((String) msg.obj);
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

    private long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    private String getLocalTime(long currentTimeMillis) {
        URL url = null;//取得资源对象
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(currentTimeMillis);
            String format = formatter.format(calendar.getTime());
            return format;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.list_icon, R.id.current_time, R.id.name, R.id.start_text, R.id.end_text, R.id.use_time, R.id.count_total, R.id.counter_number, R.id.subtract, R.id.add, R.id.reset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.list_icon:
                startActivity(new Intent(this, ListActivity.class));
                break;
            case R.id.current_time:
                break;
            case R.id.name:
                break;
            case R.id.start_text:
                break;
            case R.id.end_text:
                break;
            case R.id.use_time:
                break;
            case R.id.count_total:
                break;
            case R.id.counter_number:
                break;
            case R.id.subtract:
                if (counter > 0) {
                    counter--;
                    counterNumber.setText(counter + "");
                    soundPoolUtil.play(1);
                    vibrator.vibrate(new long[]{0, 35}, -1);
                }
                break;
            case R.id.add:
                if (counter == 0) {
                    startTimeMillis = getCurrentTimeMillis();
                    mStartTime = getLocalTime(startTimeMillis);
                    startText.setText(mStartTime.split(" ")[1]);
                    endText.setText("00:00:00");
                    useTime.setText("00:00:00");
                    countTotal.setText("0次");
                    name.setText("默认");
                }
                counter++;
                counterNumber.setText(counter + "");
                soundPoolUtil.play(1);
                vibrator.vibrate(new long[]{0, 35}, -1);
                break;
            case R.id.reset:
                if (counter > 0) {
                    endTimeMillis = getCurrentTimeMillis();
                    mEndTime = getLocalTime(endTimeMillis);
                    CounterBean counterBean = new CounterBean();
                    counterBean.setName("1111");
                    counterBean.setTotal(counter + "次");
                    counterBean.setStartTime(mStartTime);
                    counterBean.setEndTime(mEndTime);
                    counterBean.setUsedTime(changeToDate(endTimeMillis - startTimeMillis));
                    counterBean.setName(name.getText().toString());
                    counterBeanList.add(counterBean);
                    // 插入新用户
                    DaoUtilsStore.getInstance().getUserDaoUtils().insert(counterBean);
                    startText.setText(counterBean.getStartTime().split(" ")[1]);
                    endText.setText(counterBean.getEndTime().split(" ")[1]);
                    countTotal.setText(counterBean.getTotal());
                    useTime.setText(counterBean.getUsedTime());
                    counter = 0;
                    counterNumber.setText(counter + "");
                    soundPoolUtil.play(2);
                    vibrator.vibrate(new long[]{0, 80}, -1);
                }
                break;
        }
    }

    private String changeToDate(long l) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        String time = (sdf.format(l + 0.5)).split(" ")[1];
        return time;
    }

    @OnClick(R.id.list_icon)
    public void onClick() {
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