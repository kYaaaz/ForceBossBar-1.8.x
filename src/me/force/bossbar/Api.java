package me.force.bossbar;

import me.force.bossbar.util.SendBar;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;

@SuppressWarnings("unused")
public class Api {
    public void sendBar(@Nonnull List<Player> list, int interval, @Nonnull String message) {
        new SendBar(list, interval, message).send();
    }
}
