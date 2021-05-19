package cn.com.self.service.impl;

import cn.com.self.domain.TbSysUser;
import cn.com.self.domain.User;
import cn.com.self.mapper.TbSysUserMapper;
import cn.com.self.mapper.UserMapper;
import cn.com.self.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

@Service
@Transactional(readOnly = true)  //数据库只读
public class AdminServiceImpl implements AdminService {

    @Autowired
    private TbSysUserMapper tbSysUserMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String checkUser(String userId){
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("userId",userId);

        try{
            User user = userMapper.selectOneByExample(example);
            if(user==null){
                System.out.println("check:200");
                return "200";
            }
            else {
                return "403";
            }
        }
        catch (Exception e){
            System.out.println(e);
            return "500";
        }


    }

    @Override
    @Transactional(readOnly = false)  //需要插入数据，取消只读
    public String register(User user) {
        //user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));  //对密码进行MD5加密
        try{
            userMapper.insert(user);
            return "200";
        }catch (Exception e){
            System.out.println(e);
            return "403";
        }
    }

    @Override
    public User getUserById(String userId){
        try{
            User user = userMapper.selectByPrimaryKey(userId);
            return user;
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public int editUser(User user){
        try{
            int result = userMapper.updateByPrimaryKey(user);
            return result;
        }catch (Exception e){
            System.out.println(e);
            return 999;
        }
    }



    /*@Override
    public TbSysUser login(String loginCode, String password) {
        Example example = new Example(TbSysUser.class);
        example.createCriteria().andEqualTo("loginCode", loginCode);


        TbSysUser tbSysUser = tbSysUserMapper.selectOneByExample(example);

        if(tbSysUser==null){
            return null;
        }

        String checkPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        if(checkPassword.equals(tbSysUser.getPassword())){  //检查密码是否正确
            return tbSysUser;
        }
        return null;
    }*/
}
