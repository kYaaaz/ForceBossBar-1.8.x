package me.force.bossbar.command;

import me.force.bossbar.check.IsInteger;
import me.force.bossbar.util.Lang;
import me.force.bossbar.util.SendBar;
import me.force.bossbar.util.SendType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BossBar {
    final CommandSender sender;
    final String[] args;
    public BossBar(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }


    public void execute() {
        List<Player> list = new ArrayList<>();

        if (!sender.hasPermission("bossbar.usage")) {
            Lang.PERMISSION.send(sender);
            return;
        }

        if (args.length < 3) {
            Lang.USAGE.send(sender);
            return;
        }

        // /bossbar <send, sendall> [<player>] <interval> <message>
        SendType sendType = SendType.FIND.findType(args[0]);
        if (sendType == SendType.NULL) {
            Lang.USAGE.send(sender);
            return;
        }


        if (sendType.equals(SendType.SEND)) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                Lang.TARGETNULL.send(sender);
                return;
            }
            list.add(target);
        } else {
            list.addAll(Bukkit.getOnlinePlayers());
        }




        int interval;
        if (sendType.equals(SendType.SEND)) {
            IsInteger isInteger = new IsInteger(args[2]);
            if (isInteger.check()) {
                Lang.USAGE.send(sender);
                return;
            }
            interval = Integer.parseInt(args[2]);
        } else {
            IsInteger isInteger = new IsInteger(args[1]);
            if (isInteger.check()) {
                Lang.USAGE.send(sender);
                return;
            }
            interval = Integer.parseInt(args[1]);
        }

        List<String> startMessage = new LinkedList<>(Arrays.asList(args));
        startMessage.remove(0);
        startMessage.remove(0);
        if (sendType.equals(SendType.SEND)) {
            startMessage.remove(0);
        }

        StringBuilder finishMessage = new StringBuilder();
        for (String var : startMessage) {
            finishMessage.append(var).append(" ");
        }


        new SendBar(list, interval, ChatColor.translateAlternateColorCodes('&', finishMessage.toString())).send();
    }


}
