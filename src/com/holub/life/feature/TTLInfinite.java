package com.holub.life.feature;

public class TTLInfinite implements TTLBehavior {
    @Override
    public String[] getMenuName() {
        return new String[]{"TTL", "Infinite"};
    }

    @Override
    public int getTimeToLive() {
        return 4;
    }
}
