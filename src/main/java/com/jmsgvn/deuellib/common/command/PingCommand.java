package com.jmsgvn.deuellib.common.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {
    @Override public boolean onCommand(CommandSender commandSender, Command command, String s,
        String[] strings) {

        if (strings.length == 0) {
            if (commandSender instanceof Player) {
                commandSender.sendMessage(ChatColor.YELLOW +
                    "Your ping: " + ChatColor.GOLD + ((CraftPlayer) commandSender).getHandle().ping + ChatColor.YELLOW + "ms");
            } else {
                commandSender.sendMessage(ChatColor.RED + "As console you cannot request the ping of yourself... Your ping is 0.");
            }
        } else {
            OfflinePlayer player = Bukkit.getPlayer(strings[0]);

            if (player == null) {
                commandSender.sendMessage(ChatColor.RED + "Cannot find a player with the name " + strings[0] + ".");
                return true;
            }

            if (!player.isOnline()) {
                commandSender.sendMessage(ChatColor.RED + player.getName() + " is not online.");
                return true;
            }

            commandSender.sendMessage(ChatColor.YELLOW +
                player.getName() + " ping: " + ChatColor.GOLD + ((CraftPlayer) player).getHandle().ping + ChatColor.YELLOW + "ms");
        }

        return true;
    }
}
