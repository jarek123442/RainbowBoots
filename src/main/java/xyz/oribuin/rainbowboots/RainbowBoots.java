package xyz.oribuin.rainbowboots;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import xyz.oribuin.orilibrary.OriPlugin;
import xyz.oribuin.orilibrary.util.HexUtils;
import xyz.oribuin.rainbowboots.command.BootCommand;
import xyz.oribuin.rainbowboots.manager.EmptyManager;
import xyz.oribuin.rainbowboots.task.BootTask;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static xyz.oribuin.orilibrary.util.HexUtils.colorify;

@SuppressWarnings("unchecked")
public class RainbowBoots extends OriPlugin {

    @Override
    public void enablePlugin() {

//        this.getManager(EmptyManager.class);

        // Register Command
        new BootCommand(this);

        // Register Color changing and particle task
        new BootTask(this).runTaskTimer(this, 0, this.getConfig().getLong("update-interval"));
    }

    @Override
    public void disablePlugin() {
        // Unused
    }

    public ItemStack boots() {
        ItemStack itemStack = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
        assert meta != null;
        meta.setColor(Color.RED);
        meta.setDisplayName(colorify(this.get("rainbow-boots.name", "<r:0.7:l>&lRainbow Boots")));
        List<String> lore = this.get("rainbow-boots.lore", Arrays.asList(
                        "&7Rainbow boots with cool effects.",
                        " ",
                        "&7Equip to experience the <r:0.7>&lRainbow"
                ))
                .stream().map(HexUtils::colorify).collect(Collectors.toList());

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.values());
        meta.setUnbreakable(true);

        itemStack.setItemMeta(meta);
        itemStack = NBTEditor.set(itemStack, true, "RainbowBoots");
        return itemStack;
    }

    /**
     * Get a config value or the default
     *
     * @param path The path to the config value
     * @param def  The default value
     * @return The config option or the default value.
     */
    public <T> T get(String path, T def) {
        return this.getConfig().get(path) != null ? (T) this.getConfig().get(path) : def;
    }

}
