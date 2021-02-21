package xyz.oribuin.rainbowboots.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.oribuin.orilibrary.command.Argument;
import xyz.oribuin.orilibrary.command.Command;
import xyz.oribuin.orilibrary.libs.jetbrains.annotations.NotNull;
import xyz.oribuin.orilibrary.libs.jetbrains.annotations.Nullable;
import xyz.oribuin.rainbowboots.RainbowBoots;

import java.util.Collections;
import java.util.List;

import static xyz.oribuin.orilibrary.util.HexUtils.colorify;

@Command.Info(name = "rainbowboots",
        description = "Get the rtainbow boots.",
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
    }

    @Override
    public void runFunction(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] strings) {
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
    public @Nullable List<Argument> complete(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        Argument arg = new Argument(0, new String[]{"reload"});
        arg.setRequiredPerm("rainbowboots.reload");
        return Collections.singletonList(arg);
    }
}
