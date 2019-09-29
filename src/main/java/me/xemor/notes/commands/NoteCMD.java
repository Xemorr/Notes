package me.xemor.notes.commands;

import me.xemor.notes.Note;
import me.xemor.notes.Notes;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NoteCMD implements CommandExecutor {

    Notes pl;
    String prefix = "";

    public NoteCMD(Notes notes) {
        pl = notes;
        prefix = ChatColor.translateAlternateColorCodes('&', pl.getConfig().getString("prefix"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player!");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("note.notes")) {
            player.sendMessage("You do not have permission to use this command!");
        }
        else if (args.length == 0) {
            Note note = new Note(pl, player);
            String notes = note.getNotes();
            player.sendMessage(notes);
        }
        else if (args[0].equals("clear")) {
            Note note = new Note(pl, player);
            note.wipeNotes();
        }
        else if (args.length >= 2) {
            if (args[0].equals("add")) {
                Note note = new Note(pl, player);
                String string = "";
                for (int i = 1; i < args.length; i++) {
                    string += (" " + args[i]);
                }
                note.addNote(string);
                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " &fNote has been added successfully!"));
            }
            else if (args[0].equals("remove")) {
                Note note = new Note(pl, player);
                try {
                    note.removeNote(Integer.parseInt(args[1]));
                } catch(NumberFormatException e) {
                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " &f/note remove <number>"));
                }

            }
        }
        else {
            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " &f/note"));
            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " &f/note create <text>"));
            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " &f/note clear"));
            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', " &f/note remove <number>"));
        }
        return true;

    }
}
