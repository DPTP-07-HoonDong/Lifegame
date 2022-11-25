package com.holub.life.feature;

public class TTL2 implements TTLBehavior {
    @Override
    public String[] getMenuName() {
        return new String[]{"TTL", "2"};
    }

    @Override
    public int getTimeToLive() {
        return 2;
    }
}
