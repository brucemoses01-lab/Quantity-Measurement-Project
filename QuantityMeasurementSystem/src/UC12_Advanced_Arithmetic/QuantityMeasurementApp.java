package QuantityMeasurementSystem.src.UC12_Advanced_Arithmetic;

interface IMeasurable {
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
}

// Reuse enums from UC11 (LengthUnit, WeightUnit, VolumeUnit)
enum LengthUnit implements IMeasurable {
    FEET(12.0), INCH(1.0);
    private final double factor;
    LengthUnit(double factor) { this.factor = factor; }
    public double convertToBaseUnit(double v) { return v * factor; }
    public double convertFromBaseUnit(double bv) { return bv / factor; }
}

class Quantity<U extends IMeasurable> {
    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        this.value = value;
        this.unit = unit;
    }

    // --- Subtraction (Implicit Target Unit) ---
    public Quantity<U> subtract(Quantity<U> that) {
        return subtract(that, this.unit);
    }

    // --- Subtraction (Explicit Target Unit) ---
    public Quantity<U> subtract(Quantity<U> that, U targetUnit) {
        validateInput(that);
        double diffInBase = this.unit.convertToBaseUnit(this.value) - 
                            that.unit.convertToBaseUnit(that.value);
        return new Quantity<>(targetUnit.convertFromBaseUnit(diffInBase), targetUnit);
    }

    // --- Division (Returns dimensionless scalar) ---
    public double divide(Quantity<U> that) {
        validateInput(that);
        double divisorBase = that.unit.convertToBaseUnit(that.value);
        if (divisorBase == 0) throw new ArithmeticException("Cannot divide by zero quantity");
        
        return this.unit.convertToBaseUnit(this.value) / divisorBase;
    }

    private void validateInput(Quantity<U> that) {
        if (that == null) throw new IllegalArgumentException("Operand cannot be null");
        if (this.unit.getClass() != that.unit.getClass()) 
            throw new IllegalArgumentException("Cross-category arithmetic not allowed");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quantity<?> that = (Quantity<?>) obj;
        if (this.unit.getClass() != that.unit.getClass()) return false;
        return Math.abs(this.unit.convertToBaseUnit(this.value) - 
               ((IMeasurable)that.unit).convertToBaseUnit(that.value)) < 0.001;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, unit);
    }
}

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        System.out.println("--- UC12: Advanced Arithmetic (Subtract & Divide) ---");

        Quantity<LengthUnit> tenFeet = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> sixInches = new Quantity<>(6.0, LengthUnit.INCH);

        // Subtraction: 10ft - 6in = 9.5ft
        System.out.println("10ft - 6in = " + tenFeet.subtract(sixInches));

        // Division: 10ft / 2ft = 5.0 (scalar)
        Quantity<LengthUnit> twoFeet = new Quantity<>(2.0, LengthUnit.FEET);
        System.out.println("10ft / 2ft = " + tenFeet.divide(twoFeet));
    }
}
