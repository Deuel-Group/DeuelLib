package com.jmsgvn.deuellib.tab;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import com.jmsgvn.deuellib.DeuelLib;
import com.jmsgvn.deuellib.tab.common.SkinTexture;
import com.jmsgvn.deuellib.tab.common.TabListCommons;
import com.mojang.authlib.GameProfile;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

public class PlayerInfoPacketMod {

    public void addPlayer(PlayerTab tab, String name, int ping, GameProfile profile) {

        Player player = tab.toPlayer();

        PacketContainer packet = DeuelLib.getInstance().getProtocolManager().createPacket(
            PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        WrappedGameProfile gameProfile = new WrappedGameProfile(profile.getId(), profile.getName());


        PlayerInfoData playerInfoData =
            new PlayerInfoData(gameProfile, ping, EnumWrappers.NativeGameMode.SURVIVAL,
                WrappedChatComponent.fromText(name));

        playerInfoData.getProfile().getProperties().put("textures",
            new WrappedSignedProperty("textures", TabListCommons.defaultTexture.SKIN_VALUE,
                TabListCommons.defaultTexture.SKIN_SIGNATURE));

        packet.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));
        sendPacket(player, packet);
    }

    public void updateName(PlayerTab tab, String name, int ping, GameProfile profile) {
        Player player = tab.toPlayer();
        PacketContainer packet = DeuelLib.getInstance().getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.UPDATE_DISPLAY_NAME);

        WrappedGameProfile wrappedProfile = new WrappedGameProfile(profile.getId(), profile.getName());

        PlayerInfoData playerInfoData = new PlayerInfoData(wrappedProfile, ping, EnumWrappers.NativeGameMode.SURVIVAL, WrappedChatComponent.fromText(name));
        packet.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));
        sendPacket(player, packet);
    }

    public void updateHeaderAndFooter(Player player, String header, String footer) {

        PacketContainer headerAndFooter = new PacketContainer(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);

        headerAndFooter.getChatComponents().write(0, WrappedChatComponent.fromText(header));
        headerAndFooter.getChatComponents().write(1, WrappedChatComponent.fromText(footer));

        sendPacket(player, headerAndFooter);
    }

    public void updateSkin(PlayerTab playerTab, String name, int ping, GameProfile profile, SkinTexture skinTexture) {
        Player player = playerTab.toPlayer();

        WrappedGameProfile gameProfile = new WrappedGameProfile(profile.getId(), profile.getName());
        PlayerInfoData playerInfoData = new PlayerInfoData(gameProfile, ping, EnumWrappers.NativeGameMode.SURVIVAL, WrappedChatComponent.fromText(name));
        playerInfoData.getProfile().getProperties().put("texture", new WrappedSignedProperty("textures", skinTexture.SKIN_VALUE, skinTexture.SKIN_SIGNATURE));

        PacketContainer remove = DeuelLib.getInstance().getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        remove.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        remove.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));

        PacketContainer add = DeuelLib.getInstance().getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        add.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        add.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));

        sendPacket(player, remove);
        sendPacket(player, add);
    }

    public void updatePing(PlayerTab playerTab, String name, int ping, GameProfile profile) {
        Player player = playerTab.toPlayer();

        WrappedGameProfile gameProfile = new WrappedGameProfile(profile.getId(), profile.getName());
        PlayerInfoData playerInfoData = new PlayerInfoData(gameProfile, ping, EnumWrappers.NativeGameMode.SURVIVAL, WrappedChatComponent.fromText(name));

        PacketContainer container = DeuelLib.getInstance().getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        container.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.UPDATE_LATENCY);
        container.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));

        sendPacket(player, container);
    }

    public void destroy(PlayerTab playerTab, String name, int ping, GameProfile profile) {
        Player player = playerTab.toPlayer();

        WrappedGameProfile gameProfile = new WrappedGameProfile(profile.getId(), profile.getName());
        PlayerInfoData playerInfoData = new PlayerInfoData(gameProfile, ping, EnumWrappers.NativeGameMode.SURVIVAL, WrappedChatComponent.fromText(name));

        PacketContainer container = DeuelLib.getInstance().getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        container.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        container.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));

        sendPacket(player, container);
    }

    private void sendPacket(Player player, PacketContainer packet) {
        try {
            DeuelLib.getInstance().getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
