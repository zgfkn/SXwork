package cn.tedu.sxwork.mapper;

import cn.tedu.sxwork.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
@Mapper
public interface ShoppingCartMapper {
    //添加购物车
    @Insert("insert into shoppingcart values(null,#{commodityIdId},#{goodName},#{userId},#{price},#{number},#{url})")
    void insert(ShoppingCart shoppingCart);

    //查询购物车根据用户id
    @Select("select * from shoppingcart where userid=#{id}")
    List<ShoppingCart> selectByUserId(int id);

    //删除购物车根据id
    @Delete("delete from shoppingcart where id=#{id}")
    void deleteById(int id);

    //删除购物车根据用户id
    @Delete("delete from shoppingcart where userid=#{id}")
    void deleteByUserId(int id);
}
