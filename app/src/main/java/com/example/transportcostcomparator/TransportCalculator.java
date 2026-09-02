package com.example.transportcostcomparator;

public class TransportCalculator {

    // Calculates the Daily Transport Cost
    public static double calculateDailyCost(double distancePerDay, double costPerKm) {
        return distancePerDay * costPerKm;
    }

    // Calculates the Monthly Transport Cost
    public static double calculateMonthlyCost(double dailyCost, int travelDays) {
        return dailyCost * travelDays;
    }

    // Calculates the Monthly Travel Distance
    public static double calculateMonthlyDistance(double distancePerDay, int travelDays) {
        return distancePerDay * travelDays;
    }
}


