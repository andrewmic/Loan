package lt.itswedbankacademy;

import lt.itswedbankacademy.domain.Loan;
import lt.itswedbankacademy.domain.LoanRiskType;
import lt.itswedbankacademy.initializers.Task1DomainInitializer;
import lt.itswedbankacademy.service.LoanService;

import java.math.BigDecimal;

public class ClientApp {

    public static void main(String[] args) {

        Loan[] loans = getInitializer().initializeLoans();

        LoanService service = new LoanService(loans);
        BigDecimal averageLoanCost;
        BigDecimal averageLoanCostRisk;
        averageLoanCost = service.calculateAverageLoanCost();
        averageLoanCostRisk = service.calcuateAverageLoanCoste(LoanRiskType.HIGH_RISK);
        service.calculateMaximumPriceOfNonExpiredLoans();
        System.out.println(service.getHighRiskLoans());
        System.out.println(service.getHighRiskLoans().size());
        System.out.println(averageLoanCost);
        System.out.println(averageLoanCostRisk);

        System.out.println(service.calculateAverageCostOfHighRiskLoans());
        System.out.println(service.calculateMaximumPriceOfNonExpiredLoans());


    }


    public static DomainInitializer getInitializer() {
        return new Task1DomainInitializer();
    }
}
