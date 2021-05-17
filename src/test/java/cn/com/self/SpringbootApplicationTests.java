package cn.com.self;

import cn.com.self.domain.TbSysUser;
import cn.com.self.domain.User;
import cn.com.self.service.AdminService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
@Transactional
@Rollback
public class SpringbootApplicationTests {

    @Autowired
    private AdminService adminService;

    @Test
    public void register() {
        /*TbSysUser tbSysUser = new TbSysUser();
        tbSysUser.setUserCode(UUID.randomUUID().toString());
        tbSysUser.setLoginCode("admin@test.com");
        tbSysUser.setPassword("123456");
        tbSysUser.setUserName("admin");
        tbSysUser.setUserType("0");
        tbSysUser.setMgrType("1");
        tbSysUser.setStatus("0");
        tbSysUser.setCreateDate(new Date());
        tbSysUser.setCreateBy(tbSysUser.getUserCode());
        tbSysUser.setUpdateDate(new Date());
        tbSysUser.setUpdateBy(tbSysUser.getUserCode());
        tbSysUser.setCorpCode("0");
        tbSysUser.setCorpName("Admin");*/
        User user = new User();
        user.setUserId("qwererty");
        user.setPassword("123456");
        user.setName("帅哥");
        user.setGender("0");
        user.setPhoneNum("1111111111111");
        user.setLoginFlag("0");
        user.setRegisterTime(new Date(System.currentTimeMillis()));
        user.setUserGroup("0");
        user.setToken(UUID.randomUUID().toString().replace("-",""));

        System.out.println(adminService.register(user));
    }

    /*@Test
    public void login() {
        TbSysUser tbSysUser = adminService.login("admin@test.com", "123456");
        Assert.assertNotNull(tbSysUser);
    }*/

}
