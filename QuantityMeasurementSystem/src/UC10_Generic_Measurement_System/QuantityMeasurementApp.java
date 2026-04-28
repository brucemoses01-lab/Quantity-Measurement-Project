package QuantityMeasurementSystem.src.UC10_Generic_Measurement_System;

/**
 * UC10: Generic Quantity Class with Unit Interface.
 * Adheres to SOLID principles: Single Responsibility, Open-Closed, and DRY.
 */

// Step 1: Define the Interface Contract
interface IMeasurable {
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
}

// Step 2 & 3: Refactor Enums to implement the Interface
enum LengthUnit implements IMeasurable {
    FEET(12.0), INCH(1.0), YARDS(36.0), CENTIMETERS(0.393701);
    private final double factor;
    LengthUnit(double factor) { this.factor = factor; }
    public double convertToBaseUnit(double v) { return v * factor; }
    public double convertFromBaseUnit(double bv) { return bv / factor; }
}

enum WeightUnit implements IMeasurable {
    KILOGRAM(1.0), GRAM(0.001), POUND(0.453592);
    private final double factor;
    WeightUnit(double factor) { this.factor = factor; }
    public double convertToBaseUnit(double v) { return v * factor; }
    public double convertFromBaseUnit(double bv) { return bv / factor; }
}

// Step 4: Generic Quantity Class
class Quantity<U extends IMeasurable> {
    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException("Unit is null");
        this.value = value;
        this.unit = unit;
    }

    public Quantity<U> convertTo(U targetUnit) {
        double base = this.unit.convertToBaseUnit(this.value);
        return new Quantity<>(targetUnit.convertFromBaseUnit(base), targetUnit);
    }

    public Quantity<U> add(Quantity<U> that, U targetUnit) {
        double sumBase = this.unit.convertToBaseUnit(this.value) + 
                         that.unit.convertToBaseUnit(that.value);
        return new Quantity<>(targetUnit.convertFromBaseUnit(sumBase), targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quantity<?> that = (Quantity<?>) obj;
        
        // Category Safety: Prevent comparing Length to Weight
        if (this.unit.getClass() != that.unit.getClass()) return false;
        
        return Math.abs(this.unit.convertToBaseUnit(this.value) - 
                        ((IMeasurable)that.unit).convertToBaseUnit(that.value)) < 0.001;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, unit);
    }
}

// Step 5: Simplified App Class
public class QuantityMeasurementApp {
    
    // Generic demonstration method handles ANY category
    public static <U extends IMeasurable> void demonstrate(Quantity<U> q1, Quantity<U> q2, String label) {
        System.out.println(label + " Equality: " + q1.equals(q2));
    }

    public static void main(String[] args) {
        System.out.println("--- UC10: Final Generic Architecture ---");

        // Length Example
        Quantity<LengthUnit> ft = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> in = new Quantity<>(12.0, LengthUnit.INCH);
        demonstrate(ft, in, "Length (1ft vs 12in)");

        // Weight Example
        Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> g = new Quantity<>(1000.0, WeightUnit.GRAM);
        demonstrate(kg, g, "Weight (1kg vs 1000g)");
        
        // Addition Example
        System.out.println("Sum: " + kg.add(g, WeightUnit.KILOGRAM));
    }
}