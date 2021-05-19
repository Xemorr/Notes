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
            if (args[0].toLowerCase().startsWith("c")) {
                tabCompleteList.add("clear");
            }
            else if (args[0].toLowerCase().startsWith("a")) {
                tabCompleteList.add("add");
            }
            else if (args[0].toLowerCase().startsWith("r")) {
                tabCompleteList.add("remove");
            }
            else if (args[0].toLowerCase().startsWith("h")) {
                tabCompleteList.add("help");
            }
            else {
                tabCompleteList.add("clear");
                tabCompleteList.add("add");
                tabCompleteList.add("remove");
                tabCompleteList.add("help");
            }
        }
        return tabCompleteList;
    }
}
