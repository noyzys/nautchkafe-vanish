package dev.nautchkafe.vanish;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.entity.Player;

/**
* Example represents a specialized version of an audience that can handle vanishing states of entities.
* This record encapsulates an instance of {@link Audience}.
*/
record VanishAudience(Audience audience) {

    /**
    * Creates a {@code VanishAudience} from a given {@link Player}.
    *
    * @param player the player to create the audience from
    * @return a new instance of {@code VanishAudience} associated with the provided player
    */
    public static VanishAudience ofPlayer(final Player player) {
        return new AudienceAdapter(Audience.audience(player));
    }

    /**
    * Creates a {@code VanishAudience} from a given {@link Audience}.
    *
    * @param audience the audience to encapsulate
    * @return a new instance of {@code VanishAudience} encapsulating the provided audience
    */
    public static VanishAudience ofAudience(final Audience audience) {
        return new VanishAudience(audience);
    }
}
