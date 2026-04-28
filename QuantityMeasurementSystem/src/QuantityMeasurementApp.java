package QuantityMeasurementSystem.src;

public class QuantityMeasurementApp {

    /**
     * Compares two values in feet for equality.
     * @param value1 The first measurement in feet.
     * @param value2 The second measurement in feet.
     * @return true if values are equal, false otherwise.
     */
    public boolean compareFeet(Double value1, Double value2) {
        // Precondition check: handle nulls or non-numeric cases
        if (value1 == null || value2 == null) {
            return false;
        }

        // Main Flow: Compare the two values
        return value1.equals(value2);
    }

    public static void main(String[] args) {
        QuantityMeasurementApp app = new QuantityMeasurementApp();

        // Example Usage
        double firstValue = 3.0;
        double secondValue = 3.0;

        boolean result = app.compareFeet(firstValue, secondValue);
        
        System.out.println("Comparison Result for " + firstValue + "ft and " + secondValue + "ft: " + result);
    }
}
    

