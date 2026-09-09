package com.example.transportcostcomparator;

public class Recommendation {
    public static String getCostCategory(double monthlyCost) {
        if (monthlyCost < 1000.0) return "Low Cost";
        else if (monthlyCost < 2000.0) return "Moderate";
        else if (monthlyCost < 3000.0) return "High";
        else if (monthlyCost < 5000.0) return "Very High";
        else return "Excessive";
    }

    public static String getRecommendation(String category) {
        switch (category) {
            case "Low Cost": return "Continue using your current transport method.";
            case "Moderate": return "Consider carpooling where possible.";
            case "High": return "Reduce unnecessary trips and combine errands.";
            case "Very High": return "Consider public transport for regular commuting.";
            case "Excessive": return "Immediate action is recommended to reduce transport costs.";
            default: return "No recommendation available.";
        }
    }
}