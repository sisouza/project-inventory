package br.com.alura.inventory.database.converter;

import java.math.BigDecimal;

import androidx.room.TypeConverter;

public class BigDecimalConverter {

    @TypeConverter
    public Double convertToDouble(BigDecimal value) {
        return value.doubleValue();
    }

    @TypeConverter
    public BigDecimal convertToBigDecimal(Double value) {
        if (value != null) {
            return new BigDecimal(value);
        }
        return BigDecimal.ZERO;
    }

}
