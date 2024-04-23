package sml;


import java.util.Objects;

public class Flags {
    private boolean zero, sign;

    /**
     * Gets the value of the zero flag (ZF).
     *
     * @return true if the zero flag is set, false otherwise
     */
    public boolean getZF() {
        return zero;
    }

    /**
     * Sets the value of the zero flag (ZF).
     *
     * @param zero the value to set for the zero flag
     */
    public void setZF(boolean zero) {
        this.zero = zero;
    }

    /**
     * Gets the value of the sign flag (SF).
     *
     * @return true if the sign flag is set, false otherwise
     */
    public boolean getSF() {
        return sign;
    }

    /**
     * Sets the value of the sign flag (SF).
     *
     * @param sign the value to set for the sign flag
     */
    public void setSF(boolean sign) {
        this.sign = sign;
    }

    /**
     * Returns a string representation of the Flags object.
     *
     * @return a string representation containing the values of the zero flag (ZF) and sign flag (SF)
     */
    @Override
    public String toString() {
        return "Flags{ ZF=" + this.getZF() + ", SF=" + this.getSF() + " }";
    }

    /**
     * Checks whether this Flags object is equal to another object.
     * Two Flags objects are considered equal if they have the same values for the zero flag (ZF) and sign flag (SF).
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Flags flags = (Flags) obj;
        return zero == flags.zero && sign == flags.sign;
    }

    /**
     * Returns a hash code value for the Flags object.
     * The hash code is computed based on the values of the zero flag (ZF) and sign flag (SF).
     *
     * @return a hash code value for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(zero, sign);
    }
}
