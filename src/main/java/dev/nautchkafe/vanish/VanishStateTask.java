package dev.nautchkafe.vanish;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.Server;

/**
* Process the actual vanishing logic runnable and the server environment.
*/
final class VanishStateTask implements Runnable {

    private final VanishFunctor vanishFunctor;
    private final VanishEffectApplicator vanishEffectApplicator;
    private final VanishMessageRenderer messageRenderer;
    private final Server server; 

    VanishStateTask(final VanishFunctor vanishFunctor, 
                     final VanishEffectApplicator vanishEffectApplicator, 
                     final VanishMessageRenderer messageRenderer,
                     final Server server) {
        this.vanishFunctor = vanishFunctor;
        this.vanishEffectApplicator = vanishEffectApplicator;
        this.messageRenderer = messageRenderer;
        this.server = server;
    }

    /**
     * Server#getOnlinePlayers :: Or using parallelStream instead of a regular stream in this context can improve performance when processing
     * a large number of players. By leveraging multiple CPU threads, parallelStream can distribute the work
     * across several cores, allowing operations like checking player states, applying effects, and rendering messages
     * to be done concurrently, thus reducing the overall execution time for a large player base.
     *
     * However, it's important to note that parallelStream isn't always faster. The performance improvement depends
     * on factors such as the complexity of the operations being performed on each player, the number of available CPU cores,
     * and the overhead of managing parallel execution. If the operations are lightweight, the overhead of managing multiple threads
     * might negate any potential benefits. Therefore, it's recommended to monitor performance when switching from regular streams
     * to parallel streams, especially in environments with variable loads.
     */
    @Override
    public void run() {
        server.getOnlinePlayers().parallelStream().forEach(player -> {  
            final boolean isVanished = vanishFunctor.findState().apply(player.getUniqueId()).isVanished();
            vanishEffectApplicator.applyEffect().accept(player, isVanished);

            final Component actionBarMessage = messageRenderer.renderVanishMessage().apply(isVanished);
            player.sendActionBar(actionBarMessage);
        });
    }
}
