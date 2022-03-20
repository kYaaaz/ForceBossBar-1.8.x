package me.force.bossbar.util;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("BusyWait")
public class SendBar {

    private final List<Player> list;
    private int interval;
    private final String message;

    private boolean completed = false;

    public SendBar(List<Player> list, int interval, String message) {
        this.list = list;
        this.interval = interval;
        this.message = message;
    }


    public void send() {
        countdown();
        execute();
    }

    private final Map<UUID, EntityWither> witherMap = new HashMap<>();
    private void execute() {
        create();


        Thread thread = new Thread(() -> {
            try {
                while (!completed) {
                    for (UUID uuid : witherMap.keySet()) {
                        Player player = Bukkit.getPlayer(uuid);
                        if (player == null) {
                            witherMap.remove(uuid);
                            continue;
                        }

                        EntityWither wither = witherMap.get(uuid);
                        PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(wither.getId());
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(destroy);




                        new Thread(() -> {
                            try {
                                //y -10
                                wither.setLocation(player.getLocation().getX(), player.getLocation().getY() - 10,
                                        player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                                PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(wither);
                                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                                Thread.sleep(200);
                                //y +10
                                wither.setLocation(player.getLocation().getX(), player.getLocation().getY() + 10,
                                        player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                                packet = new PacketPlayOutSpawnEntityLiving(wither);
                                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                                Thread.sleep(200);
                                //x -10
                                wither.setLocation(player.getLocation().getX() - 10, player.getLocation().getY(),
                                        player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                                packet = new PacketPlayOutSpawnEntityLiving(wither);
                                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                                Thread.sleep(200);
                                //x +10
                                wither.setLocation(player.getLocation().getX() + 10, player.getLocation().getY(),
                                        player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                                packet = new PacketPlayOutSpawnEntityLiving(wither);
                                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                                Thread.sleep(200);
                                //z -10
                                wither.setLocation(player.getLocation().getX(), player.getLocation().getY(),
                                        player.getLocation().getZ() - 10, player.getLocation().getYaw(), player.getLocation().getPitch());
                                packet = new PacketPlayOutSpawnEntityLiving(wither);
                                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                                Thread.sleep(200);
                                //z +10
                                wither.setLocation(player.getLocation().getX(), player.getLocation().getY(),
                                        player.getLocation().getZ() + 10, player.getLocation().getYaw(), player.getLocation().getPitch());
                                packet = new PacketPlayOutSpawnEntityLiving(wither);
                                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);


                            } catch (InterruptedException ignored) {}
                        }).start();


                    }
                    Thread.sleep(1400);
                }

                for (UUID uuid : witherMap.keySet()) {
                    Player player = Bukkit.getPlayer(uuid);
                    if (player == null) {
                        witherMap.remove(uuid);
                        continue;
                    }
                    PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(witherMap.get(uuid).getId());
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(destroy);
                }
                witherMap.clear();


            } catch (InterruptedException ignored) {}
        });
        thread.start();

    }
    private void create() {
        for (Player player : list) {
            EntityWither wither = new EntityWither(((CraftWorld) player.getWorld()).getHandle());
            wither.setCustomName(message);
            wither.setLocation(player.getLocation().getX(), player.getLocation().getY(),
                    player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
            wither.setInvisible(true);
            wither.addEffect(new MobEffect(14, 1000, 2, true, true));

            PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(wither);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

            witherMap.put(player.getUniqueId(), wither);

        }
    }







    private void countdown() {
        Thread thread = new Thread(() -> {
           try {
               while (interval >= 0) {
                   Thread.sleep(1000);
                   interval--;
               }
               completed = true;
           } catch (InterruptedException ignored) {}
        });
        thread.start();
    }

}
