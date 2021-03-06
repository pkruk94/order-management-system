package value_object;

import exception.MoneyException;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Getter
@Embeddable
public class Money {

    private BigDecimal value;

    public Money() {
        this.value = BigDecimal.ZERO;
    }

    public Money(String value) {
        init(value);
    }

    public Money(Money money) {
        this.value = money.value;
    }

    private Money(BigDecimal value) {
        this.value = value;
    }

    public Money add(Money money) {
        return new Money(this.value.add(money.value));
    }

    public Money subtract(Money money) {
        return new Money(this.value.subtract(money.value));
    }

    public Money multiply(Money money) {
        return new Money(this.value.multiply(money.value));
    }

    public Money multiply(int value) {
        return new Money(this.value.multiply(BigDecimal.valueOf(value)));
    }

    public Money withDiscount(PercentageValue discountValue) {
        var reversedDiscount = discountValue.getReversedValue().getDecimalValue();
        return new Money(this.value.multiply(reversedDiscount));
    }

    public Money multiplyByFraction(PercentageValue percentageValue) {
        return new Money(this.value.multiply(percentageValue.getDecimalValue()));
    }

    public int compare(Money againstMoney) {
        return this.value.compareTo(againstMoney.value);
    }

    private void init(String value) {
        if (value == null || !value.matches("(\\d+\\.)?\\d")) {
            throw new MoneyException("Money value is not correct");
        }
        this.value = new BigDecimal(value);
    }

}
