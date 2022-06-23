package com.jmsgvn.deuellib.util;

import com.jmsgvn.deuellib.DeuelLib;
import redis.clients.jedis.Jedis;

public class Redis {

    public static void set(String path, String value) {
        try (Jedis jedis = DeuelLib.getPool().getResource()) {
            jedis.set(path, value);
        }
    }

    public static String get(String path) {
        try (Jedis jedis = DeuelLib.getPool().getResource()) {
            String result = jedis.get(path);
            if (result == null) {
                return "-1";
            }
            return result;
        }
    }
}
