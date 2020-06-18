package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ConcessionRatesListResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    ConcessionRatesList concessionRatesList;

    public ConcessionRatesList getConcessionRatesList() {
        return concessionRatesList;
    }

    public void setConcessionRatesList(ConcessionRatesList concessionRatesList) {
        this.concessionRatesList = concessionRatesList;
    }

    public class ConcessionRatesList {
        @SerializedName("result")
        @Expose
        ArrayList<ConcessionRates> concessionRates;

        public ArrayList<ConcessionRates> getConcessionRates() {
            return concessionRates;
        }

        public void setConcessionRates(ArrayList<ConcessionRates> concessionRates) {
            this.concessionRates = concessionRates;
        }
    }
}
