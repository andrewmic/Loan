package lt.itswedbankacademy.domain;

import java.util.Objects;

public class RealEstateLoan extends Loan {

    //"private" modifiers are missing
    String district;
    float area;

    public RealEstatePurpose getRealEstatePurpose() {
        return realEstatePurpose;
    }

    public void setPurpose(RealEstatePurpose realEstatePurpose) {
        this.realEstatePurpose = realEstatePurpose;
    }

    public void setRealEstatePurpose(RealEstatePurpose realEstatePurpose) {
        this.realEstatePurpose = realEstatePurpose;
    }

    private RealEstatePurpose realEstatePurpose;


    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RealEstateLoan)) return false;
        if (!super.equals(o)) return false;
        RealEstateLoan that = (RealEstateLoan) o;
        return Float.compare(that.area, area) == 0 &&
                Objects.equals(district, that.district) &&
                realEstatePurpose == that.realEstatePurpose;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), district, area, realEstatePurpose);
    }
}
