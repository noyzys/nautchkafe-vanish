package dev.nautchkafe.vanish;

import org.bukkit.entity.Player;
import java.util.function.BiConsumer;

/**
* The VanishEffect interface is used to define a method that will apply
* a vanish effect to a Player. The effect can be toggled on or off.
*/
interface VanishEffect {

    /**
    * Method to apply or remove a vanish effect from a player.
    *
    * @return BiConsumer that takes a Player and a Boolean indicating whether to apply
    * or remove the vanish effect.
    */
    BiConsumer<Player, Boolean> applyEffect();
}