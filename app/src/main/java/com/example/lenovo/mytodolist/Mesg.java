package com.example.lenovo.mytodolist;

/**
 * Created by lenovo on 2018/3/18.
 *消息类
 */

public class Mesg {
    //定义收到和发送的类别号
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;

    private String content;//消息内容
    private int type;//消息类别

    public Mesg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}