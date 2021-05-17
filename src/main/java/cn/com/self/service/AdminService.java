package cn.com.self.service;

import cn.com.self.domain.TbSysUser;
import cn.com.self.mapper.UserMapper;
import cn.com.self.domain.User;

public interface AdminService {


    /**
     * 检查用户名是否唯一
     * @param userId
     */
    public String checkUser(String userId);


    /**
     * 注册
     * @param user
     */
    public String register(User user);


    /**
     * 获取用户信息
     * @param userId
     */
    public User getUserById(String userId);


    /**
     * 获取用户信息
     * @param user
     */
    public int editUser(User user);

    /**
     * 登录
     * @param account
     * @param password
     */
   /* public TbSysUser login(String account, String password);*/
}
