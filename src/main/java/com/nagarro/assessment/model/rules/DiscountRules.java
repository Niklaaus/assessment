package com.nagarro.assessment.model.rules;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;

@Component
public class DiscountRules {

    private final List<PercentageBasedDiscountRule> percenBasedDiscountRules;

    private final List<UniversalDiscountRule> universalDiscountRules;

    @Autowired
    public DiscountRules(List<PercentageBasedDiscountRule> percenBasedDiscountRules, List<UniversalDiscountRule> universalDiscountRules) {
        this.percenBasedDiscountRules = percenBasedDiscountRules;
        this.universalDiscountRules = universalDiscountRules;
        this.percenBasedDiscountRules.sort(Comparator.comparingInt(DiscountRule::priority).reversed());
        this.universalDiscountRules.sort(Comparator.comparingInt(DiscountRule::priority).reversed());
    }

    public List<PercentageBasedDiscountRule> getPercenBasedDiscountRules() {
        return percenBasedDiscountRules;
    }

    public List<UniversalDiscountRule> getUniversalDiscountRules() {
        return universalDiscountRules;
    }
}
