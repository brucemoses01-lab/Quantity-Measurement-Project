package QuantityMeasurementSystem.src.UC6_Measurement_Arithmetic;

/**
 * UC6: Addition of Two Length Units.
 * Enables arithmetic operations between different units while maintaining immutability.
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
         * UC6: Adds another quantity to this one.
         * Result unit is based on the first operand (this).
         */
        public QuantityLength add(QuantityLength that) {
            if (that == null) throw new IllegalArgumentException("Operand cannot be null");
            
            // 1. Convert both to base unit (Inches)
            double v1InBase = this.value * this.unit.conversionFactor;
            double v2InBase = that.value * that.unit.conversionFactor;
            
            // 2. Sum them
            double sumInBase = v1InBase + v2InBase;
            
            // 3. Convert sum back to the unit of the first operand (this.unit)
            double resultValue = sumInBase / this.unit.conversionFactor;
            
            return new QuantityLength(resultValue, this.unit);
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

    public static void main(String[] args) {
        System.out.println("--- UC6: Measurement Arithmetic (Addition) ---");

        // 1 foot + 12 inches = 2 feet
        QuantityLength feet1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength inch12 = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength result1 = feet1.add(inch12);
        System.out.println("1.0 FT + 12.0 IN = " + result1);

        // 1 yard + 3 feet = 2 yards
        QuantityLength yard1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength feet3 = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength result2 = yard1.add(feet3);
        System.out.println("1.0 YD + 3.0 FT = " + result2);

        // 2.54 cm + 1 inch = 5.08 cm
        QuantityLength cmValue = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength inchValue = new QuantityLength(1.0, LengthUnit.INCH);
        QuantityLength result3 = cmValue.add(inchValue);
        System.out.println("2.54 CM + 1.0 IN = " + result3);
    }
}