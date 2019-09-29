package me.xemor.notes;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Note {

    Plugin pl;
    FileConfiguration config;
    Player player;

    public Note(Notes notes, Player player) {
        pl = notes;
        config = pl.getConfig();
        this.player = player;
        if (config.getString("notes." + player.getUniqueId().toString()) == null) {
            wipeNotes();
        }
    }

    public String getNotes() {
        String notes = config.getString("notes." + player.getUniqueId().toString());
        if (notes == null || notes.equals("")) {
            notes = "Your notes are empty!";
        }
        else {
            notes = "";
            for (int i = 0; i < parseNotes().length; i++) {
                notes += (i + 1) + ": " + parseNotes()[i] + "\n";
            }
        }
        return ChatColor.translateAlternateColorCodes('&', notes);
    }

    public void removeNote(int number) {
        String notes = "";
        number--;
        for (int i = 0; i < parseNotes().length; i++) {
            if (i != number) {
                notes += parseNotes()[i] + "\n";
            }
        }
        config.set("notes." + player.getUniqueId().toString(), notes);
        pl.saveConfig();
    }

    public void wipeNotes() {
        config.set("notes." + player.getUniqueId().toString(), "");
        pl.saveConfig();
    }

    public String[] parseNotes() {
        String notes = config.getString("notes." + player.getUniqueId().toString());
        return notes.split("\n");
    }

    public void addNote(String string) {
        string = string.replace("%coords%", String.format("X: %s, Y: %s, Z: %s", player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()));
        String uuid = player.getUniqueId().toString();
        string = config.getString("notes." + uuid) + string + "\n";
        config.set("notes." + uuid, string);
        pl.saveConfig();
    }

}
