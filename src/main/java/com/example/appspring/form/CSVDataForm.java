package com.example.appspring.form;

public class CSVDataForm {
    private String mccCode;
    private String trType;
    private String codeTrType;

    public String getMcc_code(){
        return mccCode;
    }

    public String setMcc_code(String mccCode){
        this.mccCode = mccCode;
        return mccCode;
    }

    public String getTrType(){
        return trType;
    }

    public String setTrType(String trType){
        this.trType = trType;
        return trType;
    }
    public String getCodeTrType(){
        return codeTrType;
    }

    public String setCodeTrType(String codeTrType){
        this.codeTrType = codeTrType;
        return codeTrType;
    }
}
