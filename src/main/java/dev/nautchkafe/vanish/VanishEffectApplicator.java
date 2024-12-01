package dev.nautchkafe.vanish;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.particle.Particle;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
* This class is responsible for applying vanish and visible effects on players.
*/
final class VanishEffectApplicator implements VanishEffect {

    private final Server server;

    VanishEffectApplicator(final Server server) {
        this.server = server;
    }

    /**
    * Applies the appropriate vanish or visible effect based on the boolean value passed.
    * @return A BiConsumer handling the player and the vanish state (true for vanish, false for visible).
    */
    @Override
    public BiConsumer<Player, Boolean> applyEffect() {
        return (player, isVanished) -> {
            final Function<Boolean, Consumer<Player>> effectFunction = isVanished ? this::applyVanishEffect : this::applyVisibleEffect;
            effectFunction.apply(isVanished).accept(player);
        };
    }

    /**
    * Applies vanishing effect on the player making them invisible to others who do not have the permission to see vanished players.
    * @param isVanished This parameter is not actively used in this method and only present to match functional interface signature.
    * @return A Consumer that accepts a Player and makes them vanish.
    */
    private Consumer<Player> applyVanishEffect(final Boolean isVanished) {
        return player -> {
            server.getOnlinePlayers().forEach(other -> {
                if (!other.hasPermission(VanishPermission.VANISH_PERMISSION)) {
                    other.hidePlayer(player);
                }
            });

            player.getWorld().strikeLightningEffect(player.getLocation());
        };
    }

    /**
    * Removes vanishing effect from the player making them visible to all and celebrates the visibility with heart particles.
    * @param isVanished This parameter is not actively used in this method and only present to match functional interface signature.
    * @return A Consumer that accepts a Player and makes them visible with a particle effect.
    */
    private Consumer<Player> applyVisibleEffect(final Boolean isVanished) {
        return player -> {
            server.getOnlinePlayers().forEach(other -> other.showPlayer(player));

            player.getWorld().spawnParticle(Particle.HEART, player.getLocation(), 10);
        };
    }
}
