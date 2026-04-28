package QuantityMeasurementSystem.src.UC5_Unit_Conversion_API;

/**
 * UC5: Generic Quantity Class for Unit Conversion and Equality.
 * Implements the DRY principle and provides a public API for length conversions.
 */
public class QuantityMeasurementApp {

    public enum LengthUnit {
        FEET(12.0), 
        INCH(1.0), 
        YARDS(36.0), 
        CENTIMETERS(0.393701);

        public final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }
    }

    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        /**
         * Instance method to convert this quantity to a target unit.
         * @return a new QuantityLength object in the target unit.
         */
        public QuantityLength convertTo(LengthUnit targetUnit) {
            double convertedValue = (this.value * this.unit.conversionFactor) / targetUnit.conversionFactor;
            return new QuantityLength(convertedValue, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityLength that = (QuantityLength) obj;
            
            double v1 = this.value * this.unit.conversionFactor;
            double v2 = that.value * that.unit.conversionFactor;
            return Math.abs(v1 - v2) < 0.00001;
        }

        @Override
        public String toString() {
            return String.format("%.2f %s", value, unit);
        }
    }

    // --- API Methods (Method Overloading) ---

    /**
     * Overloaded method 1: Takes raw numeric value and units.
     */
    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");
        double converted = (value * from.conversionFactor) / to.conversionFactor;
        System.out.println("Input: convert(" + value + ", " + from + ", " + to + ") -> Output: " + String.format("%.4f", converted));
    }

    /**
     * Overloaded method 2: Takes an existing QuantityLength object.
     */
    public static void demonstrateLengthConversion(QuantityLength length, LengthUnit to) {
        QuantityLength result = length.convertTo(to);
        System.out.println("Object Conversion: " + length + " to " + to + " -> Result: " + result);
    }

    public static void main(String[] args) {
        System.out.println("--- UC5: Unit Conversion API ---");
        
        // Basic Conversions (Method 1)
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);
        demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARDS);
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCH);

        // Object-based Conversion (Method 2)
        QuantityLength myLength = new QuantityLength(5.0, LengthUnit.FEET);
        demonstrateLengthConversion(myLength, LengthUnit.INCH);
    }
}