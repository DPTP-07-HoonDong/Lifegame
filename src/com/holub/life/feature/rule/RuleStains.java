package com.holub.life.feature.rule;

public class RuleStains implements RuleBehavior {
    private static RuleStains ruleStains = new RuleStains();

    private RuleStains() {

    }

    public static RuleStains getInstance() {
        return ruleStains;
    }

    @Override
    public String[] getMenuName() {
        return new String[]{"Rule", "Stains (3,6-8/2-3,5-8)"};
    }

    @Override
    public int[] getRule() {
        return new int[0];
    }
}
