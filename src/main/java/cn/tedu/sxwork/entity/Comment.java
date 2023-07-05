package cn.tedu.sxwork.entity;

import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private String content;//内容
    private String nick;//昵称
    private Integer commodityId;//Tree的id
    private String goodName;
}