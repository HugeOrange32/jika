package cn.com.self.service.impl;


import cn.com.self.domain.ActUsr;
import cn.com.self.domain.Card;
import cn.com.self.domain.DrawWater;
import cn.com.self.mapper.ActUsrMapper;
import cn.com.self.mapper.CardMapper;
import cn.com.self.mapper.DrawWaterMapper;
import cn.com.self.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CardServiceImpl implements CardService {

    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private ActUsrMapper actUsrMapper;

    @Autowired
    private DrawWaterMapper drawWaterMapper;

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

    @Override
    public int getActUsrByActIdCount(String actId){
        Example example = new Example(ActUsr.class);
        example.createCriteria().andEqualTo("actId",actId);
        try {
            int result = actUsrMapper.selectCountByExample(example);
            return result;
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public int editCard(Card card){
        int result = 999;
        try{
            result = cardMapper.updateByPrimaryKey(card);
            return result;
        }catch (Exception e){
            System.out.println(e);
            return result;
        }
    }


    @Override
    @Transactional(readOnly = false)
    public int insertDrawWater(DrawWater drawWater){
        int result = 999;
        drawWater.setId(UUID.randomUUID().toString().replace("-","").substring(0,16));
        try{
            result = drawWaterMapper.insert(drawWater);
            return result;
        }catch (Exception e){
            System.out.println(e);
            return result;
        }
    }

    @Override
    public List<DrawWater> getDrawWater(String userId,String actId){
        Example example = new Example(DrawWater.class);
        example.createCriteria().andEqualTo("userId",userId).andEqualTo("actId",actId);
        List<DrawWater> result = new ArrayList<DrawWater>();
        try {
            result = drawWaterMapper.selectByExample(example);
            return result;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }


}
