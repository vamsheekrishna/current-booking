package com.currentbooking.utilits.cb_api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ConcessionListResponse extends BaseResponse{
    @SerializedName("data")
    @Expose
    ConcessionList concessionList;

    public ConcessionList getConcessionList() {
        return concessionList;
    }

    public void setConcessionList(ConcessionList concessionList) {
        this.concessionList = concessionList;
    }

    public class ConcessionList {
        @SerializedName("result")
        @Expose
        ArrayList<Concession> concessions;

        public ArrayList<Concession> getConcessions() {
            return concessions;
        }

        public void setConcessions(ArrayList<Concession> concessions) {
            this.concessions = concessions;
        }
    }
}
