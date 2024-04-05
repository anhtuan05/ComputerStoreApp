package com.example.computerstoreapp.database;

public class Product {

    private int productid;
    private String name;
    private String description;
    private float price;
    private int quantity;
    private String img;

    private int active;

    private int categoriesid;

    public Product() {
    }

    public Product(String name, String description, float price, int quantity, String img, int active, int categoriesid) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.img = img;
        this.active = active;
        this.categoriesid = categoriesid;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desciption) {
        this.description = desciption;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCategoriesid() {
        return categoriesid;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public void setCategoriesid(int categoriesid) {
        this.categoriesid = categoriesid;
    }


}
