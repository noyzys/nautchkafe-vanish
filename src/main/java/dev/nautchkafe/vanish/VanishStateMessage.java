package dev.nautchkafe.vanish;

import net.kyori.adventure.text.Component;
import java.util.function.Function;

/**
* Interface for handling vanish state messages in an application.
* Implementations of this interface should define how to render a message
* based on the vanish state of a user or an object.
*/
interface VanishStateMessage {

    /**
    * Returns a {@link Function} that takes a Boolean input representing the vanish state,
    * and returns a {@link Component} that visually represents this state to the user.
    *
    * @return a function that maps vanish state to a visual component
    */
    Function<Boolean, Component> renderVanishMessage();

    /**
    * Returns a {@link Function} that takes a Boolean input representing the vanish state,
    * and returns a {@link String} that visually represents this state to the user.
    *
    * @return a function that maps vanish state to a visual component
    */
    Function<Boolean, String> VANISH_STATUS_MESSAGE = isVanished -> isVanished 
            ? "<green>Vanish Mode: <bold>ENABLED</bold>" 
            : "<red>Vanish Mode: <bold>DISABLED</bold>";
}
