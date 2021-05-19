package cn.com.self.controller;


import cn.com.self.domain.ActUsr;
import cn.com.self.domain.Card;
import cn.com.self.domain.User;
import cn.com.self.service.AdminService;
import cn.com.self.service.CardService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DrawController {


    @Autowired
    private AdminService adminService;

    @Autowired
    private CardService cardService;


    @RequestMapping(value = "userDrawCard",method = RequestMethod.POST)
    public String userDrawCard(@RequestBody JSONObject accept){
        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();

        String userId = accept.getString("userId");
        String token = accept.getString("token");
        String actId = accept.getString("actId");

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

        try{
            ActUsr actUsr = cardService.getActUsrById(userId,actId);
            if(actUsr==null){
                throw new Exception("找不到actusr");
            }
            List<Card> cardList = new ArrayList<Card>();
            cardList = cardService.getCardByActId(actId);
            if(cardList==null||cardList.size()!=5){
                throw new Exception("找不到card");
            }
            float probabilityAll = 0;
            for(Card card:cardList){
                probabilityAll = probabilityAll+card.getProbability();
            }
            if(probabilityAll!=1.0){
                throw new Exception("概率设置错误");
            }
            Double drawRandom =Math.random();
            Integer cardType = 9;
            for(int i=0;i<cardList.size();i++){
                drawRandom = drawRandom-(double)cardList.get(i).getProbability();
                if(drawRandom<0){
                    cardType = cardList.get(i).getCardType();
                    break;
                }
            }
            switch (cardType){
                case 1:
                    actUsr.setCardnum1(actUsr.getCardnum1()+1);
                    break;
                case 2:
                    actUsr.setCardnum2(actUsr.getCardnum2()+1);
                    break;
                case 3:
                    actUsr.setCardnum3(actUsr.getCardnum3()+1);
                    break;
                case 4:
                    actUsr.setCardnum4(actUsr.getCardnum4()+1);
                    break;
                case  5:
                    actUsr.setCardnum5(actUsr.getCardnum5()+1);
                    break;
            }

            int editActUsrResult = cardService.editActUsr(actUsr);
            if(editActUsrResult==999){
                throw new Exception("修改actUst失败");
            }

        }catch (Exception e){
            System.out.println(e);
        }







        return response.toJSONString();
    }


}
