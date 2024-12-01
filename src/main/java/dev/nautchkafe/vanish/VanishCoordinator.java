package dev.nautchkafe.vanish;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;

/**
* It manages the vanish state of users identified by UUID.
*/
final class VanishCoordinator implements VanishFunctor {

    /* ConcurrentHashMap, CF for multi threaded operations, use haft single*/
    private final Map<UUID, VanishState> vanishedUsers = new HashMap<>();

    /**
    * Returns a function that finds the vanish state of a user by their UUID.
    *
    * @return a function which retrieves the VanishState for a given UUID.
    */
    @Override
    public Function<UUID, VanishState> findState() {
        return playerId -> vanishedUsers.get(playerId);
    }

    /**
    * Returns a function that toggles the vanish state of a user by their UUID.
    *
    * @return a function which toggles the VanishState for a given UUID.
    */
    @Override
    public Function<UUID, VanishState> state() {
        return playerId -> {
            final VanishState currentState = vanishedUsers.getOrDefault(playerId, defaultVanishState(playerId));
            return toggleState(currentState);
        };
    }

    /**
    * Returns a function that removes the vanish state of a user by their UUID.
    * Maybe problem The use of getOrDefault(playerId, new VanishState(playerId, false)) in the findState() and state() methods creates a new VanishState object every time.
    *
    * @return a function which removes the VanishState for a given UUID and returns true if successful.
    */
    @Override
    public Function<UUID, Boolean> removeState() {
        return playerId -> vanishedUsers.remove(playerId) != null;
    }

    /**
    * Provides the default vanish state for a new user identified by their UUID.
    *
    * @param playerId the UUID of the player.
    * @return a new VanishState with a default vanished flag set to false.
    */
    private VanishState defaultVanishState(final UUID playerId) {
        return new VanishState(playerId, false); /
    }

    /**
    * Toggles the vanish state of a given VanishState instance.
    *
    * @param currentState the current state of the player.
    * @return a new VanishState with the opposite vanished flag, or the current state if no change is needed.
    */
    private VanishState toggleState(final VanishState currentState) {
        final boolean newState = !currentState.isVanished();
        return newState == currentState.isVanished()
                ? currentState 
                : new VanishState(currentState.getPlayerId(), newState); 
    }
}
