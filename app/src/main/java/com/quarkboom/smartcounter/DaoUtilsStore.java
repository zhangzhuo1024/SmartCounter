package com.quarkboom.smartcounter;

/**
 * @author: zhuozhang6
 * @date: 2020/12/28
 * @email: zhangzhuo1024@163.com
 */
public class DaoUtilsStore {
    private volatile static DaoUtilsStore instance;
    private CommonDaoUtils<CounterBean> mUserDaoUtils;

    public static DaoUtilsStore getInstance() {
        if (instance == null) {
            instance = new DaoUtilsStore();
        }
        return instance;
    }

    private DaoUtilsStore() {
        DaoManager mManager = DaoManager.getInstance();
        CounterBeanDao _UserDao = mManager.getDaoSession().getCounterBeanDao();
        mUserDaoUtils = new CommonDaoUtils<>(CounterBean.class, _UserDao);
    }

    public CommonDaoUtils<CounterBean> getUserDaoUtils() {
        return mUserDaoUtils;
    }

}
