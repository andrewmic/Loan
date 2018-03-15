package lt.itswedbankacademy.util;

import lt.itswedbankacademy.domain.VehicleLoan;

import java.math.BigDecimal;
import java.util.Date;


public class LoanUtil {
    public static BigDecimal calculateVehicleDepreciation(VehicleLoan loan) {

        //You can return calculation straight away, you don't need an additional local variable here
        BigDecimal vehicleDepreciation;

        vehicleDepreciation = loan.getPrice().multiply(
                new BigDecimal((int) DateUtil.differenceInDays(new Date(), loan.getManufactured()) / 365 / loan.getMaximumAge()));

        return vehicleDepreciation;
    }
}
