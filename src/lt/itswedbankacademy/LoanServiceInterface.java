package lt.itswedbankacademy;

import lt.itswedbankacademy.domain.Loan;
import lt.itswedbankacademy.domain.LoanRiskType;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface LoanServiceInterface {

    Collection<Loan> getLoansOfHigherThanAverageDepreciation();

    Collection<Loan> getExpiredLandLoansInReservation();

    Collection<Loan> getLowRiskHarvesterLoans();

    List<Loan> getExpiredHighRiskVehicleLoansOfHighestDuration();

    Collection<Loan> getPersonalRealEstateLoans();

    Collection<Loan> getNormalRiskVehicleLoans();

    int getMaximumAgeOfLowRiskLoanedVehicles();

    Collection<Loan> getHighRiskLoans();

    BigDecimal calculateAverageLoanCost();

    BigDecimal calculateAverageCostOfPreferredRiskLoans(LoanRiskType loanRiskType);

    BigDecimal calcuateAverageLoanCoste(LoanRiskType loanRiskType);

    BigDecimal calculateMaximumPriceOfNonExpiredLoans();

    BigDecimal getAverageDepreciation();

    BigDecimal calculateAverageCostOfHighRiskLoans();
}
