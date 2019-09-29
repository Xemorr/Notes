package me.xemor.notes.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NoteTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Must be a player!");
        }
        List<String> tabCompleteList = new ArrayList<String>();
        if (args.length == 1) {
            if (!args[0].equals("")) {
                tabCompleteList.add("clear");
                tabCompleteList.add("add");
                tabCompleteList.add("remove");
            }
        }
        return tabCompleteList;
    }
}
