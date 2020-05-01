package value_object;

import exception.DiscountException;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Embeddable
public class PercentageValue {

    private BigDecimal value;

    public PercentageValue() {
        this.value = BigDecimal.ZERO;
    }

    public PercentageValue(String value) {
        init(value);
    }

    private PercentageValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getDecimalValue() { return  value; }

    public PercentageValue getReversedValue() { return new PercentageValue(BigDecimal.ONE.subtract(value)); }

    private void init(String value) {
        if (Objects.isNull(value) && !value.matches("\\d\\.\\d+")) {
            throw new DiscountException("Discount value is not correct");
        }

        var decimalValue = new BigDecimal(value);

        if (decimalValue.compareTo(BigDecimal.ZERO) < 0 || decimalValue.compareTo(BigDecimal.ONE) > 0) {
            throw new DiscountException("Value out of range");
        }

        this.value = decimalValue;
    }

}
