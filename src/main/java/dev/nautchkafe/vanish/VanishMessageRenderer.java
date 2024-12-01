package dev.nautchkafe.vanish;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import java.util.function.Function;

/**
* Class for rendering vanish state messages using MiniMessage.
*/
final class VanishMessageRenderer implements VanishStateMessage {

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    /**
    * Generates a function that converts vanish state into a styled component message.
    *
    * @return A function mapping vanish status to a styled component.
    */
    @Override
    public Function<Boolean, Component> renderVanishMessage() {
        return isVanished -> miniMessage.parse(VanishStateMessage.VANISH_STATUS_MESSAGE.apply(isVanished));
    }
}
