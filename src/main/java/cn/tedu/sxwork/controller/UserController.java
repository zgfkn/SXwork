package cn.tedu.sxwork.controller;


import cn.tedu.sxwork.entity.User;
import cn.tedu.sxwork.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired(required = false)
    UserMapper mapper;

    //注册
    @RequestMapping("/reg")
    public int reg(@RequestBody User user) {
        User u = mapper.selectByUsername(user.getUsername());
        if (u != null) {
            return 1;//用户存在
        } else {
            mapper.insertUser(user);
            return 2;//用户不存在
        }
    }

    @RequestMapping("/selectUserByName")
    public User selectUserByName(@RequestBody User user) {
        User user1 = mapper.selectByUsername(user.getUsername());
        return user1;
    }

    @RequestMapping("/deleteUserByName")
    public void deleteUserByName(@RequestBody User user) {
        mapper.deleteByUsername(user.getUsername());
    }

    @RequestMapping("/allUser")
    public List<User> allUser() {
        List<User> userList=mapper.selectAllUser();
        return userList;
    }

    //登录
    @RequestMapping("/login")
    public int login(@RequestBody User user, HttpSession session) {
        User u = mapper.selectByUsername(user.getUsername());
        if (u==null){//如果查询为空则证明用户不存在
            return 2;
        }
        if (u.getPassword().equals(user.getPassword())) {//存在且相等添加用户
            //登录成功后把从数据库里面查询到的用户对象保存到Session会话对象中
            session.setAttribute("u", u);
            return 1;
        } else {//否则不成功
            return 2;
        }
    }

    @RequestMapping("/currentUser")
    public User currentUser(HttpSession session) {
        //返回已登录的用户信息
        User u = (User)session.getAttribute("u");
        return u;
    }

    @RequestMapping("/logout")
    public void logout(HttpSession session) {
        //删除登录成功时保存的用户对象
        session.removeAttribute("u");
    }
    //充值
    @RequestMapping("/recharge")
    public float recharge(float money,HttpSession session) {
        User u = (User)session.getAttribute("u");
        User user = mapper.selectByUsername(u.getUsername());
        float purse =money+ user.getPurse();
        mapper.trade(purse,user.getId());
        User user1 = mapper.selectByUsername(u.getUsername());
        session.removeAttribute("u");
        session.setAttribute("u", user1);
        return money;
    }
    //支付
    @RequestMapping("/pay")
    public float pay(float money,HttpSession session) {
        User u = (User)session.getAttribute("u");
        User user = mapper.selectByUsername(u.getUsername());
        float purse = user.getPurse() - money;
        mapper.trade(purse,user.getId());
        User user2 = mapper.selectByUsername(u.getUsername());
        session.removeAttribute("u");
        session.setAttribute("u", user2);
        return money;
    }
}
