package cn.com.self.controller;


import cn.com.self.service.HelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelpController {

    @Autowired
    private HelpService helpService;


}
