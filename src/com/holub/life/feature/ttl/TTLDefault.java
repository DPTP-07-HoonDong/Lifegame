package com.holub.life.feature.ttl;

public class TTLDefault implements TTLBehavior {
    private static TTLDefault ttlDefault = new TTLDefault();

    private TTLDefault() {

    }

    public static TTLDefault getInstance() {
        return ttlDefault;
    }

    @Override
    public String[] getMenuName() {
        return new String[]{"TTL", "Default (1)"};
    }

    @Override
    public int getTimeToLive() {
        return 1;
    }
}
