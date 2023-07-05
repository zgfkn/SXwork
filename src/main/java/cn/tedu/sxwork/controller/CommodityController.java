package cn.tedu.sxwork.controller;

import cn.tedu.sxwork.entity.User;
import cn.tedu.sxwork.mapper.CommodityMapper;
import cn.tedu.sxwork.entity.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
public class CommodityController {
    @Autowired(required = false)
    CommodityMapper mapper;

    //添加树
    @RequestMapping("/insert")
    public int insert(@RequestBody Commodity commodity, HttpSession session){
        //得到当前登录的用户对象
        User u = (User) session.getAttribute("u");
        if (u==null){//代表未登录
            return 2;
        }
        System.out.println("commodity = " + commodity);
        //, nick 发布时间,user_id
        //把当前登录用户的昵称和id添加给tree对象
        commodity.setNick(u.getNick());
        commodity.setUserId(u.getId());
        //设置树发布时间为当前系统时间 new Date()得到当前系统时间
        commodity.setCreated(new Date());
        System.out.println("上传之后的commodity为:"+commodity);
        mapper.insert(commodity);
        return 1;//代表发布树成功
    }

    //查询所有树
    @RequestMapping("/select")
    public List<Commodity> select(){
        return mapper.select();
    }
    //通过id查询一个树
    @RequestMapping("/selectById")
    public Commodity selectById(int id){
        return mapper.selectById(id);
    }

    //通过用户id查询用户的树
    @RequestMapping("/selectSelf")
    public List<Commodity> selectSelf(HttpSession session){
        User u = (User) session.getAttribute("u");
        if (u==null){
            return null;
        }
        //通过当前登录用户的id查询相关的树信息
        return mapper.selectByUserId(u.getId());
    }

    @RequestMapping("/delete")
    public void delete(@RequestBody Integer id){
        mapper.deleteById(id);
    }
}
