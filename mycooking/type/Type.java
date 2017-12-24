package com.example.banhnhandau.mycooking.type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by BanhNhanDau on 11/04/2017.
 */


public class Type {

    @SerializedName("idType")
    @Expose
    private int idType;
    @SerializedName("nameType")
    @Expose
    private String nameType;
    @SerializedName("imgType")
    @Expose
    private String imgType;
    @SerializedName("count")
    @Expose
    private int count;

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

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}



