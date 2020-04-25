package main.java.lol;

class DigitalFrame extends PhotoFrame {
    public String memory;         // количество встроенной памяти
    public String viewing_angle;  // угол обзора в градусах
    public String size;        // размер рамки

    public DigitalFrame(int id, String name, String price, String color, String type, String memory, String viewing_angle, String size) {
        super(id, name, price, color, type);
        this.memory = memory;
        this.viewing_angle = viewing_angle;
        this.size = size;

    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getViewing_angle() {
        return viewing_angle;
    }

    public void setViewing_angle(String viewing_angle) {
        this.viewing_angle = viewing_angle;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getId()).append(";")
                .append(getName()).append(";")
                .append(getPrice()).append(";")
                .append(getColor()).append(";")
                .append(getType()).append(";")
                .append(getMemory()).append(";")
                .append(getViewing_angle()).append(";")
                .append(getSize())
                .append(System.lineSeparator());
//        return "[" + getId() + ";" + getName() + ";" + getPrice() + ";" + getColor() + ";" + getType() + ";" + getMemory() + ";" + getViewing_angle() + ";" + getSize() + "]";
        return sb.toString();
    }

}
