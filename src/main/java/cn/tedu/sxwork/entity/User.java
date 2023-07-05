package cn.tedu.sxwork.entity;


import lombok.Data;

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String nick;
    private float purse;//钱包
    private String signature;
    private String address;
}
