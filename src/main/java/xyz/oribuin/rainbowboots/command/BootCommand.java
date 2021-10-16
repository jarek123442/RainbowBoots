package xyz.oribuin.rainbowboots.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.oribuin.orilibrary.command.Command;
import xyz.oribuin.rainbowboots.RainbowBoots;

import java.util.Collections;
import java.util.List;

import static xyz.oribuin.orilibrary.util.HexUtils.colorify;

@Command.Info(name = "rainbowboots",
        description = "Get the rainbow boots.",
        subCommands = {},
        usage = "/rainbowboots",
        playerOnly = true,
        permission = "rainbowboots.use",
        aliases = {}
)
public class BootCommand extends Command {

    private final RainbowBoots plugin = (RainbowBoots) this.getOriPlugin();

    public BootCommand(RainbowBoots plugin) {
        super(plugin);

        this.register(
                sender -> sender.sendMessage(colorify(this.plugin.get("player-only", "&c&lError &7| &fOnly a player can execute this command."))),
                sender -> sender.sendMessage(colorify(this.plugin.get("invalid-permission", "&c&lError &7| &fYou do not have permission to run this command.")))
        );
    }

    @Override
    public void runFunction(CommandSender sender, String s, String[] strings) {
        Player player = (Player) sender;

        if (strings.length == 0) {
            if (player.getInventory().firstEmpty() == -1) {
                sender.sendMessage(colorify(this.plugin.get("required-empty-slot", "&c&lError &7| &fYou need a free slot to get these boots.")));
                return;
            }

            player.getInventory().addItem(((RainbowBoots) this.getOriPlugin()).boots());
            sender.sendMessage(colorify(this.plugin.get("given-boots", "<r:0.7>&lRainbow Boots &7| &fYou have been given rainbow boots.")));
            return;
        }

        if (!sender.hasPermission("rainbowboots.reload")) {
            sender.sendMessage(colorify(this.plugin.get("invalid-permission", "&c&lError &7| &fYou do not have permission to run this command.")));
            return;
        }

        this.plugin.reload();
        sender.sendMessage(colorify(this.plugin.get("reload", "<r:0.7>&lRainbowBoots &7| &fYou have reloaded the plugin.")));
    }

    @Override
    public List<String> completeString(CommandSender commandSender, String s, String[] strings) {
        if (commandSender.hasPermission("rainbowboots.reload"))
            return Collections.singletonList("reload");

        return null;
    }
}

