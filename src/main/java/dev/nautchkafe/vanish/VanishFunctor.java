package dev.nautchkafe.vanish;

import java.util.UUID;
import java.util.function.Function;

/**
* Interface representing the state of a vanish operation.
* Provides methods to find, retrieve, and remove the vanish state
* associated with a unique identifier (UUID).
*/
interface VanishFunctor {

	/**
	* Finds the current vanish state associated with the given UUID.
	*
	* @return A function that takes a UUID and returns the associated VanishState.
	*/
	Function<UUID, VanishState> findState();

	/**
	* Gets the current vanish state associated with the given UUID.
	*
	* @return A function that takes a UUID and returns the current VanishState.
	*/
	Function<UUID, VanishState> state();

	/**
	* Removes the vanish state associated with the given UUID.
	*
	* @return A function that takes a UUID and returns a boolean indicating
	* whether the vanish state was successfully removed.
	*/
	Function<UUID, Boolean> removeState();
}
