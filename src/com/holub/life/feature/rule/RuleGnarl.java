package com.holub.life.feature.rule;

public class RuleGnarl implements RuleBehavior {
    private static RuleGnarl ruleGnarl = new RuleGnarl();

    private RuleGnarl() {

    }

    public static RuleGnarl getInstance() {
        return ruleGnarl;
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
