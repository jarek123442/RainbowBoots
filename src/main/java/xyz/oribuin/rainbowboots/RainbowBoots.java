package xyz.oribuin.rainbowboots;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import xyz.oribuin.orilibrary.OriPlugin;
import xyz.oribuin.rainbowboots.command.BootCommand;
import xyz.oribuin.rainbowboots.task.BootTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static xyz.oribuin.orilibrary.util.HexUtils.colorify;

public class RainbowBoots extends OriPlugin {
    @Override
    public void enablePlugin() {

        // Register Command
        new BootCommand(this).register(this.getConfig(), "player-only", "invalid-permission");

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
        meta.setDisplayName(colorify("<r:0.7:l>&lRainbow Boots"));
        List<String> lore = Arrays.asList(
                colorify("&7Rainbow boots with cool effects."),
                " ",
                colorify("&7Equip to experience the <r:0.7>Rainbow")
        );
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);

        itemStack.setItemMeta(meta);
        itemStack = NBTEditor.set(itemStack, true, "RainbowBoots");
        return itemStack;
    }

}
