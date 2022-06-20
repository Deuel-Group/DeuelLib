package com.jmsgvn.deuellib.tab;

import com.mojang.authlib.GameProfile;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TabUtils {
    private static Map<String, GameProfile> cache = new ConcurrentHashMap<>();

    public static GameProfile getOrCreateProfile(String name, UUID id) {
        GameProfile player = cache.get(name);

        if (player == null) {

            player = new GameProfile(UUID.randomUUID(), name);

            cache.put(name, player);
        }

        return player;
    }

    public static GameProfile getOrCreateProfile(String name) {
        return getOrCreateProfile(name, UUID.randomUUID());
    }
}
