package com.example.transportcostcomparator;

public class Transport {
    // 1. Private instance variables
    private String modeOfTransport;
    private String transportType;
    private double distancePerDay;
    private double costPerKm;
    private int travelDays;
    private double dailyCost;
    private double monthlyCost;
    private double totalMonthlyDistance;
    private String costCategory;
    private String recommendation;
    private String dateCreated;

    // 2. Default constructor
    public Transport() {
    }

    // 3. Parameterised constructor
    public Transport(String modeOfTransport, String transportType, double distancePerDay,
                     double costPerKm, int travelDays, double dailyCost, double monthlyCost,
                     double totalMonthlyDistance, String costCategory,
                     String recommendation, String dateCreated) {
        this.modeOfTransport = modeOfTransport;
        this.transportType = transportType;
        this.distancePerDay = distancePerDay;
        this.costPerKm = costPerKm;
        this.travelDays = travelDays;
        this.dailyCost = dailyCost;
        this.monthlyCost = monthlyCost;
        this.totalMonthlyDistance = totalMonthlyDistance;
        this.costCategory = costCategory;
        this.recommendation = recommendation;
        this.dateCreated = dateCreated;
    }

    // 4. Getter methods
    public String getModeOfTransport() { return modeOfTransport; }
    public String getTransportType() { return transportType; }
    public double getDistancePerDay() { return distancePerDay; }
    public double getCostPerKm() { return costPerKm; }
    public int getTravelDays() { return travelDays; }
    public double getDailyCost() { return dailyCost; }
    public double getMonthlyCost() { return monthlyCost; }
    public double getTotalMonthlyDistance() { return totalMonthlyDistance; }
    public String getCostCategory() { return costCategory; }
    public String getRecommendation() { return recommendation; }
    public String getDateCreated() { return dateCreated; }

    // 5. Setter methods
    public void setModeOfTransport(String modeOfTransport) { this.modeOfTransport = modeOfTransport; }
    public void setTransportType(String transportType) { this.transportType = transportType; }
    public void setDistancePerDay(double distancePerDay) { this.distancePerDay = distancePerDay; }
    public void setCostPerKm(double costPerKm) { this.costPerKm = costPerKm; }
    public void setTravelDays(int travelDays) { this.travelDays = travelDays; }
    public void setDailyCost(double dailyCost) { this.dailyCost = dailyCost; }
    public void setMonthlyCost(double monthlyCost) { this.monthlyCost = monthlyCost; }
    public void setTotalMonthlyDistance(double totalMonthlyDistance) { this.totalMonthlyDistance = totalMonthlyDistance; }
    public void setCostCategory(String costCategory) { this.costCategory = costCategory; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }
}