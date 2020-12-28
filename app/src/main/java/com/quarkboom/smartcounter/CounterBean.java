package com.quarkboom.smartcounter;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: zhuozhang6
 * @date: 2020/12/24
 * @email: zhangzhuo1024@163.com
 */
@Entity
public class CounterBean {
    @Id(autoincrement = true)
    private Long id;
    @Property
    private String name;
    @Property
    private String startTime;
    @Property
    private String endTime;
    @Property
    private String total;
    @Property
    private String usedTime;
    @Generated(hash = 365994764)
    public CounterBean(Long id, String name, String startTime, String endTime,
            String total, String usedTime) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.total = total;
        this.usedTime = usedTime;
    }
    @Generated(hash = 1409174317)
    public CounterBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getStartTime() {
        return this.startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return this.endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getTotal() {
        return this.total;
    }
    public void setTotal(String total) {
        this.total = total;
    }
    public String getUsedTime() {
        return this.usedTime;
    }
    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }
}
