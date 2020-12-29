package com.quarkboom.smartcounter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhuozhang6
 * @date: 2020/12/24
 * @email: zhangzhuo1024@163.com
 */
class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyHolder> {

    private List<CounterBean> mCounterBeanList = new ArrayList<>();

    public MyRecyclerViewAdapter() {

    }

    public void updateData(List<CounterBean> counterBeanList) {
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

        holder.setData(mCounterBeanList.get(position), position);
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
        private int mPosition;


        public MyHolder(View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            name = itemView.findViewById(R.id.name);
            start_text = itemView.findViewById(R.id.start_text);
            end_text = itemView.findViewById(R.id.end_text);
            total_time = itemView.findViewById(R.id.total_time);

            itemView.setOnClickListener(view -> {
                final String[] items = {"修改名称", "删除当前条目", "清空所有数据"};
                AlertDialog.Builder listDialog = new AlertDialog.Builder(mContext);
                listDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onItemClicked(dialog, which);
                    }
                });
                listDialog.create().show();
            });
        }

        private void onItemClicked(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    EditText et = new EditText(mContext);
                    new AlertDialog.Builder(mContext).setTitle("设置名称：")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setView(et)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String input = et.getText().toString();
                                    if (input.equals("")) {
                                        Toast.makeText(mContext, "内容不能为空！" + input, Toast.LENGTH_LONG).show();
                                    } else {
                                        mCounterBean.setName(input);
                                        notifyItemRangeChanged(mPosition, getItemCount());
                                    }
                                    DaoUtilsStore.getInstance().getUserDaoUtils().update(mCounterBean);
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                    break;
                case 1:
                    mCounterBeanList.remove(mPosition);//删除数据源,移除集合中当前下标的数据
                    notifyItemRemoved(mPosition);//刷新被删除的地方
                    notifyItemRangeChanged(mPosition, getItemCount()); //刷新被删除数据，以及其后面的数据
                    DaoUtilsStore.getInstance().getUserDaoUtils().delete(mCounterBean);
                    break;
                case 2:
                    new AlertDialog.Builder(mContext).setTitle("清空后不可恢复，确定要清空吗")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mCounterBeanList.clear();
                                    DaoUtilsStore.getInstance().getUserDaoUtils().deleteAll();
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                    break;
                default:
                    break;
            }
        }

        public void setData(CounterBean CounterBean, int position) {
            this.mCounterBean = CounterBean;
            this.mPosition = position;
            name.setText(CounterBean.getName());
            start_text.setText(CounterBean.getStartTime());
            end_text.setText(CounterBean.getEndTime());
            total_time.setText(CounterBean.getTotal());

        }
    }
}
