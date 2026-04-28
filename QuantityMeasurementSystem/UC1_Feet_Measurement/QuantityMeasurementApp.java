package QuantityMeasurementSystem.UC1_Feet_Measurement;

public class QuantityMeasurementApp {

    // Step 3: Inner Class for Encapsulation and Immutability
    public static class Feet {
        private final double value;

        // Step 4: Constructor
        public Feet(double value) {
            this.value = value;
        }

        // Step 5: Overriding equals() for Value-Based Equality
        @Override
        public boolean equals(Object obj) {
            // Check for Same Reference
            if (this == obj) return true;

            // Check for Null and Type Safety
            if (obj == null || getClass() != obj.getClass()) return false;

            // Cast and Compare values using Double.compare for precision
            Feet feet = (Feet) obj;
            return Double.compare(feet.value, this.value) == 0;
        }
    }

    // Step 6: Main Method for testing
    public static void main(String[] args) {
        Feet firstFeet = new Feet(1.0);
        Feet secondFeet = new Feet(1.0);
        Feet thirdFeet = new Feet(2.0);

        System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Output: Equal (" + firstFeet.equals(secondFeet) + ")");

        System.out.println("\nInput: 1.0 ft and 2.0 ft");
        System.out.println("Output: Equal (" + firstFeet.equals(thirdFeet) + ")");
        
        System.out.println("\nInput: 1.0 ft and null");
        System.out.println("Output: Equal (" + firstFeet.equals(null) + ")");
    }
}