package xyz.oribuin.rainbowboots.task;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.oribuin.orilibrary.util.HexUtils;
import xyz.oribuin.rainbowboots.RainbowBoots;

public class BootTask extends BukkitRunnable {

    private int hue = 0;

    private final RainbowBoots plugin;

    public BootTask(RainbowBoots plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().stream()
                // Check if vanished
                .filter(player -> player.getGameMode() != GameMode.SPECTATOR && !player.hasMetadata("vanished"))
                // Check if player has rainbow boots on.
                .filter(player -> player.getInventory().getBoots() != null && NBTEditor.contains(player.getInventory().getBoots(), "RainbowBoots"))
                .forEach(player -> {
                    // Particle Task
                    if (this.plugin.getConfig().getBoolean("use-particles")) {
                        for (int i = 1; i < 4; i++) {
                            if (this.plugin.getConfig().getBoolean("particles-player-only"))
                                player.spawnParticle(Particle.REDSTONE, player.getLocation(), 1, 0.2, 0.2, 0.2, new Particle.DustOptions(this.getRainbowColor(), 1f));
                            else
                                player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation(), 1, 0.2, 0.2, 0.2, new Particle.DustOptions(this.getRainbowColor(), 1f));
                        }
                    }

                    // Change the color of the boots
                    ItemStack item = player.getInventory().getBoots();
                    assert item != null;
                    LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                    assert meta != null;

                    meta.setDisplayName(HexUtils.colorify("<r#25:0.7:l>&lRainbow Boots"));

                    meta.setColor(this.getRainbowColor());
                    item.setItemMeta(meta);
                    player.getInventory().setBoots(item);
                });

    }

    private Color getRainbowColor() {
        this.hue = (hue + 4) % 362;
        java.awt.Color color = java.awt.Color.getHSBColor(hue / 360f, 1.0f, 1.0f);
        return Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue());
    }


}
