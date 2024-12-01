package dev.nautchkafe.vanish;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
* This class is responsible for handling events related to player visibility, specifically, making players vanish and handle interactions approprietly when players are invisible.
*/
final class VanishListener implements Listener {
    
    private final VanishFunctor vanishFunctor;
    private final VanishEffectApplicator vanishEffectApplicator;

    VanishListener(final VanishFunctor vanishFunctor, 
                   final VanishEffectApplicator vanishEffectApplicator) {
        this.vanishFunctor = vanishFunctor;
        this.vanishEffectApplicator = vanishEffectApplicator;
    }

    @EventHandler
    void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final boolean isVanished = isPlayerVanished(player);
        applyVanishEffect(player, isVanished);
    }

    @EventHandler
    void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        removePlayerState(player);
    }

    @EventHandler
    void onPlayerPickupItem(PlayerPickupItemEvent event) {
        final Player player = event.getPlayer();
        if (isPlayerVanished(player)) {
            event.setCancelled(false); 
        }
    }

    @EventHandler
    void onPlayerInteractEntity(final PlayerInteractEntityEvent event) {
        final Player player = event.getPlayer();
        if (canPlayerInteract(player)) {
            event.setCancelled(false);  
        } 

        event.setCancelled(true); 
    }

    /**
    * Checks if a player is currently vanished.
    *
    * @param player The player to check.
    * @return true if the player is vanished, otherwise false.
    */
    private boolean isPlayerVanished(final Player player) {
        return vanishFunctor.findState().apply(player.getUniqueId()).isVanished();
    }

    /**
    * Applies or removes vanish effects based on the vanished state of the player.
    *
    * @param player The player on whom to apply the effects.
    * @param isVanished The vanished state of the player.
    */
    private void applyVanishEffect(final Player player, boolean isVanished) {
        vanishEffectApplicator.applyEffect().accept(player, isVanished);
    }

    /**
    * Removes the vanish state of a player.
    *
    * @param player The player whose vanish state is to be removed.
    */
    private void removePlayerState(final Player player) {
        vanishFunctor.removeState().apply(player.getUniqueId());
    }

    /**
    * Determines if a vanished player can interact based on their permissions.
    *
    * @param player The player to check.
    * @return true if the player can interact, otherwise false.
    */
    private boolean canPlayerInteract(final Player player) {
        return isPlayerVanished(player) && player.hasPermission(VanishPermission.VANISH_PERMISSION);
    }
}