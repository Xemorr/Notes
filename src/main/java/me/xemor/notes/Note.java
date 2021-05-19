package me.xemor.notes;

import de.themoep.minedown.MineDown;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Note {

    Plugin pl;
    FileConfiguration config;
    Player player;
    private String noteFormat;

    public Note(Notes notes, Player player) {
        pl = notes;
        config = pl.getConfig();
        this.player = player;
        noteFormat = config.getString("language.noteFormat", "&r&f%number%: %note%\n");
        if (config.getString("notes." + player.getUniqueId().toString()) == null) {
            wipeNotes();
        }
    }

    public String getNotes() {
        StringBuilder newNotes = new StringBuilder();
        String[] notes = parseNotes();
        for (int i = 0; i < notes.length; i++) {
            newNotes.append(noteFormat.replaceAll("%number%", String.valueOf(i + 1)).replaceAll("%note%", notes[i]));
        }
        return newNotes.toString();
    }

    public void removeNote(int number) {
        String newNotes = "";
        String[] notes = parseNotes();
        number--;
        for (int i = 0; i < notes.length; i++) {
            if (i != number) {
                newNotes += notes[i] + "\n";
            }
        }
        config.set("notes." + player.getUniqueId().toString(),  newNotes);
        pl.saveConfig();
    }

    public void wipeNotes() {
        config.set("notes." + player.getUniqueId().toString(), "");
        pl.saveConfig();
    }

    public String[] parseNotes() {
        String notes = config.getString("notes." + player.getUniqueId().toString());
        if (notes == null || notes.equals("")) {
            notes = "Your notes are empty!";
        }
        return notes.split("\n");
    }

    public void addNote(String string) {
        string = string.replaceAll("%coords%", String.format("X: %s, Y: %s, Z: %s", player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()));
        String uuid = player.getUniqueId().toString();
        string = config.getString("notes." + uuid) + string + "\n";
        config.set("notes." + uuid, string);
        pl.saveConfig();
    }

}
