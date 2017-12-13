package com.example.banhnhandau.mycooking.eating;

/**
 * Created by BanhNhanDau on 11/07/2017.
 */

public class Eating {
    private int id;
    private String name;
    private String material;
    private String making;
    private byte[] img;
    private String tips;
    private int idType;
    private int bookmark;

    public Eating(int id, String name, String material, String making, byte[] img, String tips, int idType, int bookmark) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.making = making;
        this.img = img;
        this.tips = tips;
        this.idType = idType;
        this.bookmark = bookmark;
    }

    public Eating() {
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

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
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
}
