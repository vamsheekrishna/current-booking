package com.currentbooking.models;

/**
 * Created by Satya Seshu on 20/06/20.
 */
public class ConcessionTypeModel {

    private String personType;
    private String concessionType;
    private String concessionCode;

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getConcessionType() {
        return concessionType;
    }

    public void setConcessionType(String concessionType) {
        this.concessionType = concessionType;
    }

    public String getConcessionCode() {
        return concessionCode;
    }

    public void setConcessionCode(String concessionCode) {
        this.concessionCode = concessionCode;
    }
}
