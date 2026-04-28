package QuantityMeasurementSystem.src.UC8_Architecture_Refactoring;

/**
 * UC8: Refactored Unit Enum with Conversion Responsibility.
 * Centralizes unit logic to support future categories like Weight and Volume.
 */

// Step 1: Standalone Enum with Conversion Responsibility
enum LengthUnit {
    FEET(1.0), 
    INCHES(1.0 / 12.0), 
    YARDS(3.0), 
    CENTIMETERS(1.0 / 30.48); // Base unit is FEET here

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    // New Responsibility 1: Convert a value from this unit to the Base Unit (Feet)
    public double convertToBaseUnit(double value) {
        return value * this.conversionFactor;
    }

    // New Responsibility 2: Convert a value from the Base Unit (Feet) back to this unit
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / this.conversionFactor;
    }
}

// Step 2: Simplified QuantityLength Class
class QuantityLength {
    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");
        this.value = value;
        this.unit = unit;
    }

    public QuantityLength convertTo(LengthUnit targetUnit) {
        double baseValue = this.unit.convertToBaseUnit(this.value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);
        return new QuantityLength(convertedValue, targetUnit);
    }

    public QuantityLength add(QuantityLength that, LengthUnit targetUnit) {
        double v1Base = this.unit.convertToBaseUnit(this.value);
        double v2Base = that.unit.convertToBaseUnit(that.value);
        double sumInBase = v1Base + v2Base;
        return new QuantityLength(targetUnit.convertFromBaseUnit(sumInBase), targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityLength that = (QuantityLength) obj;
        return Math.abs(this.unit.convertToBaseUnit(this.value) - 
                        that.unit.convertToBaseUnit(that.value)) < 0.001;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s", value, unit);
    }
}

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        System.out.println("--- UC8: Architectural Refactoring ---");

        QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
        
        // 1. Test: Enum Responsibility (12 inches to feet)
        System.out.println("12 Inches to Feet (via Enum): " + LengthUnit.INCHES.convertToBaseUnit(12.0));

        // 2. Test: ConvertTo (1 foot to 12 inches)
        System.out.println("1.0 Foot to Inches: " + oneFoot.convertTo(LengthUnit.INCHES));

        // 3. Test: Addition (1 foot + 12 inches = 0.667 Yards)
        QuantityLength twelveInches = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength sumInYards = oneFoot.add(twelveInches, LengthUnit.YARDS);
        System.out.println("1.0 FT + 12.0 IN in YARDS: " + sumInYards);
    }
}