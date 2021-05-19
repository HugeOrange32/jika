package cn.com.self.service;

import cn.com.self.domain.HelpWater;

import java.util.List;

public interface HelpService {

    public List<HelpWater> getHelpWater(String userId,String actId);
}
