package cn.tedu.sxwork.mapper;


import cn.tedu.sxwork.entity.Commodity;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface CommodityMapper {
    //添加树洞
    @Insert("insert into commodity values(null,#{content},#{url}," +
            "#{nick},#{created},#{userId},#{price},#{number},#{goodName})")
    void insert(Commodity commodity);

    //查询所有树洞列表以时间降序
    @Select("select * from commodity order by created desc")
    List<Commodity> select();

    //根据id查询树洞
    @Select("select * from commodity where id=#{id}")
    Commodity selectById(int id);

    //查询树洞根据用户id
    @Select("select * from commodity where userid=#{id}")
    List<Commodity> selectByUserId(int id);

    //删除树洞根据id
    @Delete("delete from commodity where id=#{id}")
    void deleteById(Integer id);

    @Update("update commodity set number=#{i} where id=#{id}")
    void updateGoodNum(Integer i,Integer id);

    //删除所有数量为或小零的商品
    @Delete("delete from commodity where number<=0")
    void deleteByZone();

}

