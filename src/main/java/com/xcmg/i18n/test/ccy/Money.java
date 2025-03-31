package com.xcmg.i18n.test.ccy;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

/**
 * Money类用于表示不同币种的金额，支持高精度计算和多币种操作。
 * 该类是不可变的（immutable），并且实现了Comparable和Serializable接口。
 */
@Slf4j
public final class Money implements Comparable<Money>, Serializable {

    private static final long serialVersionUID = 1L;

    private final BigDecimal amount;
    private final Currency currency;

    /**
     * 私有构造函数，确保通过工厂方法创建实例。
     *
     * @param amount   金额，单位为元
     * @param currency 币种，非空
     */
    private Money(BigDecimal amount, Currency currency) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null.");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null.");
        }
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * 静态工厂方法，通过传入Currency和BigDecimal金额创建Money实例。
     * 默认使用RoundingMode.HALF_UP进行四舍五入。
     *
     * @param currency 币种
     * @param amount   金额，单位为元
     * @return 新的Money实例
     */
    public static Money of(Currency currency, BigDecimal amount) {
        return of(currency, amount, RoundingMode.HALF_UP);
    }

    /**
     * 静态工厂方法，通过传入Currency和BigDecimal金额创建Money实例。
     * 允许指定RoundingMode进行四舍五入。
     *
     * @param currency     币种
     * @param amount       金额，单位为元
     * @param roundingMode 四舍五入模式
     * @return 新的Money实例
     */
    public static Money of(Currency currency, BigDecimal amount, RoundingMode roundingMode) {
        Objects.requireNonNull(currency, "Currency cannot be null.");
        Objects.requireNonNull(amount, "Amount cannot be null.");
        Objects.requireNonNull(roundingMode, "RoundingMode cannot be null.");

        BigDecimal scaledAmount = amount.setScale(
                currency.getDefaultFractionDigits(),
                roundingMode
        );

        return new Money(scaledAmount, currency);
    }

    /**
     * 加法操作，返回新的Money实例。
     * 仅允许相同币种的加法操作。
     *
     * @param other 加数
     * @return 相加后的Money实例
     * @throws IllegalArgumentException 如果币种不一致
     */
    public Money add(Money other) {
        validateSameCurrency(other);
        BigDecimal resultAmount = this.amount.add(other.amount);
        return new Money(resultAmount, this.currency);
    }

    /**
     * 减法操作，返回新的Money实例。
     * 仅允许相同币种的减法操作。
     *
     * @param other 减数
     * @return 相减后的Money实例
     * @throws IllegalArgumentException 如果币种不一致
     */
    public Money subtract(Money other) {
        validateSameCurrency(other);
        BigDecimal resultAmount = this.amount.subtract(other.amount);
        return new Money(resultAmount, this.currency);
    }

    /**
     * 乘法操作，使用默认舍入模式（RoundingMode.HALF_UP），返回新的Money实例。
     *
     * @param multiplier 乘数
     * @return 乘法后的Money实例
     * @throws ArithmeticException      如果需要进行舍入但无法进行
     * @throws IllegalArgumentException 如果multiplier为null
     */
    public Money multiply(BigDecimal multiplier) {
        return multiply(multiplier, RoundingMode.HALF_UP);
    }

    /**
     * 乘法操作，返回新的Money实例。
     *
     * @param multiplier   乘数
     * @param roundingMode 四舍五入模式
     * @return 乘法后的Money实例
     * @throws ArithmeticException      如果需要进行舍入但没有指定舍入模式
     * @throws IllegalArgumentException 如果multiplier或roundingMode为null
     */
    public Money multiply(BigDecimal multiplier, RoundingMode roundingMode) {
        Objects.requireNonNull(multiplier, "Multiplier cannot be null.");
        Objects.requireNonNull(roundingMode, "RoundingMode cannot be null.");

        BigDecimal resultAmount = this.amount.multiply(multiplier)
                .setScale(currency.getDefaultFractionDigits(), roundingMode);
        return new Money(resultAmount, this.currency);
    }

    /**
     * 除法操作，返回新的Money实例。
     *
     * @param divisor      除数
     * @param scale        保留的小数位数
     * @param roundingMode 四舍五入模式
     * @return 除法后的Money实例
     * @throws ArithmeticException      如果除数为零或无法精确表示
     * @throws IllegalArgumentException 如果divisor或roundingMode为null
     */
    public Money divide(BigDecimal divisor, int scale, RoundingMode roundingMode) {
        Objects.requireNonNull(divisor, "Divisor cannot be null.");
        Objects.requireNonNull(roundingMode, "RoundingMode cannot be null.");
        if (divisor.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero.");
        }

        BigDecimal resultAmount = this.amount.divide(divisor, scale, roundingMode)
                .setScale(currency.getDefaultFractionDigits(), roundingMode);
        return new Money(resultAmount, this.currency);
    }

    /**
     * 比较大小，仅允许相同币种的比较。
     *
     * @param other 要比较的Money对象
     * @return 负数、零或正数，分别表示小于、等于或大于
     * @throws IllegalArgumentException 如果币种不一致
     */
    @Override
    public int compareTo(Money other) {
        validateSameCurrency(other);
        return this.amount.compareTo(other.amount);
    }

    /**
     * 获取金额，单位为元。
     *
     * @return 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 获取最小单位金额，通过Currency.getDefaultFractionDigits()和amount计算。
     * 例如，人民币1元 = 100分，日元1元 = 1元。
     *
     * @return 最小单位金额
     */
    public BigDecimal getAmountMinorUnit() {
        int fractionDigits = currency.getDefaultFractionDigits();
        return amount.movePointRight(fractionDigits);
    }

    /**
     * 获取币种。
     *
     * @return 币种
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * 校验两个Money对象的币种是否相同。
     *
     * @param other 另一个Money对象
     * @throws IllegalArgumentException 如果币种不一致
     */
    private void validateSameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currencies do not match.");
        }
    }

    /**
     * 重写equals方法，基于金额和币种判断相等。
     *
     * @param o 其他对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Money money = (Money) o;
        return amount.equals(money.amount) &&
                currency.equals(money.currency);
    }

    /**
     * 重写hashCode方法，基于金额和币种生成哈希码。
     *
     * @return 哈希码
     */
    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    /**
     * 重写toString方法，格式化输出币种和金额。
     *
     * @return 格式化后的字符串
     */
    @Override
    public String toString() {
        return String.format("%s %s", currency.getCurrencyCode(), amount);
    }

    public static void main(String[] args) {
        // java.math.RoundingMode：
        // UP：远零方向舍入。示例：1.666返回1.67，-1.666返回-1.67。
        // DOWN：向零方向舍入。示例：1.6返回1，-1.6返回-1。
        // CEILING：向上舍入。示例：1.6返回2，-1.6返回-1。
        // FLOOR：向下舍入。示例：1.6返回1，-1.6返回-2。
        // HALF_UP：四舍五入。示例：1.5返回2，-1.5返回-2。
        // HALF_DOWN：五舍六入。示例：1.5返回1，-1.5返回-1，1.6返回2，-1.6返回-2。
        // HALF_EVEN：银行家算法，尾数小于0.5舍，尾数大于0.5入，尾数等于0.5往最终结果是偶数的方向进。示例：1.51返回2，-1.49返回-1，2.5返回2，3.5返回4（1.5，2.5，3.5，4.5，5.5等这些最终只出现2，4，4，4，6等偶数）。
        Money money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(1.666), RoundingMode.UP);
        log.info("远零方向舍入 {}: {}", 1.666, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.666), RoundingMode.UP);
        log.info("远零方向舍入 {}: {}", -1.666, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.665), RoundingMode.UP);
        log.info("远零方向舍入 {}: {}", -1.665, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.664), RoundingMode.UP);
        log.info("远零方向舍入 {}: {}", -1.664, money.getAmount().doubleValue());
        log.info("--------------------------------------------------------------------------");
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(1.666), RoundingMode.DOWN);
        log.info("向零方向舍入 {}: {}", 1.666, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.666), RoundingMode.DOWN);
        log.info("向零方向舍入 {}: {}", -1.666, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.665), RoundingMode.DOWN);
        log.info("向零方向舍入 {}: {}", -1.665, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.664), RoundingMode.DOWN);
        log.info("向零方向舍入 {}: {}", -1.664, money.getAmount().doubleValue());
        log.info("--------------------------------------------------------------------------");
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(1.666), RoundingMode.CEILING);
        log.info("向上舍入 {}: {}", 1.666, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.666), RoundingMode.CEILING);
        log.info("向上舍入 {}: {}", -1.666, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.665), RoundingMode.CEILING);
        log.info("向上舍入 {}: {}", -1.665, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.664), RoundingMode.CEILING);
        log.info("向上舍入 {}: {}", -1.664, money.getAmount().doubleValue());
        log.info("--------------------------------------------------------------------------");
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(1.666), RoundingMode.FLOOR);
        log.info("向下舍入 {}: {}", 1.666, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.666), RoundingMode.FLOOR);
        log.info("向下舍入 {}: {}", -1.666, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.665), RoundingMode.FLOOR);
        log.info("向下舍入 {}: {}", -1.665, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.664), RoundingMode.FLOOR);
        log.info("向下舍入 {}: {}", -1.664, money.getAmount().doubleValue());
        log.info("--------------------------------------------------------------------------");
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(1.666), RoundingMode.HALF_UP);
        log.info("四舍五入 {}: {}", 1.666, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.666), RoundingMode.HALF_UP);
        log.info("四舍五入 {}: {}", -1.666, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.665), RoundingMode.HALF_UP);
        log.info("四舍五入 {}: {}", -1.665, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.664), RoundingMode.HALF_UP);
        log.info("四舍五入 {}: {}", -1.664, money.getAmount().doubleValue());
        log.info("--------------------------------------------------------------------------");
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(1.666), RoundingMode.HALF_DOWN);
        log.info("五舍六入 {}: {}", 1.666, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.666), RoundingMode.HALF_DOWN);
        log.info("五舍六入 {}: {}", -1.666, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.665), RoundingMode.HALF_DOWN);
        log.info("五舍六入 {}: {}", -1.665, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.664), RoundingMode.HALF_DOWN);
        log.info("五舍六入 {}: {}", -1.664, money.getAmount().doubleValue());
        log.info("--------------------------------------------------------------------------");
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(1.666), RoundingMode.HALF_EVEN);
        log.info("银行家算法 {}: {}", 1.666, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.666), RoundingMode.HALF_EVEN);
        log.info("银行家算法 {}: {}", -1.666, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.665), RoundingMode.HALF_EVEN);
        log.info("银行家算法 {}: {}", -1.665, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(-1.664), RoundingMode.HALF_EVEN);
        log.info("银行家算法 {}: {}", -1.664, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(1.665), RoundingMode.HALF_EVEN);
        log.info("银行家算法 {}: {}", 1.635, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(1.625), RoundingMode.HALF_EVEN);
        log.info("银行家算法 {}: {}", 1.625, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(1.635), RoundingMode.HALF_EVEN);
        log.info("银行家算法 {}: {}", 1.635, money.getAmount().doubleValue());
        money = Money.of(Currency.getInstance(Locale.CHINA), BigDecimal.valueOf(1.645), RoundingMode.HALF_EVEN);
        log.info("银行家算法 {}: {}", 1.645, money.getAmount().doubleValue());

        log.info("toString 1.64 转换为最小单位 {}", money.getAmountMinorUnit().toString());
        log.info("toPlainString 1.64 转换为最小单位 {}", money.getAmountMinorUnit().toPlainString());
        log.info("toEngineeringString 1.64 转换为最小单位 {}", money.getAmountMinorUnit().toEngineeringString());
        log.info("toBigInteger 1.64 转换为最小单位 {}", money.getAmountMinorUnit().toBigInteger());
        log.info("toBigIntegerExact 1.64 转换为最小单位 {}", money.getAmountMinorUnit().toBigIntegerExact());
        log.info("doubleValue 1.64 转换为最小单位 {}", money.getAmountMinorUnit().doubleValue());
    }

}
