package cn.com.self.service.impl;

import cn.com.self.domain.HelpWater;
import cn.com.self.mapper.HelpMapper;
import cn.com.self.mapper.HelpWaterMapper;
import cn.com.self.service.HelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class HelpServiceImpl implements HelpService {

    @Autowired
    private HelpMapper helpMapper;

    @Autowired
    private HelpWaterMapper helpWaterMapper;

    public List<HelpWater> getHelpWater(String userId,String actId){
        List<HelpWater> result = new ArrayList<HelpWater>();
        Example example = new Example(HelpWater.class);
        example.createCriteria().andEqualTo("userId",userId).andEqualTo("actId",actId);
        try{
            result = helpWaterMapper.selectByExample(example);
            return result;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}
