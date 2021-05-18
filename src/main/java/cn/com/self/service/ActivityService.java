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
     * @param title,startTime,endTime,state
     */
    public List<Activity> getActivity(String title, Date startTime, Date endTime ,Integer state);


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


    /**
     * 添加活动
     * @param activity
     */
    public String addActivity(Activity activity);


    /**
     * 获取活动的参与人数
     * @param actId
     */
    public Integer getJoinedNum(String actId);

    /**
     * 获取集满卡片的人数
     * @param actId
     */
    public Integer getCompletedNum(String actId);





}
