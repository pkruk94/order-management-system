package value_object;

import exception.DiscountException;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Discount {

    private BigDecimal value;

    public Discount() {
        this.value = BigDecimal.ZERO;
    }

    public Discount(String value) {
        init(value);
    }

    private Discount(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getDecimalValue() { return  value; }

    public Discount getReversedValue() { return new Discount(BigDecimal.ONE.subtract(value)); }

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
