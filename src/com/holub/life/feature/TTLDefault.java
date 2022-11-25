package com.holub.life.feature;

public class TTLDefault implements TTLBehavior {
    private static TTLDefault ttlDefault = new TTLDefault();

    private TTLDefault() {

    }

    public static TTLDefault getInstance() {
        return ttlDefault;
    }

    @Override
    public String[] getMenuName() {
        return new String[]{"TTL", "Default"};
    }

    @Override
    public int getTimeToLive() {
        return 1;
    }
}
