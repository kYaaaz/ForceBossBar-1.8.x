package me.force.bossbar.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Handler implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        new BossBar(sender, args).execute();
        return true;
    }
}
