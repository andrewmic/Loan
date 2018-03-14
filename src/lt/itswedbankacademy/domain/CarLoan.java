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
    }

}
