package me.force.bossbar;

import me.force.bossbar.check.Version;
import me.force.bossbar.command.Handler;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Main extends JavaPlugin {

    public void onEnable() {
        if (!version()) {
            getLogger().severe("This plugin created for 1.8.x versions.");
            getLogger().severe("The options will not work!");
            return;
        }

        getCommand("bossBar").setExecutor(new Handler());
    }

    private boolean version() {
        return new Version(this).checkVersion();
    }

}
