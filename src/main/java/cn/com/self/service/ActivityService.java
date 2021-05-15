package cn.com.self.service;

import cn.com.self.domain.Activity;
import cn.com.self.domain.TbSysUser;
import cn.com.self.domain.User;

import java.util.Date;
import java.util.List;

public interface ActivityService {

    /**
     * 获取所有活动
     */
    public List<Activity> getAllActivity();


    /**
     * 根据条件获取活动
     * @param title,startTime,endTime
     */
    public List<Activity> getActivity(String title, Date startTime, Date endTime);


    /**
     * 根据id获取活动
     * @param actId
     */
    public Activity getActivityById(String actId);


    /**
     * 根据id获取活动
     * @param activity
     */
    public Integer editActivity(Activity activity);





}
