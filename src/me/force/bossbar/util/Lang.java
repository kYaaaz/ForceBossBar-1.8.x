package me.force.bossbar.util;

import org.bukkit.command.CommandSender;

public enum Lang {

    USAGE("§e/bossBar <send, sendall> [<player>] <interval> <message>"),
    PERMISSION("§cPermission denied"),
    TARGETNULL("§cTarget not found")


    ;

    private final String var;
    Lang(String var) {
        this.var = var;
    }
    public void send(CommandSender sender) {
        sender.sendMessage("§3< BossBar > §7" + var);
    }

}
