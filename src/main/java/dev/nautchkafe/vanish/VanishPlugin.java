package dev.nautchkafe.vanish;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.server.Server;

/* Vanish plugin for bukkit api */
public final class VanishPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        final Server server = getServer();

        final VanishCoordinator vanishCoordinator = new VanishCoordinator();
        final VanishEffectApplicator vanishEffectApplicator = new VanishEffectApplicator(server);
        final VanishMessageRenderer messageRenderer = new VanishMessageRenderer();


        final VanishCommand vanishCommand = new VanishCommand(vanishCoordinator, vanishEffectApplicator);
        getCommand("vanish").setExecutor(vanishCommand);

        final VanishListener vanishListener = new VanishListener(vanishCoordinator, vanishEffectApplicator);
        getServer().getPluginManager().registerEvents(vanishListener, this);

        final VanishStateTask vanishStateTask = new VanishStateTask(vanishCoordinator, vanishEffectApplicator, messageRenderer, server);
        server.getScheduler().runTaskTimerAsynchronously(this, vanishStateTask, 0L, 20L);  // 0L = start, 20L = 20 server tick
    }
}
