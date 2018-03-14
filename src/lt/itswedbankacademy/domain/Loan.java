package lt.itswedbankacademy.domain;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Loan {

    private int termInYears;
    private BigDecimal price;
    private BigDecimal InterestRate;
    private String name;
    private Date creationDate;
    private LoanRiskType riskType;
    private boolean isValid;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public int getTermInYears() {
        return termInYears;
    }

    public void setTermInYears(int termInYears) {
        this.termInYears = termInYears;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public BigDecimal getInterestRate() {
        return InterestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        InterestRate = interestRate;
    }

    public BigDecimal getPrice() {

        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LoanRiskType getRiskType() {
        return riskType;
    }

    public BigDecimal calculateTotalCost() {

        BigDecimal sum = price.multiply(InterestRate.divide(new BigDecimal(100)));

        return sum.add(price);

    }

    public void setRiskType(LoanRiskType loanRiskType) {
        this.riskType = loanRiskType;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "termInYears=" + termInYears +
                ", price=" + price +
                ", InterestRate=" + InterestRate +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", loanRiskType=" + riskType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loan)) return false;
        Loan loan = (Loan) o;
        return termInYears == loan.termInYears &&
                Objects.equals(price, loan.price) &&
                Objects.equals(InterestRate, loan.InterestRate) &&
                Objects.equals(name, loan.name) &&
                Objects.equals(creationDate, loan.creationDate) &&
                riskType == loan.riskType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(termInYears, price, InterestRate, name, creationDate, riskType);
    }


}
