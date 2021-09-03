package xyz.oribuin.rainbowboots.command;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
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

        final FileConfiguration config = this.plugin.getConfig();
        this.register(
                sender -> sender.sendMessage(colorify(config.getString("player-only"))),
                sender -> sender.sendMessage(colorify(config.getString("invalid-permission")))
        );
    }

    @Override
    public void runFunction(CommandSender sender, String s, String[] strings) {
        Player player = (Player) sender;

        if (strings.length == 0) {
            if (player.getInventory().firstEmpty() == -1) {
                sender.sendMessage(colorify(this.plugin.getConfig().getString("required-empty-slot")));
                return;
            }

            player.getInventory().addItem(((RainbowBoots) this.getOriPlugin()).boots());
            sender.sendMessage(colorify(this.plugin.getConfig().getString("given-boots")));
            return;
        }

        if (!sender.hasPermission("rainbowboots.reload")) {
            sender.sendMessage(colorify(this.plugin.getConfig().getString("invalid-permission")));
            return;
        }

        this.plugin.reload();
        sender.sendMessage(colorify(this.plugin.getConfig().getString("reload")));
    }

    @Override
    public List<String> completeString(CommandSender commandSender, String s, String[] strings) {
        if (commandSender.hasPermission("rainbowboots.reload"))
            return Collections.singletonList("reload");

        return null;
    }
}

