package com.jmsgvn.deuellib.scoreboard;

import com.jmsgvn.deuellib.DeuelLib;

public class ScoreboardThread extends Thread{

    public ScoreboardThread() {
        setName("Deuel - Scoreboard Thread");
        setDaemon(true);
    }

    public void run() {
        while (DeuelLib.getInstance().isEnabled()) {
            for (PlayerBoard board : ScoreboardManager.getBoards().values()) {
                try {
                    if (System.currentTimeMillis() - board.getCreatedAt() >= 250L) {
                        board.update();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
