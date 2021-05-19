package cn.com.self.service.impl;


import cn.com.self.domain.ActUsr;
import cn.com.self.domain.Card;
import cn.com.self.mapper.ActUsrMapper;
import cn.com.self.mapper.CardMapper;
import cn.com.self.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CardServiceImpl implements CardService {

    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private ActUsrMapper actUsrMapper;

    @Override
    @Transactional(readOnly = false)
    public int insertOneCard(Card card){
        int result = 999;
        try{
            result = cardMapper.insert(card);
        }catch (Exception e){
            System.out.println(e);
            return result;
        }
        return result;
    }

    @Override
    public List<Card> getCardByActId(String actId){
        Example example = new Example(Card.class);
        example.createCriteria().andEqualTo("activityId",actId);
        try{
            List<Card> result= cardMapper.selectByExample(example);
            return result;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }

    @Override
    public ActUsr getActUsrById(String userId,String actId){
        Example example = new Example(ActUsr.class);
        example.createCriteria().andEqualTo("userId",userId).andEqualTo("actId",actId);
        try{
            ActUsr result = actUsrMapper.selectOneByExample(example);
            return result;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }


    @Override
    @Transactional(readOnly = false)
    public int editActUsr(ActUsr actUsr){
        int result = 999;
        try{
            result = actUsrMapper.updateByPrimaryKey(actUsr);
            return result;
        }catch (Exception e){
            System.out.println(e);
            return result;
        }
    }


}
