package com.holub.life.feature.rule;

public class RuleDefault implements RuleBehavior {
    private static RuleDefault ruleDefault = new RuleDefault();

    private RuleDefault() {

    }

    public static RuleDefault getInstance() {
        return ruleDefault;
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
