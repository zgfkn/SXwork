package cn.tedu.sxwork.controller;

import cn.tedu.sxwork.entity.Administrator;
import cn.tedu.sxwork.mapper.AdministratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class AdministratorController {
    @Autowired(required = false)
    AdministratorMapper administratorMapper;
    //注册
    @RequestMapping("/regAdministrator")
    public int regAdministrator(@RequestBody Administrator administrator) {
        Administrator administrator1 = administratorMapper.selectByName(administrator.getName());
        if (administrator1 != null) {
            return 1;//用户存在
        } else {
            administratorMapper.insertAdministrator(administrator);
            return 2;//用户不存在
        }
    }

    //登录
    @RequestMapping("/loginAdministrator")
    public int loginAdministrator(@RequestBody Administrator administrator, HttpSession session) {
        Administrator administrator1 = administratorMapper.selectByName(administrator.getName());
        if (administrator1==null){//如果查询为空则证明用户不存在
            return 2;
        }
        if (administrator1.getPassword().equals(administrator.getPassword())) {//存在且相等添加用户
            //登录成功后把从数据库里面查询到的用户对象保存到Session会话对象中
            session.setAttribute("administrator", administrator1);
            return 1;
        } else {//否则不成功
            return 2;
        }
    }
}
