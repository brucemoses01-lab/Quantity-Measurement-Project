package QuantityMeasurementSystem.src.UC13_Centralized_DRY_Arithmetic;

import java.util.function.DoubleBinaryOperator;

/**
 * UC13: Centralized Arithmetic Logic (DRY Principle).
 * Uses Functional Interfaces and Enums to eliminate code duplication.
 */

interface IMeasurable {
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
}

// Enum using Lambda Expressions for Arithmetic Operations
enum ArithmeticOperation {
    ADD((a, b) -> a + b),
    SUBTRACT((a, b) -> a - b),
    DIVIDE((a, b) -> {
        if (b == 0) throw new ArithmeticException("Division by zero");
        return a / b;
    });

    private final DoubleBinaryOperator operator;
    ArithmeticOperation(DoubleBinaryOperator operator) { this.operator = operator; }
    
    public double apply(double left, double right) {
        return operator.applyAsDouble(left, right);
    }
}

class Quantity<U extends IMeasurable> {
    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        this.value = value;
        this.unit = unit;
    }

    // --- REFACTORED PUBLIC API ---
    
    public Quantity<U> add(Quantity<U> other) { return add(other, this.unit); }
    public Quantity<U> add(Quantity<U> other, U target) { return computeQuantity(other, target, ArithmeticOperation.ADD); }

    public Quantity<U> subtract(Quantity<U> other) { return subtract(other, this.unit); }
    public Quantity<U> subtract(Quantity<U> other, U target) { return computeQuantity(other, target, ArithmeticOperation.SUBTRACT); }

    public double divide(Quantity<U> other) {
        validate(other);
        return ArithmeticOperation.DIVIDE.apply(this.getBaseValue(), other.getBaseValue());
    }

    // --- CENTRALIZED HELPERS (The DRY "Engine") ---

    private void validate(Quantity<U> other) {
        if (other == null) throw new IllegalArgumentException("Operand cannot be null");
        if (this.unit.getClass() != other.unit.getClass()) 
            throw new IllegalArgumentException("Category mismatch");
    }

    private double getBaseValue() {
        return this.unit.convertToBaseUnit(this.value);
    }

    private Quantity<U> computeQuantity(Quantity<U> other, U target, ArithmeticOperation op) {
        validate(other);
        double resultInBase = op.apply(this.getBaseValue(), other.getBaseValue());
        double finalValue = target.convertFromBaseUnit(resultInBase);
        return new Quantity<>(Math.round(finalValue * 100.0) / 100.0, target); // Rounding to 2 decimal places
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quantity<?> that = (Quantity<?>) obj;
        if (this.unit.getClass() != that.unit.getClass()) return false;
        return Math.abs(this.getBaseValue() - ((IMeasurable)that.unit).convertToBaseUnit(that.value)) < 0.001;
    }

    @Override
    public String toString() { return String.format("%.2f %s", value, unit); }
}

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        System.out.println("--- UC13: Centralized DRY Arithmetic System ---");
        // UC13 logic is internal; behavior remains identical to UC12
        Quantity<LengthUnit> tenFt = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> twoFt = new Quantity<>(2.0, LengthUnit.FEET);
        
        System.out.println("Add: " + tenFt.add(twoFt));
        System.out.println("Subtract: " + tenFt.subtract(twoFt));
        System.out.println("Divide: " + tenFt.divide(twoFt));
    }
}

// LengthUnit Enum (same as UC12)
enum LengthUnit implements IMeasurable {
    FEET(12.0), INCH(1.0);
    private final double f;
    LengthUnit(double f) { this.f = f; }
    public double convertToBaseUnit(double v) { return v * f; }
    public double convertFromBaseUnit(double bv) { return bv / f; }
}