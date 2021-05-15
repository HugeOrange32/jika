package cn.com.self.controller;


import cn.com.self.domain.Activity;
import cn.com.self.domain.User;
import cn.com.self.service.ActivityService;
import cn.com.self.service.AdminService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
public class ActivityController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ActivityService activityService;
    private Object IOException;

    @RequestMapping(value = "adminGetList", method = RequestMethod.GET)
    public String adminGetList(@RequestParam(value = "userID")String userId,
                               @RequestParam(value = "token")String token,
                               @RequestParam(value = "title")String title,
                               @RequestParam(value = "startTime",defaultValue = "2000-01-01 00:00:00") String startTime,
                               @RequestParam(value = "endTime",defaultValue = "2100-12-31 00:00:00")String endTime,
                               @RequestParam(value = "state")Integer state){


        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        List<Activity> result = new ArrayList<Activity>();

        if(userId.equals("")||token.equals("")){
            response.put("code","403");
            response.put("desc","请填写用户信息");
            return response.toJSONString();
        }

        User user = adminService.getUserById(userId);
        if(!token.equals(user.getToken())||user.getUserGroup().equals("0")){
            response.put("code","403");
            response.put("desc","用户信息错误");
            return response.toJSONString();
        }

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            Date startTimeDate = simpleDateFormat.parse(startTime);
            Date endTimeDate = simpleDateFormat.parse(endTime);

            result = activityService.getActivity(title,startTimeDate,endTimeDate);
            response.put("code","200");
            response.put("desc","请求成功");
            response.put("actList",result);

        }catch (Exception e){
            System.out.println(e);
            response.put("code","500");
            response.put("desc","查询活动服务错误");
            return response.toJSONString();
        }
        return response.toJSONString();
    }

    @RequestMapping(value = "upActivity",method = RequestMethod.POST)
    public String upActivity(@RequestParam(value = "userId")String userId,
                             @RequestParam(value = "token")String token,
                             @RequestParam(value = "upList")List<String> upList){

        JSONObject response = new JSONObject();
        //JSONObject data = new JSONObject();
        List<Activity> upSuccess = new ArrayList<Activity>();

        if(userId.equals("")||token.equals("")){
            response.put("code","403");
            response.put("desc","请填写用户信息");
            return response.toJSONString();
        }

        User user = adminService.getUserById(userId);
        if(!token.equals(user.getToken())||user.getUserGroup().equals("0")){
            response.put("code","403");
            response.put("desc","用户信息错误");
            return response.toJSONString();
        }

        if(upList.size()==0){
            response.put("code","403");
            response.put("desc","空请求");
            return response.toJSONString();
        }
        try {
            for (String actId : upList) {
                Activity upNow = activityService.getActivityById(actId);
                if(upNow.equals(null)){
                    throw new Exception("ID不存在");
                }
                if(upNow.getStatus().equals("0")){
                    if(upNow.getEndTime().getTime()>System.currentTimeMillis()){
                        upNow.setStatus("1");
                    }
                    else {
                        upNow.setStatus("2");
                    }
                    Integer editResult = activityService.editActivity(upNow);
                    if(editResult==999){
                        throw new Exception("修改失败");
                    }
                    else {
                        upSuccess.add(upNow);
                    }
                }
            }
            response.put("code","200");
            response.put("desc","上架成功");
            response.put("actList",upSuccess);
        }catch (Exception e){
            System.out.println(e);
            response.put("code","500");
            response.put("desc","查询活动服务错误");
            return response.toJSONString();
        }
        return response.toJSONString();
    }

    @RequestMapping(value = "downActivity",method = RequestMethod.POST)
    public String downActivity(@RequestParam(value = "userId")String userId,
                             @RequestParam(value = "token")String token,
                             @RequestParam(value = "downList")List<String> downList){

        JSONObject response = new JSONObject();
        //JSONObject data = new JSONObject();
        List<Activity> downSuccess = new ArrayList<Activity>();

        if(userId.equals("")||token.equals("")){
            response.put("code","403");
            response.put("desc","请填写用户信息");
            return response.toJSONString();
        }

        User user = adminService.getUserById(userId);
        if(!token.equals(user.getToken())||user.getUserGroup().equals("0")){
            response.put("code","403");
            response.put("desc","用户信息错误");
            return response.toJSONString();
        }

        if(downList.size()==0){
            response.put("code","403");
            response.put("desc","空请求");
            return response.toJSONString();
        }
        try {
            for (String actId : downList) {
                Activity downNow = activityService.getActivityById(actId);
                if(downNow.equals(null)){
                    throw new Exception("ID不存在");
                }
                if(!downNow.getStatus().equals("0")){
                    downNow.setStatus("0");
                    Integer editResult = activityService.editActivity(downNow);
                    if(editResult==999){
                        throw new Exception("修改失败");
                    }
                    else {
                        downSuccess.add(downNow);
                    }
                }
            }
            response.put("code","200");
            response.put("desc","上架成功");
            response.put("actList",downSuccess);
        }catch (Exception e){
            System.out.println(e);
            response.put("code","500");
            response.put("desc","查询活动服务错误");
            return response.toJSONString();
        }
        return response.toJSONString();
    }

}
