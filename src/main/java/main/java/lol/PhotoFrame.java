package main.java.lol;

public abstract class PhotoFrame {
    public int id;         // id записи
    public String name;    // название фирмы изготовителя
    public String price;    // цена рамки
    public String color;   // цвет рамки
    public String type;     //type

    public PhotoFrame(int id, String name, String price, String color, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.color = color;
        this.type = type;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "";
    }


}
