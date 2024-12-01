package dev.nautchkafe.vanish;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
* Represents a command to toggle vanish state for players in a game.
*/
final class VanishCommand implements CommandExecutor {

    private final VanishCoordinator vanishCoordinator;
    private final VanishEffectApplicator vanishEffectApplicator;

    VanishCommand(final VanishCoordinator vanishCoordinator, 
            final VanishEffectApplicator vanishEffectApplicator) {
        this.vanishCoordinator = vanishCoordinator;
        this.vanishEffectApplicator = vanishEffectApplicator;
    }

    /**
    * Handles the command input by players to toggle their vanish state.
    *
    * @param sender The sender of the command, which should be a player.
    * @param command The command that was executed.
    * @param label The alias of the command used.
    * @param args Additional command arguments.
    * @return true to indicate that the command was processed.
    */
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, 
            final String label, 
            final String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command is only for players.");
            return true;
        }

        final boolean isVanished = !vanishCoordinator.state().apply(player.getUniqueId()).isVanished();

        player.sendMessage(isVanished ? "You are now vanished." : "You are now visible.");
        vanishEffectApplicator.applyEffect().accept(player, isVanished);
        return true;
    }
}