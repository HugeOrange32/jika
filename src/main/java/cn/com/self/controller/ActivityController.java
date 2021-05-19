package cn.com.self.controller;


import cn.com.self.domain.ActUsr;
import cn.com.self.domain.Activity;
import cn.com.self.domain.User;
import cn.com.self.domain.Card;
import cn.com.self.service.ActivityService;
import cn.com.self.service.AdminService;
import cn.com.self.service.CardService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.NewThreadAction;
import tk.mybatis.mapper.entity.Example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.util.UUID;


@RestController
public class ActivityController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CardService cardService;

    @Autowired
    private ActivityService activityService;
    private Object IOException;

    @RequestMapping(value = "adminGetList", method = RequestMethod.GET)
    public String adminGetList(@RequestParam(value = "userId")String userId,
                               @RequestParam(value = "token")String token,
                               @RequestParam(value = "title")String title,
                               @RequestParam(value = "startTime",defaultValue = "2000-01-01 00:00:00") String startTime,
                               @RequestParam(value = "endTime",defaultValue = "2100-12-31 00:00:00")String endTime,
                               @RequestParam(value = "state")Integer state){


        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        List<Activity> result = new ArrayList<Activity>();


        if(userId.equals("")||token.equals("")){
            response.put("code",403);
            response.put("desc","请填写用户信息");
            return response.toJSONString();
        }

        User user = adminService.getUserById(userId);
        if(!token.equals(user.getToken())||user.getUserGroup().equals("0")){
            response.put("code",403);
            response.put("desc","用户信息错误");
            return response.toJSONString();
        }

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTimeDate = simpleDateFormat.parse(startTime);
            Date endTimeDate = simpleDateFormat.parse(endTime);
//            List<String> actList = new ArrayList<String>();


            result = activityService.getActivity(title,startTimeDate,endTimeDate,state);
            response.put("code",200);
            response.put("desc","请求成功");
            data.put("code",1);
            data.put("desc","查询成功");

            List<JSONObject> actList = new ArrayList<JSONObject>();
            result.forEach(item->{
                JSONObject listItem = new JSONObject();
                Integer joinedNum = activityService.getJoinedNum(item.getActId());
                Integer completeNum = activityService.getCompletedNum(item.getActId());
                listItem.put("actId",item.getActId());
                listItem.put("actTitle",item.getTitle());
                listItem.put("createTime",simpleDateFormat.format(item.getCreateTime()));
                listItem.put("startTime",simpleDateFormat.format(item.getStartTime()));
                listItem.put("endTime",simpleDateFormat.format(item.getEndTime()));
                listItem.put("state",Integer.parseInt(item.getStatus()));
                listItem.put("joinedNum",joinedNum);
                listItem.put("completeNum",completeNum);
                actList.add(listItem);
            });
            data.put("actList",actList);
            System.out.println(actList);
            response.put("data",data);

        }catch (Exception e){
            System.out.println(e);
            response.put("code",500);
            response.put("desc","查询活动服务错误");
            return response.toJSONString();
        }
        return response.toJSONString();
    }

    @RequestMapping(value = "upActivity",method = RequestMethod.POST)
    public String upActivity(@RequestBody JSONObject accept){

        JSONObject response = new JSONObject();
        //JSONObject data = new JSONObject();
        List<Activity> upSuccess = new ArrayList<Activity>();
        String userId = accept.getString("userId");
        String token = accept.getString("token");
        List<String> upList = new ArrayList<String>();
        JSONArray upJson = accept.getJSONArray("upList");
        upList = JSONObject.parseArray(upJson.toJSONString(),String.class);

        if(userId.equals("")||token.equals("")){
            response.put("code",403);
            response.put("desc","请填写用户信息");
            return response.toJSONString();
        }

        User user = adminService.getUserById(userId);
        if(!token.equals(user.getToken())||user.getUserGroup().equals("0")){
            response.put("code",403);
            response.put("desc","用户信息错误");
            return response.toJSONString();
        }

        if(upList.size()==0){
            response.put("code",403);
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
            response.put("code",200);
            response.put("desc","上架成功");
            response.put("actList",upSuccess);
        }catch (Exception e){
            System.out.println(e);
            response.put("code",500);
            response.put("desc","查询活动服务错误");
            return response.toJSONString();
        }
        return response.toJSONString();
    }

    @RequestMapping(value = "downActivity",method = RequestMethod.POST)
    public String downActivity(@RequestBody JSONObject accept){

        JSONObject response = new JSONObject();
        //JSONObject data = new JSONObject();
        List<Activity> downSuccess = new ArrayList<Activity>();

        String userId = accept.getString("userId");
        String token = accept.getString("token");
        List<String> downList = new ArrayList<String>();
        JSONArray downJson = accept.getJSONArray("upList");
        downList = JSONObject.parseArray(downJson.toJSONString(),String.class);

        if(userId.equals("")||token.equals("")){
            response.put("code",403);
            response.put("desc","请填写用户信息");
            return response.toJSONString();
        }

        User user = adminService.getUserById(userId);
        if(!token.equals(user.getToken())||user.getUserGroup().equals("0")){
            response.put("code",403);
            response.put("desc","用户信息错误");
            return response.toJSONString();
        }

        if(downList.size()==0){
            response.put("code",403);
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
            response.put("code",200);
            response.put("desc","上架成功");
            response.put("actList",downSuccess);
        }catch (Exception e){
            System.out.println(e);
            response.put("code",500);
            response.put("desc","查询活动服务错误");
            return response.toJSONString();
        }
        return response.toJSONString();
    }

    @RequestMapping(value = "titleCheck", method = RequestMethod.GET)
    public String adminGetList(@RequestParam(value = "actId")String actId,
                               @RequestParam(value = "actTitle")String actTitle) {


        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        List<Activity> result = new ArrayList<Activity>();


        if (actTitle.equals("")) {
            response.put("code", 403);
            response.put("desc", "请填写活动名称");
            return response.toJSONString();
        }
        try {
            result = activityService.getActivityByTitle(actTitle);
            response.put("code", 200);
            response.put("desc", "请求成功");
            if (result.size() == 0) {
                data.put("code", 1);
                data.put("desc", "活动标题未被占用");
            } else {
                if (result.get(0).getActId() == actId) {
                    data.put("code", 1);
                    data.put("desc", "活动标题未被占用");
                } else {
                    data.put("code", 0);
                    data.put("desc", "活动标题已被占用");
                }
            }
            response.put("data", data);
        } catch (Exception e) {
            System.out.println(e);
            response.put("code", 500);
            response.put("desc", "查询活动服务错误");
        }
        return response.toJSONString();

    }

    @RequestMapping(value = "addActivity",method = RequestMethod.POST)
    public String addActivity(@RequestBody JSONObject accept) throws ParseException {

        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();

        String userId = accept.getString("userId");
        String token = accept.getString("token");
        String actTitle = accept.getString("actTitle");
        String startTime = accept.getString("startTime");
        String endTime = accept.getString("endTime");
        Integer type = accept.getInteger(("type"));
        String img = accept.getString("img");
        String rule = accept.getString("rule");
        JSONArray cardList = accept.getJSONArray("cardList");



        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTimeFormat = dateFormat2.parse(startTime);
        Date endTimeFormat = dateFormat2.parse(endTime);
        Date nowTime = new Date(System.currentTimeMillis());

        if(userId.equals("")||token.equals("")){
            response.put("code",403);
            response.put("desc","请填写用户信息");
            return response.toJSONString();
        }
        String actId = UUID.randomUUID().toString().replace("-","").substring(0,15);

        Activity activity = new Activity();
        activity.setActId(actId);
        activity.setTitle(actTitle);
        activity.setCreateTime(nowTime);
        activity.setStartTime(startTimeFormat);
        activity.setEndTime(endTimeFormat);
        activity.setImg(img);
        activity.setRule(rule);
        activity.setTypeNumber(type);
        // 超期
        if(nowTime.compareTo(endTimeFormat)>=0){
            activity.setStatus("2");
        } else {
            activity.setStatus("1");
        }

        try{
            String addActStatus = activityService.addActivity(activity);
            System.out.println(addActStatus);
            if(addActStatus.equals("200")){
                // 插入成功，插入card表
                Card card = new Card();
                for (int i=0;i<cardList.size();i++){
                    String cardLog = UUID.randomUUID().toString().replace("-","").substring(0,15);
                    String cardId = cardList.getJSONObject(i).getString("cardId");
                    Float probability = cardList.getJSONObject(i).getFloat("probability");
                    Card carditem = new Card();
                    carditem.setActivityId(actId);
                    carditem.setImg(cardId);
                    carditem.setProbability(probability);
                    carditem.setCardId(cardLog);
                    Integer cardStatus = cardService.insertOneCard(carditem);
                    if(cardStatus.equals(999)){
                        throw new NullPointerException("添加卡片出错");
                    }
                }
                response.put("code",200);
                response.put("desc","请求成功");
                data.put("code",1);
                data.put("desc","活动新增成功");
            } else{
                response.put("code",200);
                response.put("desc","请求成功");
                data.put("code",0);
                data.put("desc","活动新增失败");
            }
            response.put("data",data);
            return response.toJSONString();
        } catch (Exception e){
            System.out.println(e);
            response.put("code",500);
            response.put("desc","注册服务错误");
            return response.toJSONString();
        }

    }

    @RequestMapping(value = "userJoinAct",method = RequestMethod.POST)
    public String userJoinAct(@RequestBody JSONObject accept) {
        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        String userId = accept.getString("userId");
        String token = accept.getString("token");
        String actId = accept.getString("actId");
        User user = adminService.getUserById(userId);
        if (!token.equals(user.getToken()) || user.getUserGroup().equals("1")) {
            response.put("code", 403);
            response.put("desc", "用户信息错误");
            return response.toJSONString();
        }

        try {
            ActUsr actUsr = new ActUsr();
            actUsr.setActId(actId);
            actUsr.setUserId(userId);
            actUsr.setFirstShare("1");
            actUsr.setCardDrawNums(3);
            actUsr.setCardnum1(0);
            actUsr.setCardnum2(0);
            actUsr.setCardnum3(0);
            actUsr.setCardnum4(0);
            actUsr.setCardnum5(0);
            int resultCode = activityService.joinActivity(actUsr);
            if (resultCode == 999) {
                throw new Exception("插入参加活动表失败");
            } else {
                response.put("code", 200);
                response.put("desc", "请求成功");
                data.put("code", 1);
                data.put("desc", "参与活动成功");
                response.put("data", data);
                return response.toJSONString();
            }
        } catch (Exception e) {
            System.out.println(e);
            response.put("code", 500);
            response.put("desc", "服务器错误");
            return response.toJSONString();
        }

    }

    @RequestMapping(value = "adminGetDetail", method = RequestMethod.GET)
    public String adminGetDetail(@RequestParam(value = "actId")String actId,
                                 @RequestParam(value = "token")String token,
                                 @RequestParam(value = "userId")String userId) {


        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        Activity result = new Activity();
        List<Card> cardRes = new ArrayList<Card>();
        List<JSONObject> cardList = new ArrayList<JSONObject>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        if (token.equals("")||userId.equals("")) {
            response.put("code", 400);
            response.put("desc", "请填写正确的用户信息");
            return response.toJSONString();
        }
        if (actId.equals("")) {
            response.put("code", 403);
            response.put("desc", "请传入活动ID");
            return response.toJSONString();
        }
        try {
            result = activityService.getActivityById(actId);
            response.put("code", 200);
            response.put("desc", "请求成功");
            if (result != null) {
                data.put("code", 1);
                data.put("desc", "查询成功");
                data.put("actTitle",result.getTitle());
                data.put("actPic",result.getImg());
                data.put("startTime",simpleDateFormat.format(result.getStartTime()));
                data.put("endTime",simpleDateFormat.format(result.getStartTime()));
                data.put("type",result.getTypeNumber());
                data.put("rule",result.getRule());
                cardRes = cardService.getCardByActId(actId);
                for(int i=0;i<cardRes.size();i++){
                    JSONObject cardItem = new JSONObject();
                    cardItem.put("id",cardRes.get(i).getImg());
                    cardItem.put("probability",cardRes.get(i).getProbability());
                    cardList.add(cardItem);
                }
                data.put("cardList",cardList);
            } else {

                    data.put("code", 0);
                    data.put("desc", "查询失败");
            }
            response.put("data", data);
        } catch (Exception e) {
            System.out.println(e);
            response.put("code", 500);
            response.put("desc", "查询活动服务错误");
        }
        return response.toJSONString();

    }

    @RequestMapping(value = "editActivity",method = RequestMethod.POST)
    public String editActivity(@RequestBody JSONObject accept) throws ParseException {

        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();

        String userId = accept.getString("userId");
        String token = accept.getString("token");
        String actTitle = accept.getString("actTitle");
        String startTime = accept.getString("startTime");
        String endTime = accept.getString("endTime");
        Integer type = accept.getInteger(("type"));
        String img = accept.getString("img");
        String rule = accept.getString("rule");
        JSONArray cardList = accept.getJSONArray("cardList");
        String actId = accept.getString("actId");



        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTimeFormat = dateFormat2.parse(startTime);
        Date endTimeFormat = dateFormat2.parse(endTime);
        Date nowTime = new Date(System.currentTimeMillis());

        if(userId.equals("")||token.equals("")){
            response.put("code",403);
            response.put("desc","请填写用户信息");
            return response.toJSONString();
        }

        Activity activity = activityService.getActivityById(actId);
        if(!actTitle.equals("")){
            activity.setTitle(actTitle);
        }
        if(!startTime.equals("")){
            activity.setStartTime(startTimeFormat);
        }
        if(!endTime.equals("")){
            activity.setEndTime(endTimeFormat);
            // 超期
            if(nowTime.compareTo(endTimeFormat)>=0){
                activity.setStatus("2");
            } else {
                activity.setStatus("1");
            }
        }
        if(!rule.equals("")){
            activity.setRule(rule);
        }
        int joinedNum = cardService.getActUsrByActIdCount(actId);
        if(joinedNum == 0){
            if(!img.equals("")){
                activity.setImg(img);
            }
            if(type>=0){
                activity.setTypeNumber(type);
            }
        }

        try{
            Integer editRes = activityService.editActivity(activity);
            System.out.println(editRes);
            if(editRes!= 999){
                // 修改成功
                List<Card> cardData = cardService.getCardByActId(actId);
                for (int i=0;i<cardList.size();i++){
                    Float probability = cardList.getJSONObject(i).getFloat("probability");
                    String cardId = cardList.getJSONObject(i).getString("cardId");
                    Card carditem = cardData.get(i);
                    carditem.setImg(cardId);
                    carditem.setProbability(probability);
                    Integer cardStatus = cardService.editCard(carditem);
                    if(cardStatus == 999){
                        throw new NullPointerException("修改卡片出错");
                    }
                }
                response.put("code",200);
                response.put("desc","请求成功");
                data.put("code",1);
                data.put("desc","修改活动成功");
            } else{
                response.put("code",200);
                response.put("desc","请求成功");
                data.put("code",0);
                data.put("desc","修改活动失败");
            }
            response.put("data",data);
            return response.toJSONString();
        } catch (Exception e){
            System.out.println(e);
            response.put("code",500);
            response.put("desc","服务错误");
            return response.toJSONString();
        }

    }

    @RequestMapping(value = "userGetList", method = RequestMethod.GET)
    public String userGetList(@RequestParam(value = "userId")String userId,
                               @RequestParam(value = "token")String token ){


        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        List<Activity> result = new ArrayList<Activity>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        if(userId.equals("")||token.equals("")){
            response.put("code",403);
            response.put("desc","请填写用户信息");
            return response.toJSONString();
        }

        User user = adminService.getUserById(userId);

        try {
            result = activityService.getActivityByState(1);
            result.addAll(activityService.getActivityByState(2));
            response.put("code",200);
            response.put("desc","请求成功");
            data.put("code",1);
            data.put("desc","查询成功");

            List<JSONObject> actList = new ArrayList<JSONObject>();
            result.forEach(item->{
                ActUsr actUsr = cardService.getActUsrById(userId,item.getActId());
                JSONObject listItem = new JSONObject();
                listItem.put("actId",item.getActId());
                listItem.put("actTitle",item.getTitle());
                listItem.put("startTime",simpleDateFormat.format(item.getStartTime()));
                listItem.put("endTime",simpleDateFormat.format(item.getEndTime()));
                listItem.put("state",Integer.parseInt(item.getStatus()));
                listItem.put("img",item.getImg());
                if(actUsr == null){
                    listItem.put("have",0);
                }else{
                    listItem.put("have",1);
                }
                actList.add(listItem);
            });
            data.put("actList",actList);
            System.out.println(actList);
            response.put("data",data);

        }catch (Exception e){
            System.out.println(e);
            response.put("code",500);
            response.put("desc","查询活动服务错误");
            return response.toJSONString();
        }
        return response.toJSONString();
    }
}
