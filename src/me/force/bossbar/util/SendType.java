package me.force.bossbar.util;

public enum SendType {

    SEND, SENDALL, NULL, FIND;

    SendType() {}
    public SendType findType(String var) {
        if (var.equalsIgnoreCase("send")) {
            return SEND;
        } else if (var.equalsIgnoreCase("sendall")) {
            return SENDALL;
        }
        return NULL;
    }

}
