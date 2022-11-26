package com.holub.life.feature.ttl;

public class TTL3 implements TTLBehavior {
    private static TTL3 ttl3 = new TTL3();

    private TTL3() {

    }

    public static TTL3 getInstance() {
        return ttl3;
    }

    @Override
    public String[] getMenuName() {
        return new String[]{"TTL", "3"};
    }

    @Override
    public int getTimeToLive() {
        return 3;
    }
}
