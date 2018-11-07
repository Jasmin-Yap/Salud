package mapp.com.sg.salud.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Jasmin on 2/8/2018.
 */

public class drinksData {
    private String Bname;
    private String Bprice;
    private int image;
    private int quan;

    public drinksData(String name, String price, int image){
        this.Bname = name;
        this.Bprice = price;
        this.image = image;
    }

    public int getImage() { return image; }

    public void setImage(int image) { this.image = image; }

    public String getBName() { return Bname; }

    public void setBName(String name) { this.Bname = name; }

    public String getBPrice() { return Bprice; }

    public void setBPrice(String price) { this.Bprice = price; }

    public int getQuan() { return quan; }

    public void setQuan(int quan) { this.quan = quan; }
}
