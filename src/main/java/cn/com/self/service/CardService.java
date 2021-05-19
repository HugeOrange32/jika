package cn.com.self.service;

import cn.com.self.domain.ActUsr;
import cn.com.self.domain.Card;
import cn.com.self.domain.DrawWater;

import java.util.List;

public interface CardService {

    public int insertOneCard(Card card);


    public List<Card> getCardByActId(String actId);


    public ActUsr getActUsrById(String userId,String actId);


    public int editActUsr(ActUsr actUsr);


    public int insertDrawWater(DrawWater drawWater);

}
