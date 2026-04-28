package QuantityMeasurementSystem.src.UC11_Volume_Scaling;

/**
 * UC11: Volume Measurement Scaling.
 * Proves the architecture scales to new categories without modifying core logic.
 */

interface IMeasurable {
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
}

// Step 1: Create the New VolumeUnit Enum
enum VolumeUnit implements IMeasurable {
    LITRE(1.0), 
    MILLILITRE(0.001), 
    GALLON(3.78541);

    private final double factor;
    VolumeUnit(double factor) { this.factor = factor; }

    public double convertToBaseUnit(double v) { return v * factor; }
    public double convertFromBaseUnit(double bv) { return bv / factor; }
}

// Reusing the Generic Quantity Class from UC10 (Unchanged)
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
        if (this.unit.getClass() != that.unit.getClass()) return false;
        
        return Math.abs(this.unit.convertToBaseUnit(this.value) - 
                        ((IMeasurable)that.unit).convertToBaseUnit(that.value)) < 0.001;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s", value, unit);
    }
}

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        System.out.println("--- UC11: Volume Category Integration ---");

        // 1. Equality: 1 Litre == 1000 Millilitres
        Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        System.out.println("1.0 L equals 1000.0 mL: " + litre.equals(ml));

        // 2. Conversion: 1 Gallon to Litres
        Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);
        System.out.println("1.0 Gallon to Litres: " + gallon.convertTo(VolumeUnit.LITRE));

        // 3. Addition: 1 Gallon + 3.785 Litres = 2 Gallons
        Quantity<VolumeUnit> resultInGallons = gallon.add(new Quantity<>(3.78541, VolumeUnit.LITRE), VolumeUnit.GALLON);
        System.out.println("1.0 Gal + 3.785 L in Gallons: " + resultInGallons);
        
        // 4. Verification of cross-category safety (Theoretical)
        // Quantity<LengthUnit> someLength = ...;
        // litre.equals(someLength); // Returns false safely
    }
}