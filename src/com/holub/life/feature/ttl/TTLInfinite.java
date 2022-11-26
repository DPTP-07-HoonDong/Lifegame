package com.holub.life.feature.ttl;

public class TTLInfinite implements TTLBehavior {
    private static TTLInfinite ttlInfinite = new TTLInfinite();

    private TTLInfinite() {

    }

    public static TTLInfinite getInstance() {
        return ttlInfinite;
    }

    @Override
    public String[] getMenuName() {
        return new String[]{"TTL", "Infinite"};
    }

    @Override
    public int getTimeToLive() {
        return 4;
    }
}
