package com.jmsgvn.deuellib.scoreboard;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.jmsgvn.deuellib.DeuelLib;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.lang.reflect.Field;
import java.util.*;

public class PlayerBoard {
    private UUID uuid;

    private Objective objective;

    private Map<String, Integer> displayedScores = new HashMap<>();

    private Map<String, String> scorePrefixes = new HashMap<>();

    private Map<String, String> scoreSuffixes = new HashMap<>();

    private Set<String> sentTeamCreates = new HashSet<>();

    private final StringBuilder separateScoreBuilder = new StringBuilder();

    private final List<String> separateScores = new ArrayList<>();

    private final Set<String> recentlyUpdatedScores = new HashSet<>();

    private final Set<String> usedBaseScores = new HashSet<>();

    private final String[] prefixScoreSuffix = new String[3];

    private final ThreadLocal<LinkedList<String>> localList = ThreadLocal.withInitial(LinkedList::new);

    private final long createdAt;

    public PlayerBoard(Player player) {
        this.uuid = player.getUniqueId();
        Scoreboard scoreboard = DeuelLib.getInstance().getServer().getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("deuellib", "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(scoreboard);
        this.createdAt = System.currentTimeMillis();
    }

    public void update() {
        String title = ChatColor.translateAlternateColorCodes('&', ScoreboardManager.getScoreboardTitle(toPlayer()));

        List<String> lines = this.localList.get();

        if (!lines.isEmpty()) {
            lines.clear();
        }

        ScoreboardManager.getProvider().provide(this.localList.get(), toPlayer());
        this.recentlyUpdatedScores.clear();
        this.usedBaseScores.clear();
        int nextValue = lines.size();

        Preconditions.checkArgument((lines.size() < 16), "Too many lines passed!");
        Preconditions.checkArgument((title.length() < 32), "Title is too long!");

        if (!this.objective.getDisplayName().equals(title)) {
            this.objective.setDisplayName(title);
        }

        for (String line : lines) {
            if (48 <= line.length()) {
                throw new IllegalArgumentException("Lines is too long! Offending line: " + line);
            }

            String[] seperated = separate(line, this.usedBaseScores);
            String prefix = seperated[0];
            String score = seperated[1];
            String suffix = seperated[2];

            this.recentlyUpdatedScores.add(score);
            if (!this.sentTeamCreates.contains(score))
                createAndAddMember(score);
            if (!this.displayedScores.containsKey(score) ||
                this.displayedScores.get(score) != nextValue)
                setScore(score, nextValue);
            if (!this.scorePrefixes.containsKey(score) || !this.scorePrefixes.get(score).equals(prefix) || !this.scoreSuffixes.get(score)
                .equals(suffix))
                updateScore(score, prefix, suffix);
            nextValue--;
        }

        for (UnmodifiableIterator<String> unmodifiableIterator = ImmutableSet.copyOf(this.displayedScores.keySet()).iterator(); unmodifiableIterator.hasNext(); ) {
            String displayedScore = unmodifiableIterator.next();
            if (this.recentlyUpdatedScores.contains(displayedScore))
                continue;
            removeScore(displayedScore);
        }
    }

    private void setField(Packet packet, String field, Object value) {
        try {
            Field fieldObject = packet.getClass().getDeclaredField(field);
            fieldObject.setAccessible(true);
            fieldObject.set(packet, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void createAndAddMember(String scoreTitle) {
        ScoreboardTeamPacketMod scoreboardTeamAdd = new ScoreboardTeamPacketMod(scoreTitle, "_", "_", (Collection) ImmutableList.of(), 0);
        ScoreboardTeamPacketMod scoreboardTeamAddMember = new ScoreboardTeamPacketMod(scoreTitle, (Collection)ImmutableList.of(scoreTitle), 3);
        scoreboardTeamAdd.sendToPlayer(toPlayer());
        scoreboardTeamAddMember.sendToPlayer(toPlayer());
        this.sentTeamCreates.add(scoreTitle);
    }

    private void setScore(String score, int value) {
        PacketPlayOutScoreboardScore scoreboardScorePacket = new PacketPlayOutScoreboardScore();
        setField((Packet)scoreboardScorePacket, "a", score);
        setField((Packet)scoreboardScorePacket, "b", this.objective.getName());
        setField((Packet)scoreboardScorePacket, "c", Integer.valueOf(value));
        setField((Packet)scoreboardScorePacket, "d",
            PacketPlayOutScoreboardScore.EnumScoreboardAction.CHANGE);
        this.displayedScores.put(score, Integer.valueOf(value));
        (((CraftPlayer)toPlayer()).getHandle()).playerConnection.sendPacket((Packet)scoreboardScorePacket);
    }

    private void removeScore(String score) {
        this.displayedScores.remove(score);
        this.scorePrefixes.remove(score);
        this.scoreSuffixes.remove(score);
        (((CraftPlayer)toPlayer()).getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutScoreboardScore(score));
    }

    private void updateScore(String score, String prefix, String suffix) {
        this.scorePrefixes.put(score, prefix);
        this.scoreSuffixes.put(score, suffix);
        (new ScoreboardTeamPacketMod(score, prefix, suffix, null, 2)).sendToPlayer(toPlayer());
    }

    /**
     * DO NOT USE YET. DOES NOT COMPLY WITH PAPER FOR SOME REASON
     */
    public void destroy() {

        List<String> lines = this.localList.get();

        if (!lines.isEmpty()) {
            lines.clear();
        }

        ScoreboardManager.getProvider().provide(this.localList.get(), toPlayer());
        Iterator<String> iterator = localList.get().iterator();

        while (iterator.hasNext()) {
            String score = iterator.next();
            removeScore(score);
        }


        toPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public Player toPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    private String[] separate(String line, Collection<String> usedBaseScores) {
        line = ChatColor.translateAlternateColorCodes('&', line);
        String prefix = "";
        String score = "";
        String suffix = "";
        this.separateScores.clear();
        this.separateScoreBuilder.setLength(0);
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '*' || (this.separateScoreBuilder.length() == 16 && this.separateScores.size() < 3)) {
                this.separateScores.add(this.separateScoreBuilder.toString());
                this.separateScoreBuilder.setLength(0);
                if (c == '*')
                    continue;
            }
            this.separateScoreBuilder.append(c);
            continue;
        }
        this.separateScores.add(this.separateScoreBuilder.toString());
        switch (this.separateScores.size()) {
            case 1:
                score = this.separateScores.get(0);
                break;
            case 2:
                score = this.separateScores.get(0);
                suffix = this.separateScores.get(1);
                break;
            case 3:
                prefix = this.separateScores.get(0);
                score = this.separateScores.get(1);
                suffix = this.separateScores.get(2);
                break;
            default:
                DeuelLib.getInstance().getLogger().warning("Failed to separate scoreboard line. Input: " + line);
                break;
        }
        if (usedBaseScores.contains(score))
            if (score.length() <= 14) {
                for (ChatColor chatColor : ChatColor.values()) {
                    String possibleScore = chatColor + score;
                    if (!usedBaseScores.contains(possibleScore)) {
                        score = possibleScore;
                        break;
                    }
                }
                if (usedBaseScores.contains(score))
                    DeuelLib.getInstance().getLogger().warning("Failed to find alternate color code for: " + score);
            } else {
                DeuelLib.getInstance().getLogger().warning("Found a scoreboard base collision to shift: " + score);
            }
        if (prefix.length() > 16)
            prefix = ChatColor.DARK_RED.toString() + ChatColor.BOLD + ">16";
        if (score.length() > 16)
            score = ChatColor.DARK_RED.toString() + ChatColor.BOLD + ">16";
        if (suffix.length() > 16)
            suffix = ChatColor.DARK_RED.toString() + ChatColor.BOLD + ">16";
        usedBaseScores.add(score);
        this.prefixScoreSuffix[0] = prefix;
        this.prefixScoreSuffix[1] = score;
        this.prefixScoreSuffix[2] = suffix;
        return this.prefixScoreSuffix;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
