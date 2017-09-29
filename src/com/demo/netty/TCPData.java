package com.demo.netty;

/**
 * Created by Administrator on 2017/9/29.
 */
public class TCPData {

    private int msgType;
    private String content;

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
