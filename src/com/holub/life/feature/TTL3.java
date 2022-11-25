package com.holub.life.feature;

public class TTL3 implements TTLBehavior {
    @Override
    public String[] getMenuName() {
        return new String[]{"TTL", "3"};
    }

    @Override
    public int getTimeToLive() {
        return 3;
    }
}
