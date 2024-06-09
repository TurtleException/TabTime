package de.turtleboi.spigot.tabtime;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TabTime extends JavaPlugin {
    public static final String SYM_SUN = "§6☀";
    public static final String SYM_MOON = "§9\uD83C\uDF19";
    public static final String SYM_RAIN = "§b☂";
    public static final String SYM_THUNDER = "§3⚡";

    @Override
    public void onEnable() {
        // schedule updater
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                player.setPlayerListFooter(toTime(player.getWorld()));
            }
        }, 1, 1);
    }

    public static String toTime(World world) {
        return toTime(world.getTime(), world.isClearWeather(), world.isThundering());
    }

    public static String toTime(long time, boolean clearWeather, boolean thundering) {
        int hours   = (int) ((time / 1000 + 6) % 24);
        int minutes = (int) ((time % 1000) / (1000 / 60.0));

        String weather;
        if (clearWeather) {
            if (hours >= 6 & hours < 18) {
                weather = SYM_SUN;
            } else {
                weather = SYM_MOON;
            }
        } else if (thundering) {
            weather = SYM_THUNDER;
        } else {
            weather = SYM_RAIN;
        }

        return "§7" + String.format("%02d", hours)
                + "§8" + ":"
                + "§7" + String.format("%02d", minutes)
                + " " + weather;
    }
}
