package cn.com.self.controller;


import cn.com.self.domain.ActUsr;
import cn.com.self.domain.Help;
import cn.com.self.domain.HelpWater;
import cn.com.self.domain.User;
import cn.com.self.service.AdminService;
import cn.com.self.service.CardService;
import cn.com.self.service.HelpService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelpController {


    @Autowired
    private AdminService adminService;

    @Autowired
    private HelpService helpService;

    @Autowired
    private CardService cardService;



    @RequestMapping(value = "sendShare",method = RequestMethod.POST)
    public String sendShare(@RequestBody JSONObject accept){
        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        String userId = accept.getString("userId");
        String token = accept.getString("token");
        String actId = accept.getString("actId");
        try{
            ActUsr actUsr = cardService.getActUsrById(userId,actId);
            if(actUsr.getFirstShare().equals("0")){
                response.put("code",200);
                response.put("desc","请求成功");
                data.put("code",0);
                data.put("desc","不是首次分享");
                data.put("drawTimes",actUsr.getCardDrawNums());
                response.put("data",data);
                return response.toJSONString();
            }
            else {
                actUsr.setFirstShare("0");
                actUsr.setCardDrawNums(actUsr.getCardDrawNums()+3);
                response.put("code",200);
                response.put("desc","请求成功");
                data.put("code",1);
                data.put("desc","首次分享成功");
                data.put("drawTimes",actUsr.getCardDrawNums());
                response.put("data",data);
                return response.toJSONString();
            }
        }
        catch (Exception e){
            System.out.println(e);
            response.put("code",500);
            response.put("desc","服务器错误");
            return response.toJSONString();
        }
    }


    @RequestMapping(value = "userHelpLog",method = RequestMethod.GET)
    public String userHelpLog(@RequestParam(value = "userId")String userId,
                              @RequestParam(value = "token")String token,
                              @RequestParam(value = "actId")String actId){
        JSONObject response = new JSONObject();
        List<HelpWater> result = new ArrayList<HelpWater>();

        if(userId.equals("")||token.equals("")){
            response.put("code",403);
            response.put("desc","请填写用户信息");
            return response.toJSONString();
        }

        User user = adminService.getUserById(userId);
        if(!token.equals(user.getToken())||user.getUserGroup().equals("1")){
            response.put("code",403);
            response.put("desc","用户信息错误");
            return response.toJSONString();
        }

        try {
            result = helpService.getHelpWater(userId,actId);
            response.put("code",200);
            response.put("desc","请求成功");
            response.put("helpLog",result);
            return response.toJSONString();
        }catch (Exception e){
            System.out.println(e);
            response.put("code",500);
            response.put("desc","服务器错误");
            return response.toJSONString();
        }

    }

}
