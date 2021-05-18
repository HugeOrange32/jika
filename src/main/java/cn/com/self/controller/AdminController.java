package cn.com.self.controller;

import cn.com.self.domain.TbSysUser;
import cn.com.self.domain.User;
import cn.com.self.dto.BaseResult;
import cn.com.self.service.AdminService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.common.util.report.qual.ReportWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.text.ParseException;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;



    @RequestMapping(value = "test",method = RequestMethod.GET)
    public String test(String test){
        return "test";
    }


    @RequestMapping(value = "userCheck",method = RequestMethod.GET)
    public String userCheck(@RequestParam(value = "userId") String userId){
        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        String resultcode = adminService.checkUser(userId);
        if(resultcode.equals("200")){
            response.put("code",200);
            response.put("desc","请求成功");
            data.put("code",1);
            data.put("desc","用户名未占用");
            response.put("data",data);

        }
        else if(resultcode.equals("403")){
            response.put("code",200);
            response.put("desc","请求成功");
            data.put("code",0);
            data.put("desc","用户名已占用");
            response.put("data",data);
        }
        else {
            response.put("code",500);
            response.put("desc","server error");
        }
        return response.toJSONString();
    }


    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ResponseBody
    public String register(HttpSession httpSession,
                           @RequestBody JSONObject accept) throws ParseException {
        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        //JSONObject accept = JSONObject.parseObject(acceptString);
        String userId = accept.getString("userId");
        String userName = accept.getString("userName");
        String password = accept.getString("password");
        Integer gender = accept.getInteger("gender");
        Integer group = accept.getInteger("group");
        String phoneNum = accept.getString("phoneNum");
        if(userId.equals("")){
            response.put("code",403);
            response.put("desc","请填写用户名");
            return response.toJSONString();
        }
        if(userName.equals("")){
            response.put("code",403);
            response.put("desc","请填写昵称");
            return response.toJSONString();
        }
        if(password.equals("")){
            response.put("code",403);
            response.put("desc","请填写密码");
            return response.toJSONString();
        }
        if(gender.equals("")){
            response.put("code",403);
            response.put("desc","请填写性别");
            return response.toJSONString();
        }
        String checkIdStatus = adminService.checkUser(userId);
        if(checkIdStatus.equals("403")||checkIdStatus.equals("500")){
            response.put("code",checkIdStatus);
            response.put("desc","检查用户名唯一失败");
            return response.toJSONString();
        }

        User user = new User();
        user.setUserId(userId);
        user.setName(userName);
        user.setPassword(password);
        user.setGender(gender.toString());
        user.setUserGroup(group.toString());
        user.setPhoneNum(phoneNum);
        user.setRegisterTime(new Date(System.currentTimeMillis()));
        user.setLoginFlag("0");
        user.setToken(UUID.randomUUID().toString().replace("-",""));
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date tokenExpTime = dateFormat2.parse("2099-12-31 00:00:00");
        user.setTokenExptime(tokenExpTime);

        try{
            String registerStatus = adminService.register(user);
            if(registerStatus.equals("200")){
                response.put("code",200);
                response.put("desc","请求成功");
                data.put("code",1);
                data.put("desc","注册成功");

            }
            else{
                response.put("code",200);
                response.put("desc","请求成功");
                data.put("code",0);
                data.put("desc","注册失败");
            }
            response.put("data",data);
            return response.toJSONString();
        }
        catch (Exception e){
            System.out.println(e);
            response.put("code",500);
            response.put("desc","注册服务错误");
            return response.toJSONString();
        }


    }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(@RequestBody JSONObject accept){

        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        String userId = accept.getString("userId");
        String password = accept.getString("password");
        if(userId.equals("")){
            response.put("code",403);
            response.put("desc","请填写用户名");
            return response.toJSONString();
        }
        if(password.equals("")){
            response.put("code",403);
            response.put("desc","请填写密码");
            return response.toJSONString();
        }
        try{
            User user = adminService.getUserById(userId);
            if(user==null){
                response.put("code",200);
                response.put("desc","请求成功");
                data.put("code",0);
                data.put("desc","登陆失败，无此用户");
                response.put("data",data);
                return response.toJSONString();
            }
            if(user.getPassword().equals(password)){
                response.put("code",200);
                response.put("desc","请求成功");
                data.put("code",1);
                data.put("desc","登陆成功");
                data.put("token",user.getToken());
                data.put("group",user.getUserGroup());
                data.put("userName",user.getName());
                response.put("data",data);
                return response.toJSONString();
            }
            else {
                response.put("code",200);
                response.put("desc","请求成功");
                data.put("code",0);
                data.put("desc","登陆失败，密码错误");
                response.put("data",data);
                return response.toJSONString();
            }
        } catch (Exception e){
            System.out.println(e);
            response.put("code",500);
            response.put("desc","登陆服务错误");
            return response.toJSONString();
        }
    }


    @RequestMapping(value = "getInfo", method = RequestMethod.GET)
    public String getInfo(@RequestParam(value = "userId")String userId,
                          @RequestParam(value = "token")String token){

        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();

        if(userId.equals("")){
            response.put("code",403);
            response.put("desc","请填写用户名");
            return response.toJSONString();
        }
        if(token.equals("")){
            response.put("code",403);
            response.put("desc","请填写token");
            return response.toJSONString();
        }

        try{
            User user = adminService.getUserById(userId);
            if(user==null){
                response.put("code",200);
                response.put("desc","请求成功");
                data.put("code",0);
                data.put("desc","查询失败，无此用户");
                response.put("data",data);
                return response.toJSONString();
            }
            if(user.getToken().equals(token)){
                response.put("code",200);
                response.put("desc","请求成功");
                data.put("code",1);
                data.put("desc","查询成功");
                data.put("userId",user.getUserId());
                data.put("userName",user.getName());
                data.put("gender",user.getGender());
                data.put("phoneNum",user.getPhoneNum());
                data.put("registerTime",user.getRegisterTime());
                response.put("data",data);
                return response.toJSONString();
            }
            else {
                response.put("code",200);
                response.put("desc","请求成功");
                data.put("code",0);
                data.put("desc","查询失败，token错误");
                response.put("data",data);
                return response.toJSONString();
            }
        }catch (Exception e){
            System.out.println(e);
            response.put("code",500);
            response.put("desc","查询服务出错");
            return response.toJSONString();
        }
    }

    @RequestMapping(value = "editInfo", method = RequestMethod.POST)
    public String editInfo(@RequestBody JSONObject accept){

        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        String userId = accept.getString("userId");
        String userName = accept.getString("userName");
        String password = accept.getString("password");
        Integer gender = accept.getInteger("gender");
        Integer group = accept.getInteger("group");
        String phoneNum = accept.getString("phoneNum");
        String token = accept.getString("token");

        try{
            User user = adminService.getUserById(userId);
            if(!user.getToken().equals(token)){
                response.put("code",200);
                response.put("desc","请求成功");
                data.put("code",0);
                data.put("desc","修改失败，token错误");
                response.put("data",data);
                return response.toJSONString();
            }
            if(!userName.equals("")){
                user.setName(userName);
            }
            if(!password.equals("")) {
                user.setPassword(password);
            }
            if(gender!=null){
                user.setGender(gender.toString());
            }
            if(group!=null){
                user.setUserGroup(group.toString());
            }
            if(!phoneNum.equals("")){
                user.setPhoneNum(phoneNum);
            }
            int editResult = adminService.editUser(user);

            if (editResult==999){

                response.put("code",500);
                response.put("desc","修改服务出错");
            }
            else {
                response.put("code",200);
                response.put("desc","请求成功");
                data.put("code",1);
                data.put("desc","查询成功");
                data.put("userId",user.getUserId());
                data.put("userName",user.getName());
                data.put("gender",user.getGender());
                data.put("phoneNum",user.getPhoneNum());
                data.put("registerTime",user.getRegisterTime());
                response.put("data",data);
            }
            return response.toJSONString();
        }catch (Exception e){
            response.put("code",500);
            response.put("desc","修改服务出错");
            System.out.println(e);
            return response.toJSONString();
        }
    }


}
