package dev.nautchkafe.vanish;

import java.util.UUID;

/**
* Represents the vanish state of a player.
*
* <p>This record encapsulates the information needed to track whether a
* specific player is in a vanished state or not.</p>
*
* @param playerId the unique identifier of the player
* @param isVanished a boolean indicating if the player is currently vanished
*/
record VanishState(
	UUID playerId, 
	boolean isVanished
) {
}