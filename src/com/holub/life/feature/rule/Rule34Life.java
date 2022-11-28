package com.holub.life.feature.rule;

public class Rule34Life implements RuleBehavior {
    private static Rule34Life rule34Life = new Rule34Life();

    private Rule34Life() {

    }

    public static Rule34Life getInstance() {
        return rule34Life;
    }

    @Override
    public String[] getMenuName() {
        return new String[0];
    }

    @Override
    public int[] getRule() {
        return new int[0];
    }
}
