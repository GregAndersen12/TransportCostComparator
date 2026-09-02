package com.example.transportcostcomparator;

public class Recommendation {

    // Determines the transport cost category using conditional statements
    public static String getCostCategory(double monthlyCost) {
        if (monthlyCost < 1000.0) {
            return "Low Cost";
        } else if (monthlyCost >= 1000.0 && monthlyCost <= 3000.0) {
            return "Moderate Cost";
        } else {
            return "High Cost";
        }
    }

    // Determines the cost-saving recommendation using conditional statements
    public static String getRecommendation(double monthlyCost) {
        if (monthlyCost < 1000.0) {
            return "Great job keeping costs low. Consider carpooling occasionally to save even more.";
        } else if (monthlyCost >= 1000.0 && monthlyCost <= 3000.0) {
            return "Consider purchasing a monthly public transport pass or joining a lift club to reduce expenses.";
        } else {
            return "Your transport costs are high. Explore alternative routes, remote work days, or utilizing public rail systems.";
        }
    }
}