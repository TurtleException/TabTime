package de.turtleboi.spigot.tabtime;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class TabTime extends JavaPlugin {
    public static final String SYM_SUN = "§6☀";
    public static final String SYM_MOON = "§9\uD83C\uDF19";
    public static final String SYM_RAIN = "§b☂";
    public static final String SYM_THUNDER = "§3⚡";
    public static final String SYM_NETHER = "§c☠";
    public static final String SYM_END = "§8\uD83C\uDF1F";

    @Override
    public void onEnable() {
        // schedule updater
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                player.setPlayerListFooter(toTime(player.getWorld()));
            }
        }, 1, 1);
    }

    /***
     * Creates a String containing the formatted time and symbol for a {@link World}. The text is formatted as minecraft
     * legacy text.
     * @param world The source world for time and weather.
     * @return Formatted time & symbol text.
     */
    public static String toTime(World world) {
        if (!world.isNatural()) {
            String suffix = world.isUltraWarm() ? SYM_NETHER : SYM_END;
            return "§7" + "§k" + "??"
                    + "§8" + ":"
                    + "§7" + "§k" + "??"
                    + " " + suffix;
        }

        return toTime(world.getTime(), world.isClearWeather(), world.isThundering());
    }

    /***
     * Creates a String containing the formatted time and symbol for a specified time and weather. The text is formatted
     * as minecraft legacy text.
     * It is presumed that the source {@link World} of the provided arguments is natural.
     * @param time The world time.
     * @param clearWeather true, if the weather is clear.
     * @param thundering true, if it is thundering.
     * @return Formatted time & symbol text.
     */
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
