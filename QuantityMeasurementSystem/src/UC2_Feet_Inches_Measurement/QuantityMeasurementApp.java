package QuantityMeasurementSystem.src.UC2_Feet_Inches_Measurement;

public class QuantityMeasurementApp {

    // Step 1: Feet Class (Identical to UC1)
    public static class Feet {
        private final double value;
        public Feet(double value) { this.value = value; }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Feet feet = (Feet) obj;
            return Double.compare(feet.value, this.value) == 0;
        }
    }

    // Step 1: Inches Class (New implementation similar to Feet)
    public static class Inches {
        private final double value;
        public Inches(double value) { this.value = value; }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Inches inches = (Inches) obj;
            return Double.compare(inches.value, this.value) == 0;
        }
    }

    // Step 3: Method for Feet equality check
    public static boolean compareFeet(double v1, double v2) {
        Feet f1 = new Feet(v1);
        Feet f2 = new Feet(v2);
        return f1.equals(f2);
    }

    // Step 3: Method for Inches equality check
    public static boolean compareInches(double v1, double v2) {
        Inches i1 = new Inches(v1);
        Inches i2 = new Inches(v2);
        return i1.equals(i2);
    }

    public static void main(String[] args) {
        // Example Output logic
        System.out.println("Input: 1.0 inch and 1.0 inch");
        System.out.println("Output: Equal (" + compareInches(1.0, 1.0) + ")");

        System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Output: Equal (" + compareFeet(1.0, 1.0) + ")");
    }
}