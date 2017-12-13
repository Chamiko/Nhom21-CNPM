package com.example.banhnhandau.mycooking.type;

/**
 * Created by BanhNhanDau on 11/04/2017.
 */

public class Type {
    private int idType;
    private String nameType;
    private byte[] imgType;

    public Type(int idType, String nameType, byte[] imgType) {
        this.idType = idType;
        this.nameType = nameType;
        this.imgType = imgType;
    }

    public Type() {
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public byte[] getImgType() {
        return imgType;
    }

    public void setImgType(byte[] imgType) {
        this.imgType = imgType;
    }
}
