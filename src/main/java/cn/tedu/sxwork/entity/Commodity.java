package cn.tedu.sxwork.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Commodity {
    private Integer id;
    private String content;//描述内容
    private String url;//图片路径
    private String nick;//昵称
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private Date created;//发布时间 导包 java.util
    private Integer userId;  //user_id
    private float price;
    private int number;
    private String goodName;
}
