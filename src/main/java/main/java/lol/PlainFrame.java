package main.java.lol;

public class PlainFrame extends PhotoFrame {
    private String material;            // материал рамки
    private String width;                  // ширина рамки
    private String material_insert;     // материал вставки

    public PlainFrame(int id, String name, String price, String color, String type, String material, String width, String material_insert) {
        super(id, name, price, color, type);
        this.material = material;
        this.width = width;
        this.material_insert = material_insert;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getMaterial_insert() {
        return material_insert;
    }

    public void setMaterial_insert(String material_insert) {
        this.material_insert = material_insert;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getId()).append(";")
                .append(getName()).append(";")
                .append(getPrice()).append(";")
                .append(getColor()).append(";")
                .append(getType()).append(";")
                .append(getMaterial()).append(";")
                .append(getWidth()).append(";")
                .append(getMaterial_insert())
                .append(System.lineSeparator());
//        return "\n[" + getId() + ";" + getName() + ";" + getPrice() + ";" + getColor() + ";" + getType() + ";" + getMaterial() + ";" + getWidth() + ";" + getMaterial_insert() + "]";
        return sb.toString();
    }

}
