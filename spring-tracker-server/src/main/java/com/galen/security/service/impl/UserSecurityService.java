package com.galen.security.service.impl;

import com.galen.security.mapper.UserSecurityMapper;
import com.galen.security.pojo.SecuritySysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Galen
 * @Date: 2019/4/3-17:19
 * @Description: security权限管理框架
 **/
@Service
@Transactional
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserSecurityMapper userSecurityMapper;

    /**
     * @Author: Galen
     * @Description: 实现了UserDetailsService接口中的loadUserByUsername方法
     * 执行登录,构建Authentication对象必须的信息,
     * 如果用户不存在，则抛出UsernameNotFoundException异常
     * @Date: 2019/3/27-16:04
     * @Param: [s]
     * @return: org.springframework.security.core.userdetails.UserDetails
     **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * @Author: Galen
         * @Description: 查询数据库，获取登录的用户信息
         **/
        SecuritySysUser securityUser = userSecurityMapper.loadUserByUsername(username);
        if (securityUser == null) {
            throw new UsernameNotFoundException("用户名不对");
        }
        return securityUser;
    }
}
