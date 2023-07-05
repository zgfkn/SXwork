package cn.tedu.sxwork.controller;

import cn.tedu.sxwork.entity.ShoppingCart;
import cn.tedu.sxwork.entity.User;
import cn.tedu.sxwork.mapper.ShoppingCartMapper;
import cn.tedu.sxwork.mapper.CommodityMapper;
import cn.tedu.sxwork.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ShoppingCartController {
    @Autowired(required = false)
    ShoppingCartMapper mapper;
    @Autowired(required = false)
    CommodityMapper commodityMapper;
    @Autowired(required = false)
    UserMapper UserMapper;

    //添加购物车
    @RequestMapping("/insertShoppingCart")
    public int insert(@RequestBody ShoppingCart shoppingCart, HttpSession session){
        //得到当前登录的用户对象
        User u = (User) session.getAttribute("u");
        System.out.println("shoppingCart = " + shoppingCart);
        //, nick 发布时间,user_id
        //把当前登录用户的id添加给shoppingCart对象
        shoppingCart.setUserId(u.getId());
        shoppingCart.setUrl(commodityMapper.selectById(shoppingCart.getCommodityId()).getUrl());
        System.out.println("上传之后的shoppingCart为:"+shoppingCart);
        mapper.insert(shoppingCart);
        return 1;//代表发布树成功
    }

    //通过用户id查询用户的购物车
    @RequestMapping("/selectShoppingCart")
    public List<ShoppingCart> selectShoppingCart(HttpSession session){
        User u = (User) session.getAttribute("u");
        if (u==null){
            return null;
        }
        System.out.println(mapper.selectByUserId(u.getId()));
        //通过当前登录用户的id查询相关的购物车信息
        return mapper.selectByUserId(u.getId());
    }
    //删除购物车中的物品
    @RequestMapping("/deleteShoppingCart")
    public void delete(@RequestBody int id){
        mapper.deleteById(id);
    }
    //结算
    //结算
    //结算准备
    @RequestMapping("/clearing/ready")
    public float ready(HttpSession session){
        User u = (User) session.getAttribute("u");
        List<ShoppingCart> arr=mapper.selectByUserId(u.getId());
        float all=0;
        for (ShoppingCart shoppingCart: arr) {//总共钱
            float money = 0;
            money = shoppingCart.getPrice() * shoppingCart.getNumber();
            all += money;
            int b = commodityMapper.selectById(shoppingCart.getCommodityId()).getNumber()-shoppingCart.getNumber();
            if (b<0){
                return -2;//商品数量不够
            }
            if (all> u.getPurse()) return -1;//钱包不够
        }
        return all;
    }
    //结算开始
    @RequestMapping("/clearing/start")
    public float start(HttpSession session){
        User u = (User) session.getAttribute("u");
        List<ShoppingCart> arr=mapper.selectByUserId(u.getId());
        float all=0;
        for (ShoppingCart shoppingCart: arr){
            //结算
            float money=0;
            money=shoppingCart.getPrice()*shoppingCart.getNumber();
            all+=money;
            //更新商品数量
            int b = commodityMapper.selectById(shoppingCart.getCommodityId()).getNumber()-shoppingCart.getNumber();
            commodityMapper.updateGoodNum(b,shoppingCart.getCommodityId());
            //卖家所得
            User user2 = UserMapper.selectById(commodityMapper.selectById(shoppingCart.getCommodityId()).getUserId());
            float purse=money+ user2.getPurse();
            UserMapper.trade(purse,user2.getId());
        }
        //支付
        float purse = u.getPurse() - all;
        UserMapper.trade(purse,u.getId());
        User user = UserMapper.selectByUsername(u.getUsername());
        session.removeAttribute("u");
        session.setAttribute("u", user);
        //删除购物车
        mapper.deleteByUserId(u.getId());
        commodityMapper.deleteByZone();
        return all;
    }
}
