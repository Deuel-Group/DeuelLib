package com.jmsgvn.deuellib.tab.command;

import com.jmsgvn.deuellib.tab.TabManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ToggleTabCommand implements CommandExecutor {
    @Override public boolean onCommand(CommandSender commandSender, Command command, String s,
        String[] strings) {

        if (!commandSender.hasPermission("deuellib.admin")) {
            commandSender.sendMessage(
                ChatColor.RED + "You do not have permission to run this command.");
            return true;
        }

        TabManager.setInitiated(!TabManager.isInitiated());
        commandSender.sendMessage(
            ChatColor.YELLOW + "The tab has been " + (TabManager.isInitiated() ?
                ChatColor.GREEN + "enabled" :
                ChatColor.RED + "disabled") + ChatColor.YELLOW + ".");

        return true;
    }
}
