package com.example.banhnhandau.mycooking.eating;

/**
 * Created by BanhNhanDau on 11/07/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Eating {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("material")
    @Expose
    private String material;
    @SerializedName("making")
    @Expose
    private String making;
    @SerializedName("tips")
    @Expose
    private String tips;
    @SerializedName("idType")
    @Expose
    private int idType;
    @SerializedName("bookmark")
    @Expose
    private int bookmark;
    @SerializedName("image")
    @Expose
    private String image;

    public Eating(int id, String name, String material, String making, String tips, int idType, int bookmark, String image) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.making = making;
        this.tips = tips;
        this.idType = idType;
        this.bookmark = bookmark;
        this.image = image;
    }

    public Eating() {
//        this.material = null;
//        this.making = null;
//        this.tips = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaking() {
        return making;
    }

    public void setMaking(String making) {
        this.making = making;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getBookmark() {
        return bookmark;
    }

    public void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

