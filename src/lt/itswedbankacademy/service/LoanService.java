package lt.itswedbankacademy.service;

import lt.itswedbankacademy.LoanServiceInterface;
import lt.itswedbankacademy.domain.*;
import lt.itswedbankacademy.util.DateUtil;
import lt.itswedbankacademy.util.LoanUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class LoanService implements LoanServiceInterface {

    // List<Loan> loans = new ArrayList<>();
    LoanIterable loans;

    public LoanService(Loan[] loans) {
        this.loans = new LoanIterable(loans);
    }

    @Override
    public List<Loan> getHighRiskLoans() {

        List<Loan> highRiskLoans = new ArrayList<>();


        for (Loan loan : loans) {
            //For enums, it is more optimal to use "==" instead of "equals"
            if (loan.getRiskType().equals(LoanRiskType.HIGH_RISK)) {
                highRiskLoans.add(loan);

            }
        }

        return highRiskLoans;
    }

    @Override
    public BigDecimal getAverageDepreciation() {
        BigDecimal averageDepreciation = BigDecimal.ZERO;
        for (Loan loan : loans) {
            if (loan instanceof VehicleLoan) {
                averageDepreciation = averageDepreciation.add(LoanUtil.calculateVehicleDepreciation((VehicleLoan) loan));
            }
        }
        return averageDepreciation.divide(new BigDecimal(loans.size()), 4, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateAverageLoanCost() {

        BigDecimal averageLoanCost = BigDecimal.ZERO;

        for (Loan loan : loans) {
            //You already have a method for calculating Loan total cost:
            //averageLoanCost = averageLoanCost.add(loan.calculateTotalCost());
            averageLoanCost = averageLoanCost.add(loan.getPrice().multiply(loan.getInterestRate().divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP))).add(loan.getPrice());

        }

//        for (int i = 0; i < loans.size() ; i++) {
//
//            averageLoanCost = averageLoanCost.add(loans.get(i).calculateInterest());
//        }

        averageLoanCost = averageLoanCost.divide(new BigDecimal(loans.size()));


        return averageLoanCost;
    }

    @Override
    public BigDecimal calcuateAverageLoanCoste(LoanRiskType loanRiskType) {

        BigDecimal averageLoanCost = BigDecimal.ZERO;
        int count = 0;

        for (Loan loan : loans) {
            if (loan.getRiskType().equals(loanRiskType)) {
                //You already have a method for calculating Loan total cost:
                //averageLoanCost = averageLoanCost.add(loan.calculateTotalCost());
                averageLoanCost = averageLoanCost.add(loan.getPrice().multiply((loan.getInterestRate().multiply(newInterestRate(loanRiskType))).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP))).add(loan.getPrice());
                count++;
            }
        }

        averageLoanCost = averageLoanCost.divide(new BigDecimal(count), RoundingMode.HALF_EVEN);

        return averageLoanCost;
    }

    @Override
    public BigDecimal calculateAverageCostOfHighRiskLoans() {
        //You already have a method which calculates average loan cost by risk, so use it:
        //return calcuateAverageLoanCoste(LoanRiskType.HIGH_RISK);

        BigDecimal averageLoanCost = BigDecimal.ZERO;
        int count = 0;

        for (Loan loan : loans) {
            if (loan.getRiskType().equals(LoanRiskType.HIGH_RISK)) {
                averageLoanCost = averageLoanCost.add(loan.getPrice().multiply(loan.getInterestRate().divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP))).add(loan.getPrice());
                count++;
            }
        }

//        for (int i = 0; i < loans.size()  ; i++) {
//
//            if (loans.get(i).getRiskType().equals(LoanRiskType.HIGH_RISK)) {
//                averageLoanCost = averageLoanCost.add(loans.get(i).calculateInterest());
//                count++;
//            }
//        }

        averageLoanCost = averageLoanCost.divide(new BigDecimal(count), RoundingMode.HALF_UP);

        return averageLoanCost;
    }

    @Override
    public BigDecimal calculateMaximumPriceOfNonExpiredLoans() {

        BigDecimal max = BigDecimal.ZERO;
        for (Loan loan : loans) {
            //This check should be implemented in "Loan.isValid()"
            if (DateUtil.differenceInDays(new Date(), DateUtil.addYears(loan.getCreationDate(), loan.getTermInYears())) < 0) {
                if (max.compareTo(loan.getPrice()) < 0) {
                    max = loan.getPrice();
                }
            }
        }
        return max;
    }

    public Map<LoanRiskType, List<Loan>> groupLoansByRiskType() {

        //Wow, wow, wow! This is way too complicated than it needs to be :)
        //Plus, it will not work correctly if a new "LoanRiskType" would be introduced.

        Map<LoanRiskType, List<Loan>> loansByRiskType = new HashMap<>();
        //   Map<LoanRiskType, List<Loan>> loansByRiskType = new HashMap<>();

        //This is a correct implementation. Don't know why it is commented though
        // for(Loan loan : loans){
        //      if(!loansByRiskType.containsKey(loan.getRiskType())){
        //           loansByRiskType.put(loan.getRiskType(), new ArrayList<>());
        //       }
        //       loansByRiskType.get(loan.getRiskType()).add(loan);
        //    }

        List<Loan> highRisk = new ArrayList();
        List<Loan> normalRisk = new ArrayList();
        List<Loan> lowRisk = new ArrayList();

        for (Loan loan : loans) {
            if (LoanRiskType.HIGH_RISK.equals(loan.getRiskType())) {
                highRisk.add(loan);
                loansByRiskType.put(LoanRiskType.HIGH_RISK, highRisk);
            } else if (LoanRiskType.NORMAL_RISK.equals(loan.getRiskType())) {
                normalRisk.add(loan);
                loansByRiskType.put(LoanRiskType.NORMAL_RISK, normalRisk);
            } else if (LoanRiskType.LOW_RISK.equals(loan.getRiskType())) {
                lowRisk.add(loan);
                loansByRiskType.put(LoanRiskType.LOW_RISK, lowRisk);
            }
        }

//        for (int i = 0; i < loans.size(); i++) {
//
//            if(LoanRiskType.HIGH_RISK.equals(loans.get(i).getRiskType())) {
//                highRisk.add(loans.get(i));
//                loansByRiskType.put(LoanRiskType.HIGH_RISK, highRisk);
//            }
//            else if(LoanRiskType.NORMAL_RISK.equals(loans.get(i).getRiskType())) {
//                normalRisk.add(loans.get(i));
//                loansByRiskType.put(LoanRiskType.NORMAL_RISK, normalRisk);
//            }
//            else if(LoanRiskType.LOW_RISK.equals(loans.get(i).getRiskType())) {
//                lowRisk.add(loans.get(i));
//                loansByRiskType.put(LoanRiskType.LOW_RISK, lowRisk);
//            }
//        }

        return loansByRiskType;
    }

    public Set<String> findVehicleModels() {

        //You don't need to use "TreeSet" here. "HashSet" would do find.
        Set<String> numbers = new TreeSet<>();

        for (Loan loan : loans) {

            numbers.add(((VehicleLoan) loan).getModel());
        }

        return numbers;
    }


    //I think this should be implemented in "VehicleLoan.setInterestRate(...)"
    public BigDecimal newInterestRate(LoanRiskType loanRiskType) {

        BigDecimal newInterestRate = BigDecimal.ZERO;

        if (LoanRiskType.HIGH_RISK.equals(loanRiskType)) {
            newInterestRate = (new BigDecimal(1.5));
        }
        if (LoanRiskType.LOW_RISK.equals(loanRiskType))
            newInterestRate = (new BigDecimal(0.8));

        return newInterestRate;
    }

    //////-----
    @Override
    //I don't remember this to be in assignment :)
    public BigDecimal calculateAverageCostOfPreferredRiskLoans(LoanRiskType loanRiskType) {

        BigDecimal finalLoan;
        BigDecimal interestRate;
        BigDecimal loanSum = BigDecimal.ZERO;
        BigDecimal divider = BigDecimal.ZERO;
        for (Loan loan : loans) {
            if (loanRiskType.equals(loan.getRiskType())) {
                interestRate = loan.getInterestRate().divide(new BigDecimal("100"));
                finalLoan = (loan.getPrice().multiply(interestRate)).add(loan.getPrice());

                loanSum = loanSum.add(finalLoan);
                divider = divider.add(BigDecimal.ONE);
            }
        }
        return loanSum.divide(divider);
    }

    @Override
    public Collection<Loan> getLoansOfHigherThanAverageDepreciation() {
        Collection<Loan> loansOfHigherThanAverageDepreciation = new ArrayList<>();
        BigDecimal averageDepreciation = getAverageDepreciation();
        for (Loan loan : loans) {

            if ((loan instanceof VehicleLoan) &&
                    (LoanUtil.calculateVehicleDepreciation((VehicleLoan) loan).compareTo(averageDepreciation)) > 0) {
                loansOfHigherThanAverageDepreciation.add(loan);
            }
        }
        return loansOfHigherThanAverageDepreciation;
    }

    @Override
    public Collection<Loan> getExpiredLandLoansInReservation() {
        Collection<Loan> expiredLandLoansInReservation = new ArrayList<>();
        for (Loan loan : loans) {
            validateDate(loan);
            //Correct, "loan.isValid()" should be used here
            if ((loan instanceof LandLoan) && !loan.isValid() && ((LandLoan) loan).isInReservation()) {
                expiredLandLoansInReservation.add(loan);
            }
        }
        return expiredLandLoansInReservation;
    }

    @Override
    public Collection<Loan> getLowRiskHarvesterLoans() {
        Collection<Loan> lowRiskHarvesterLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if ((loan instanceof HarvesterLoan) && (loan.getRiskType().equals(LoanRiskType.LOW_RISK))) {
                lowRiskHarvesterLoans.add(loan);
            }
        }
        return lowRiskHarvesterLoans;
    }

    public void validateDate(Loan loan) {
        Date dateAfter = DateUtil.addYears(loan.getCreationDate(), loan.getTermInYears());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        //You don't need to convert "new Date()" to String and then turn it back into Date. Pass "new Date()" directly:
        // DateUtil.differenceInDays(dateAfter, new Date())
        Date todayDate = DateUtil.getDateFromString(df.format(new Date()));
        loan.setValid(DateUtil.differenceInDays(dateAfter, todayDate) >= 0);
    }

    @Override
    public List<Loan> getExpiredHighRiskVehicleLoansOfHighestDuration() {
        int highestDuration = -1;
        for (Loan loan : loans) {
            //I think this line should not be here:
            loan.isValid();
            if ((loan instanceof VehicleLoan) && !loan.isValid() && (loan.getTermInYears() > highestDuration)) {
                highestDuration = loan.getTermInYears();
            }
        }

        List<Loan> expiredHighRiskVehicleLoansOfHighestDuration = new ArrayList<>();
        for (Loan loan : loans) {
            validateDate(loan);
            if ((loan instanceof VehicleLoan) && !loan.isValid() && (loan.getTermInYears() == highestDuration)) {
                expiredHighRiskVehicleLoansOfHighestDuration.add(loan);
            }
        }
        return expiredHighRiskVehicleLoansOfHighestDuration;
    }

    @Override
    public Collection<Loan> getPersonalRealEstateLoans() {
        Collection<Loan> personalRealEstateLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if ((loan instanceof RealEstateLoan) && (((RealEstateLoan) loan).getRealEstatePurpose().equals(RealEstatePurpose.PERSONAL))) {
                personalRealEstateLoans.add(loan);
            }
        }
        return personalRealEstateLoans;
    }

    @Override
    public Collection<Loan> getNormalRiskVehicleLoans() {
        Collection<Loan> normalRiskVehicleLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan instanceof VehicleLoan && loan.getRiskType().equals(LoanRiskType.NORMAL_RISK)) {
                normalRiskVehicleLoans.add(loan);
            }
        }
        return normalRiskVehicleLoans;
    }

    @Override
    public int getMaximumAgeOfLowRiskLoanedVehicles() {
        int max = 0;
        for (Loan loan : loans) {
            if (loan instanceof VehicleLoan && loan.getRiskType().equals(LoanRiskType.LOW_RISK)) {
                //I think this line should not be here:
                ((VehicleLoan) loan).getAge();
                if (max < ((VehicleLoan) loan).getAge()) {
                    max = ((VehicleLoan) loan).getAge();
                }
            }
        }
        return max;
    }

    //-----
    public Set<Loan> prioritizeLoans() {
        Set<Loan> prioritizeLoans = new TreeSet<>(new LoanComparator());
        prioritizeLoans.addAll(Arrays.asList(loans.getLoans()));
        return prioritizeLoans;
    }

    //I would recommend extracting this class into a separate file
    private class LoanComparator implements Comparator<Loan> {

        @Override

        public int compare(Loan o1, Loan o2) {

            //This is better (see comments in "CarLoan.compareTo(...)")
            int compareResult = returnLoanRiskType(o1.getRiskType()).compareTo(returnLoanRiskType(o2.getRiskType()));

            if (compareResult != 0) {

                return compareResult;

            }
            compareResult = o2.calculateTotalCost().compareTo(o1.calculateTotalCost());

            if (compareResult != 0) {
                return compareResult;

            }
            compareResult = o1.getCreationDate().compareTo(o2.getCreationDate());
            return compareResult;

        }

        //This an acceptable implementation. You can find a better one here (if interested) -
        // https://github.com/andrewmic/enum-comparator-example/blob/master/src/main/java/lt/itacademy/example/comparator/SwitchlessMappedValueBasedLoanRiskComparator.java
        private Integer returnLoanRiskType(LoanRiskType loanRiskType) {

            switch (loanRiskType) {
                case HIGH_RISK:
                    return 1;
                case NORMAL_RISK:
                    return 2;
                case LOW_RISK:

                    return 3;
            }
            return 0;
        }
    }

}
