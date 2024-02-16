package bntu.accounting.application.util.normalization;

public class RounderValues {

    protected static Double normalizeItem(Double value){
        Double result;
        result = offNegative(value);
        result = roundValue(result);
        return result;
    }

    protected static Double roundValue(double value) {
        double result = Math.round(value * 100);
        result = result / 100;
        return result;
    }

    protected static Double offNegative(Double value){
        if (value < 0) return value * -1;
        return value;
    }
}
