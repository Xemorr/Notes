package me.xemor.notes;

import me.xemor.notes.commands.NoteCMD;
import me.xemor.notes.commands.NoteTabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Notes extends JavaPlugin {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        this.getCommand("note").setExecutor(new NoteCMD(this));
        this.getCommand("note").setTabCompleter(new NoteTabCompleter());
    }

    @Override
    public void onDisable() {

    }
}
