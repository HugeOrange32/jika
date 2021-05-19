package cn.com.self.service.impl;

import cn.com.self.mapper.HelpMapper;
import cn.com.self.service.HelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class HelpServiceImpl implements HelpService {

    @Autowired
    private HelpMapper helpMapper;
}
