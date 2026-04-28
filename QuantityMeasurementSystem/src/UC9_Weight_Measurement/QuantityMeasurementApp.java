package QuantityMeasurementSystem.src.UC9_Weight_Measurement;

// --- Weight Category ---

enum WeightUnit {
    KILOGRAM(1.0), 
    GRAM(0.001), 
    POUND(0.453592);

    private final double conversionFactor;

    WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double convertToBaseUnit(double value) {
        return value * this.conversionFactor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / this.conversionFactor;
    }
}

class QuantityWeight {
    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");
        this.value = value;
        this.unit = unit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        // Strict Type Checking: Prevents Weight vs Length comparison
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityWeight that = (QuantityWeight) obj;
        return Math.abs(this.unit.convertToBaseUnit(this.value) - 
                        that.unit.convertToBaseUnit(that.value)) < 0.001;
    }

    public QuantityWeight convertTo(WeightUnit targetUnit) {
        double baseValue = this.unit.convertToBaseUnit(this.value);
        return new QuantityWeight(targetUnit.convertFromBaseUnit(baseValue), targetUnit);
    }

    public QuantityWeight add(QuantityWeight that, WeightUnit targetUnit) {
        double sumInBase = this.unit.convertToBaseUnit(this.value) + 
                           that.unit.convertToBaseUnit(that.value);
        return new QuantityWeight(targetUnit.convertFromBaseUnit(sumInBase), targetUnit);
    }

    @Override
    public String toString() {
        return String.format("%.3f %s", value, unit);
    }
}

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        System.out.println("--- UC9: Weight Measurement System ---");

        // 1. Equality: 1kg == 1000g
        QuantityWeight oneKg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight thousandGrams = new QuantityWeight(1000.0, WeightUnit.GRAM);
        System.out.println("1.0 KG equals 1000.0 G: " + oneKg.equals(thousandGrams));

        // 2. Conversion: 1lb to grams
        QuantityWeight onePound = new QuantityWeight(1.0, WeightUnit.POUND);
        System.out.println("1.0 LB converted to G: " + onePound.convertTo(WeightUnit.GRAM));

        // 3. Addition: 1kg + 1000g = 2000g
        QuantityWeight sumInGrams = oneKg.add(thousandGrams, WeightUnit.GRAM);
        System.out.println("1.0 KG + 1000.0 G in Grams: " + sumInGrams);

        // 4. Category Safety Check
        // System.out.println(oneKg.equals(someLengthObject)); -> Returns false due to getClass() check
    }
}