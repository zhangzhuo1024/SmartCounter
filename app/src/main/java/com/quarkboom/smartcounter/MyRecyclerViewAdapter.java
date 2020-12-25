package com.quarkboom.smartcounter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * @author: zhuozhang6
 * @date: 2020/12/24
 * @email: zhangzhuo1024@163.com
 */
class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyHolder> {

    private ArrayList<CounterBean> mCounterBeanList = new ArrayList<>();

    public MyRecyclerViewAdapter() {

    }

    public void updateData(ArrayList<CounterBean> counterBeanList) {
        this.mCounterBeanList = counterBeanList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.recycler_view_item, null);
        MyHolder myHolder = new MyHolder(inflate);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.setData(mCounterBeanList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCounterBeanList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private TextView name;
        private TextView start_text;
        private TextView end_text;
        private TextView total_time;
        private CounterBean mCounterBean;


        public MyHolder(View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            name = itemView.findViewById(R.id.name);
            start_text = itemView.findViewById(R.id.start_text);
            end_text = itemView.findViewById(R.id.end_text);
            total_time = itemView.findViewById(R.id.total_time);

            itemView.setOnClickListener(view -> {
//                Intent intent = new Intent(mContext, NewsHtmlActivity.class);
//                intent.putExtra("url", mCounterBean.getPath());
//                mContext.startActivity(intent);


            });
        }

        public void setData(CounterBean CounterBean) {
            this.mCounterBean = CounterBean;
            name.setText(CounterBean.getmName());
            start_text.setText(CounterBean.getmStartTime());
            end_text.setText(CounterBean.getmEndTime());
            total_time.setText(CounterBean.getmTotal());

        }
    }
}
