package cn.com.self.service.impl;

import cn.com.self.domain.Activity;
import cn.com.self.domain.TbSysUser;
import cn.com.self.domain.User;
import cn.com.self.mapper.ActivityMapper;
import cn.com.self.mapper.TbSysUserMapper;
import cn.com.self.mapper.UserMapper;
import cn.com.self.service.ActivityService;
import cn.com.self.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)  //数据库只读
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private TbSysUserMapper tbSysUserMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ActivityMapper activityMapper;

    public List<Activity> getAllActivity(){
        List<Activity> result = new ArrayList<Activity>();
        try {
            result = activityMapper.selectAll();
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        return result;
    }

    public List<Activity> getActivity(String title, Date startTime, Date endTime){
        List<Activity> result = new ArrayList<Activity>();
        Example example = new Example(Activity.class);
        if(title.equals("")){
            example.createCriteria().andBetween("createTime",startTime,endTime);
        }
        else {
            example.createCriteria().andEqualTo("title", title).andBetween("createTime", startTime, endTime);
        }
        try {
            result = activityMapper.selectByExample(example);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        return result;
    }

    public Activity getActivityById(String actId){

        try{
            Activity result = activityMapper.selectByPrimaryKey(actId);
            return result;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public Integer editActivity(Activity activity){
        Integer result = 999;
        try{
            result = activityMapper.updateByPrimaryKey(activity);
        }catch (Exception e){
            System.out.println(e);
            return result;
        }
        return result;
    }



}
