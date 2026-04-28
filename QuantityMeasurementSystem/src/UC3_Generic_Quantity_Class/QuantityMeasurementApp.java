package QuantityMeasurementSystem.src.UC3_Generic_Quantity_Class;

public class QuantityMeasurementApp {
    // Step 1: LengthUnit Enum with conversion factors relative to Feet
    public enum LengthUnit {
        FEET(1.0), 
        INCH(1.0 / 12.0); // 12 inches = 1 foot

        public final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }
    }

    // Step 2: Generic Quantity Length Class
    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {
            // Reflexive check
            if (this == obj) return true;
            
            // Null and Type safety
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength that = (QuantityLength) obj;

            // Convert both to the base unit (Feet) before comparison
            double valueInFeet1 = this.value * this.unit.conversionFactor;
            double valueInFeet2 = that.value * that.unit.conversionFactor;

            // Use Double.compare for floating-point precision
            return Double.compare(valueInFeet1, valueInFeet2) == 0;
        }
    }

    public static void main(String[] args) {
        // Test Case: 1.0 Feet vs 12.0 Inches
        QuantityLength oneFeet = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength twelveInches = new QuantityLength(12.0, LengthUnit.INCH);
        
        System.out.println("Input: Quantity(1.0, FEET) and Quantity(12.0, INCH)");
        System.out.println("Output: Equal (" + oneFeet.equals(twelveInches) + ")");

        // Test Case: 1.0 Inch vs 1.0 Inch
        QuantityLength oneInch = new QuantityLength(1.0, LengthUnit.INCH);
        QuantityLength anotherInch = new QuantityLength(1.0, LengthUnit.INCH);
        
        System.out.println("\nInput: Quantity(1.0, INCH) and Quantity(1.0, INCH)");
        System.out.println("Output: Equal (" + oneInch.equals(anotherInch) + ")");
    }
}
    
