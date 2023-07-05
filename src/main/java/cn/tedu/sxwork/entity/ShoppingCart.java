package cn.tedu.sxwork.entity;

import lombok.Data;

@Data
public class ShoppingCart {
    private Integer id;
    private Integer commodityId;//tree_id
    private String goodName;;//商品名
    private Integer userId;  //user_id
    private float price;//单价
    private int number;//数量
    private String url;
}
