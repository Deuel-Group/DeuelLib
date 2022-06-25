package com.jmsgvn.deuellib.event;

import com.jmsgvn.deuellib.DeuelLib;
import com.jmsgvn.deuellib.util.CC;
import net.luckperms.api.model.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        User user = DeuelLib.getInstance().getLuckPerms().getUserManager().getUser(event.getPlayer().getUniqueId());

        if (user == null) {
            return;
        }

        String prefix = user.getCachedData().getMetaData().getPrefix();
        String username = event.getPlayer().getName();
        String message = event.getMessage();
        String suffix = user.getCachedData().getMetaData().getSuffix();

        if (prefix == null) {
            prefix = "";
        }

        if (suffix == null) {
            suffix = "";
        }

        event.setFormat(CC.translate(prefix +  username + suffix + "&7: &f" + message));
    }
}
