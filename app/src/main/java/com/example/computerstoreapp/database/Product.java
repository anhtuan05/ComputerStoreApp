package com.example.computerstoreapp.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Product implements Parcelable {

    private int productid;
    private String name;
    private String description;
    private float price;
    private int quantity;
    private String img;

    private int active;

    private int categoriesid;

    private int orderQuantity = 1;

    public Product() {
    }

    public Product(Parcel in) {
        productid = in.readInt();
        name = in.readString();
        description = in.readString();
        price = in.readFloat();
        quantity = in.readInt();
        img = in.readString();
        active = in.readInt();
        categoriesid = in.readInt();
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

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productid);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeFloat(price);
        dest.writeInt(quantity);
        dest.writeString(img);
        dest.writeInt(active);
        dest.writeInt(categoriesid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product otherProduct = (Product) obj;
        return this.productid == otherProduct.productid;
    }
}
