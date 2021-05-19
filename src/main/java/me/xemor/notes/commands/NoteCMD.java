package me.xemor.notes.commands;

import de.themoep.minedown.MineDown;
import me.xemor.notes.Note;
import me.xemor.notes.Notes;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class NoteCMD implements CommandExecutor {

    private final Notes pl;
    private String prefix = "";
    private final String mustBePlayer;
    private final String clearedNotes;
    private final String addedNote;
    private final String removedNote;
    private final String noteUsage;
    private final String addUsage;
    private final String clearUsage;
    private final String removeUsage;

    public NoteCMD(Notes notes) {
        pl = notes;
        FileConfiguration configuration = pl.getConfig();
        ConfigurationSection language = configuration.getConfigurationSection("language");
        mustBePlayer = prefix + language.getString("mustBePlayer", "You must be a player!");
        clearedNotes = prefix + language.getString("clearedNotes", "&fYour notes have been cleared successfully!");
        addedNote = prefix + language.getString("addedNote", "&fNote has been added successfully!");
        removedNote = prefix + language.getString("removedNote", "&fThe note has been removed successfully!");
        noteUsage = prefix + language.getString("noteUsage", "&f/note");
        addUsage = prefix + language.getString("addUsage", "&f/note create <text>");
        clearUsage = prefix + language.getString("clearUsage", "&f/note clear");
        removeUsage = prefix + language.getString("removeUsage", "&f/note remove <number>");
        prefix = pl.getConfig().getString("prefix");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.spigot().sendMessage(new MineDown(mustBePlayer).toComponent());
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            Note note = new Note(pl, player);
            String notes = note.getNotes();
            player.spigot().sendMessage(new MineDown(notes).toComponent());
        }
        else if (args[0].equals("clear")) {
            Note note = new Note(pl, player);
            note.wipeNotes();
            player.spigot().sendMessage(new MineDown(clearedNotes).toComponent());
        }
        else if (args.length >= 2) {
            if (args[0].equals("add")) {
                Note note = new Note(pl, player);
                StringBuilder string = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    string.append(" ").append(args[i]);
                }
                string.substring(1);
                note.addNote(string.toString());
                player.spigot().sendMessage(new MineDown(addedNote).toComponent());
            }
            else if (args[0].equals("remove")) {
                Note note = new Note(pl, player);
                try {
                    note.removeNote(Integer.parseInt(args[1]));
                    player.spigot().sendMessage(new MineDown(removedNote).toComponent());
                } catch(NumberFormatException e) {
                    player.spigot().sendMessage(new MineDown(removeUsage).toComponent());
                }
            }
        }
        else {
            player.spigot().sendMessage(new MineDown(noteUsage).toComponent());
            player.spigot().sendMessage(new MineDown(addUsage).toComponent());
            player.spigot().sendMessage(new MineDown(clearUsage).toComponent());
            player.spigot().sendMessage(new MineDown(removeUsage).toComponent());
        }
        return true;

    }
}
