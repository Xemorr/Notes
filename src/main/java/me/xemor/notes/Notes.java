package me.xemor.notes;

import me.xemor.notes.commands.NoteCMD;
import me.xemor.notes.commands.NoteTabCompleter;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

public final class Notes extends JavaPlugin {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        this.getCommand("note").setExecutor(new NoteCMD(this));
        this.getCommand("note").setTabCompleter(new NoteTabCompleter());
        handleMetrics();
        checkForNewUpdate();
    }

    @Override
    public void onDisable() {

    }

    public void handleMetrics() {
        Metrics metrics = new Metrics(this, 11239);
        if (!metrics.isEnabled()) {
            getLogger().log(Level.WARNING, "[Notes] You have disabled bstats, this is very sad :(");
        }
    }

    public void checkForNewUpdate() {
        UpdateChecker.init(this, 71689);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (UpdateChecker.isInitialized()) {
                    UpdateChecker updateChecker = UpdateChecker.get();
                    updateChecker.requestUpdateCheck().thenAccept(result -> {
                        if (result.requiresUpdate()) {
                            getLogger().warning("This server is still running " + getDescription().getVersion() + " of Notes");
                            getLogger().warning("The latest version is " + result.getNewestVersion());
                            getLogger().warning("Updating is important to ensure there are not any bugs or vulnerabilities.");
                            getLogger().warning("As well as ensuring your players have the best time when writing notes!");
                        }
                    });
                }
            }
        }.runTaskTimer(this, 0L, 432000L);
    }
}
