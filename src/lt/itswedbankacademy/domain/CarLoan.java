package lt.itswedbankacademy.domain;

public class CarLoan extends VehicleLoan implements Comparable<CarLoan> {

    float enginePower;


    public float getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(float enginePower) {
        this.enginePower = enginePower;
    }


    @Override
    public int compareTo(CarLoan o) {

        //First of all, why are you comparing "price" 3 times?
        //Second, why 2 of these times you are comparing "this" to "that" (ascending) and third (final) time "that" to "this"
        // descending (correct order)? :) Good approach would be to compare once and re-use saved result later on
        // (see example below).
        //Third, do not use "this" keyword if not necessary (Java is not TypeScript). Here is a good explanation when to use "this"
        // https://stackoverflow.com/questions/2411270/when-should-i-use-this-in-a-class
        int comp = getPrice().compareTo(o.getPrice());
        if (this.getPrice().compareTo(o.getPrice()) == 0) {

            if (comp == 0) {
                if (this.enginePower == o.enginePower) {
                    return this.getInterestRate().compareTo(o.getInterestRate());

                } else {
                    return -Float.compare(this.enginePower, o.enginePower);
                }
            }
        }
        return -(this.getPrice().compareTo(o.getPrice()));

/*        int compareResult = -getPrice().compareTo(o.getPrice());
        if (compareResult != 0) {
            return compareResult;
        }

        compareResult = -Float.compare(enginePower, o.enginePower);

        return compareResult == 0 ? getInterestRate().compareTo(o.getInterestRate()) : compareResult;*/
    }

}
