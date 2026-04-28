package QuantityMeasurementSystem.src.UC7_Target_Unit_Addition;

/**
 * UC7: Addition with Target Unit Specification.
 * Demonstrates Method Overloading and private utility methods for better API design.
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

        // --- UC6: Implicit Target Unit (Backward Compatibility) ---
        public QuantityLength add(QuantityLength that) {
            return add(this, that, this.unit);
        }

        // --- UC7: Explicit Target Unit (Method Overloading) ---
        public static QuantityLength add(QuantityLength length1, QuantityLength length2, LengthUnit targetUnit) {
            if (length1 == null || length2 == null || targetUnit == null) {
                throw new IllegalArgumentException("Parameters cannot be null");
            }
            return performAddition(length1, length2, targetUnit);
        }

        /**
         * Private Utility Method: Centralizes the addition math to avoid code duplication.
         */
        private static QuantityLength performAddition(QuantityLength l1, QuantityLength l2, LengthUnit target) {
            // Normalize both to base unit (Inches)
            double v1InBase = l1.value * l1.unit.conversionFactor;
            double v2InBase = l2.value * l2.unit.conversionFactor;
            
            // Sum and convert to target
            double resultValue = (v1InBase + v2InBase) / target.conversionFactor;
            return new QuantityLength(resultValue, target);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityLength that = (QuantityLength) obj;
            double v1 = this.value * this.unit.conversionFactor;
            double v2 = that.value * that.unit.conversionFactor;
            return Math.abs(v1 - v2) < 0.001; // Epsilon for rounding
        }

        @Override
        public String toString() {
            return String.format("%.3f %s", value, unit);
        }
    }

    public static void main(String[] args) {
        System.out.println("--- UC7: Explicit Target Unit Addition ---");

        QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength twelveInches = new QuantityLength(12.0, LengthUnit.INCH);

        // Result in Yards: 1ft + 12in = 2ft = ~0.667 Yards
        QuantityLength resultInYards = QuantityLength.add(oneFoot, twelveInches, LengthUnit.YARDS);
        System.out.println("1.0 FT + 12.0 IN in YARDS: " + resultInYards);

        // Result in Inches: 1ft + 12in = 24 Inches
        QuantityLength resultInInches = QuantityLength.add(oneFoot, twelveInches, LengthUnit.INCH);
        System.out.println("1.0 FT + 12.0 IN in INCHES: " + resultInInches);
        
        // Result in CM: 1in + 1in = 5.08 CM
        QuantityLength oneInch = new QuantityLength(1.0, LengthUnit.INCH);
        QuantityLength resultInCm = QuantityLength.add(oneInch, oneInch, LengthUnit.CENTIMETERS);
        System.out.println("1.0 IN + 1.0 IN in CM: " + resultInCm);
    }
}