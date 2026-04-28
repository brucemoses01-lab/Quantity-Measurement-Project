package QuantityMeasurementSystem.src.UC4_Extended_Units;

public class QuantityMeasurementApp {

    // Step 1: Updated LengthUnit Enum with Yards and Centimeters
    public enum LengthUnit {
        FEET(12.0),           // 1 Foot = 12 Inches
        INCH(1.0),            // 1 Inch = 1 Inch (Base Unit)
        YARDS(36.0),          // 1 Yard = 36 Inches (3 Feet)
        CENTIMETERS(0.393701); // 1 cm = 0.393701 Inches

        public final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }
    }

    // Step 2: QuantityLength Class (No changes needed from UC3 - Pure Scalability!)
    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength that = (QuantityLength) obj;

            // Convert everything to Inches for comparison
            double valueInInches1 = this.value * this.unit.conversionFactor;
            double valueInInches2 = that.value * that.unit.conversionFactor;

            // Use an epsilon/tolerance check for CM due to floating point decimals
            return Math.abs(valueInInches1 - valueInInches2) < 0.00001;
        }
    }

    public static void main(String[] args) {
        // Yard to Feet
        QuantityLength oneYard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength threeFeet = new QuantityLength(3.0, LengthUnit.FEET);
        System.out.println("1.0 Yard and 3.0 Feet: " + oneYard.equals(threeFeet));

        // Yard to Inches
        QuantityLength thirtySixInches = new QuantityLength(36.0, LengthUnit.INCH);
        System.out.println("1.0 Yard and 36.0 Inches: " + oneYard.equals(thirtySixInches));

        // CM to Inches
        QuantityLength oneCm = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength pointInch = new QuantityLength(0.393701, LengthUnit.INCH);
        System.out.println("1.0 CM and 0.393701 Inches: " + oneCm.equals(pointInch));
    }
}