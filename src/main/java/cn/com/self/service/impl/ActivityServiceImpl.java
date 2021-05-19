package cn.com.self.service.impl;

import cn.com.self.domain.ActUsr;
import cn.com.self.domain.Activity;
import cn.com.self.domain.TbSysUser;
import cn.com.self.domain.User;
import cn.com.self.mapper.ActUsrMapper;
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

    @Autowired
    private ActUsrMapper actUsrMapper;

    @Override
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

    @Override
    public List<Activity> getActivity(String title, Date startTime, Date endTime,Integer state){
        List<Activity> result = new ArrayList<Activity>();
        Example example = new Example(Activity.class);
        if(state==null){
            if(title.equals("")){
                example.createCriteria().andBetween("createTime",startTime,endTime);
            }
            else {
                example.createCriteria().andEqualTo("title", title).andBetween("createTime", startTime, endTime);
            }
        } else {
            if(title.equals("")){
                example.createCriteria().andEqualTo("status",state).andBetween("createTime",startTime,endTime);
            }
            else {
                example.createCriteria().andEqualTo("title", title).andEqualTo("status",state).andBetween("createTime", startTime, endTime);
            }
        }

        try {
            result = activityMapper.selectByExample(example);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        return result;
    }

    @Override
    public Activity getActivityById(String actId){

        try{
            Activity result = activityMapper.selectByPrimaryKey(actId);
            return result;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }


    @Override
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


    @Override
    @Transactional(readOnly = false)
    public String addActivity(Activity activity){
        try{
            Integer result = activityMapper.insert(activity);
            return "200";
        }catch (Exception e){
            System.out.println(e);
            return "403";
        }
    }

    @Override
    public Integer getJoinedNum(String actId){
        Integer result = 0;
        Example example = new Example(ActUsr.class);
        try{
            example.createCriteria().andEqualTo("actId",actId);
            result = actUsrMapper.selectByExample(example).size();
            return result;
        }
        catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }

    @Override
    public Integer getCompletedNum(String actId){
        Integer result = 0;
        Example example = new Example(ActUsr.class);
        try{
            example.createCriteria().andEqualTo("actId",actId).andNotEqualTo("cardnum1",0).andNotEqualTo("cardnum2",0).andNotEqualTo("cardnum3",0).andNotEqualTo("cardnum4",0).andNotEqualTo("cardnum5",0);
            result = actUsrMapper.selectByExample(example).size();
            return result;
        }
        catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }

    @Override
    public List<Activity> getActivityByTitle(String actTitle){
        List<Activity> result = new ArrayList<Activity>();
        Example example = new Example(Activity.class);
        try{
            example.createCriteria().andEqualTo("title",actTitle);
            result = activityMapper.selectByExample(example);
            return result;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public int joinActivity(ActUsr actUsr){
        int result = 999;
        try{
            result = actUsrMapper.insert(actUsr);
            return result;
        }catch (Exception e){
            System.out.println(e);
            return result;
        }
    }



}
