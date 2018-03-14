package lt.itswedbankacademy.domain;

import java.util.Iterator;

public class LoanIterable implements Iterable<Loan> {


    private Loan[] loans;

    public LoanIterable(Loan[] loans) {
        this.loans = loans;
    }

    public int size() {
        return loans.length;
    }

    @Override
    public Iterator<Loan> iterator() {
        return new Iterator<Loan>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < loans.length;

            }

            @Override
            public Loan next() {
                return loans[index++];

            }
        };
    }


    public Loan[] getLoans() {
        return loans;
    }

    public void setLoans(Loan[] loans) {
        this.loans = loans;
    }
}
