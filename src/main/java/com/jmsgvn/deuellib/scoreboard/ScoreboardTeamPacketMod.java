package com.jmsgvn.deuellib.scoreboard;

import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Collection;

public class ScoreboardTeamPacketMod {

    private PacketPlayOutScoreboardTeam packet;

    private static Field aField;

    private static Field bField;

    private static Field cField;

    private static Field dField;

    private static Field eField;

    private static Field fField;

    private static Field gField;

    private static Field hField;

    private static Field iField;

    static {
        try {
            aField = PacketPlayOutScoreboardTeam.class.getDeclaredField("a");
            bField = PacketPlayOutScoreboardTeam.class.getDeclaredField("b");
            cField = PacketPlayOutScoreboardTeam.class.getDeclaredField("c");
            dField = PacketPlayOutScoreboardTeam.class.getDeclaredField("d");
            eField = PacketPlayOutScoreboardTeam.class.getDeclaredField("e");
            fField = PacketPlayOutScoreboardTeam.class.getDeclaredField("f");
            gField = PacketPlayOutScoreboardTeam.class.getDeclaredField("g");
            hField = PacketPlayOutScoreboardTeam.class.getDeclaredField("h");
            iField = PacketPlayOutScoreboardTeam.class.getDeclaredField("i");
            aField.setAccessible(true);
            bField.setAccessible(true);
            cField.setAccessible(true);
            dField.setAccessible(true);
            eField.setAccessible(true);
            fField.setAccessible(true);
            gField.setAccessible(true);
            hField.setAccessible(true);
            iField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ScoreboardTeamPacketMod(String name, String prefix, String suffix, Collection players, int paramInt) {
        this.packet = new PacketPlayOutScoreboardTeam();

        try {
            aField.set(this.packet, name);
            hField.set(this.packet, paramInt);
            if (paramInt == 0 || paramInt == 2) {
                bField.set(this.packet, name);
                cField.set(this.packet, prefix);
                dField.set(this.packet, suffix);
                fField.set(this.packet, 3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (paramInt == 0)
            addAll(players);
    }

    public ScoreboardTeamPacketMod(String name, Collection players, int paramInt) {
        this(name, "", "", players, paramInt);

        try {
            fField.set(this.packet, 3);
            aField.set(this.packet, name);
            iField.set(this.packet, paramInt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAll(players);
    }

    public void sendToPlayer(Player bukkitPlayer) {
        (((CraftPlayer)bukkitPlayer).getHandle()).playerConnection.sendPacket(this.packet);
    }

    private void addAll(Collection col) {
        if (col == null)
            return;
        try {
            ((Collection)gField.get(this.packet)).addAll(col);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
