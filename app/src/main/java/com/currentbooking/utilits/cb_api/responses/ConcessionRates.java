package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConcessionRates {
    @SerializedName("concession_cd")
    @Expose
    String concessionCD;
    @SerializedName("bus_type_cd")
    @Expose
    String busTypeCD;
    @SerializedName("effective_from")
    @Expose
    String effectiveFrom;
    @SerializedName("effective_till")
    @Expose
    String effectiveTill;
    @SerializedName("concession_rate")
    @Expose
    String concessionRate;
    @SerializedName("is_home_state_only")
    @Expose
    String isHomeStateOnly;
    @SerializedName("is_audit_trail")
    @Expose
    String isSuditTrail;
    @SerializedName("remburisement")
    @Expose
    String remburisement;
    @SerializedName("is_reservation_charge_applicable")
    @Expose
    String isReservationChargeApplicable;
    @SerializedName("is_asn_applicable")
    @Expose
    String isAsnApplicable;
    @SerializedName("concession_service_charge_disable_enable")
    @Expose
    String concessionServiceChargeDisableEnable;
    @SerializedName("zero_amount_ticket_allow")
    @Expose
    String zeroAmountTicketAllow;
    @SerializedName("tripwise_km_allowed")
    @Expose
    String tripWiseKMAllowed;
    @SerializedName("per_trip_km_limit")
    @Expose
    String perTripKMLimit;
    @SerializedName("concession_registration_depot_or_division")
    @Expose
    String concessionRegistrationDepotORDivision;

    public String getConcessionCD() {
        return concessionCD;
    }

    public void setConcessionCD(String concessionCD) {
        this.concessionCD = concessionCD;
    }

    public String getBusTypeCD() {
        return busTypeCD;
    }

    public void setBusTypeCD(String busTypeCD) {
        this.busTypeCD = busTypeCD;
    }

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public String getEffectiveTill() {
        return effectiveTill;
    }

    public void setEffectiveTill(String effectiveTill) {
        this.effectiveTill = effectiveTill;
    }

    public String getConcessionRate() {
        return concessionRate;
    }

    public void setConcessionRate(String concessionRate) {
        this.concessionRate = concessionRate;
    }

    public String getIsHomeStateOnly() {
        return isHomeStateOnly;
    }

    public void setIsHomeStateOnly(String isHomeStateOnly) {
        this.isHomeStateOnly = isHomeStateOnly;
    }

    public String getIsSuditTrail() {
        return isSuditTrail;
    }

    public void setIsSuditTrail(String isSuditTrail) {
        this.isSuditTrail = isSuditTrail;
    }

    public String getRemburisement() {
        return remburisement;
    }

    public void setRemburisement(String remburisement) {
        this.remburisement = remburisement;
    }

    public String getIsReservationChargeApplicable() {
        return isReservationChargeApplicable;
    }

    public void setIsReservationChargeApplicable(String isReservationChargeApplicable) {
        this.isReservationChargeApplicable = isReservationChargeApplicable;
    }

    public String getIsAsnApplicable() {
        return isAsnApplicable;
    }

    public void setIsAsnApplicable(String isAsnApplicable) {
        this.isAsnApplicable = isAsnApplicable;
    }

    public String getConcessionServiceChargeDisableEnable() {
        return concessionServiceChargeDisableEnable;
    }

    public void setConcessionServiceChargeDisableEnable(String concessionServiceChargeDisableEnable) {
        this.concessionServiceChargeDisableEnable = concessionServiceChargeDisableEnable;
    }

    public String getZeroAmountTicketAllow() {
        return zeroAmountTicketAllow;
    }

    public void setZeroAmountTicketAllow(String zeroAmountTicketAllow) {
        this.zeroAmountTicketAllow = zeroAmountTicketAllow;
    }

    public String getTripWiseKMAllowed() {
        return tripWiseKMAllowed;
    }

    public void setTripWiseKMAllowed(String tripWiseKMAllowed) {
        this.tripWiseKMAllowed = tripWiseKMAllowed;
    }

    public String getPerTripKMLimit() {
        return perTripKMLimit;
    }

    public void setPerTripKMLimit(String perTripKMLimit) {
        this.perTripKMLimit = perTripKMLimit;
    }

    public String getConcessionRegistrationDepotORDivision() {
        return concessionRegistrationDepotORDivision;
    }

    public void setConcessionRegistrationDepotORDivision(String concessionRegistrationDepotORDivision) {
        this.concessionRegistrationDepotORDivision = concessionRegistrationDepotORDivision;
    }
}
