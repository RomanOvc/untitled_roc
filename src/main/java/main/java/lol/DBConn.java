package main.java.lol;

import java.sql.*;
import java.util.List;


public class DBConn {

    //JDBC driver and URl
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    Connection conn;

    private DBConn(Connection conn) {
        this.conn = conn;
    }

    public static DBConn OpenConnection(String host, String userName, String password) throws SQLException, IllegalArgumentException, ClassNotFoundException {
        if (host != null && host.isEmpty() && userName != null && userName.isEmpty()) {
            throw new IllegalArgumentException("Host and username  cannot be empty");
        }
        String jdbc_url;
        if (password != null && !password.isEmpty()) {
            jdbc_url = "jdbc:mysql://" + userName + ":" + password + "@" + host;
        } else {
            jdbc_url = "jdbc:mysql://" + userName + "@" + host;
        }

        Connection conn = null;

        //Register JDBC driver
        Class.forName(JDBC_DRIVER);

        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(jdbc_url);
        DBConn newConn = new DBConn(conn);
        return newConn;
    }

    public void setupPhotoFrameDB() throws Exception {
        // class Statement need for SQL query
        Statement stmt = null;
        //Execute a query
        System.out.println("Creating database...");
        stmt = conn.createStatement();

        String create_db_sql = "CREATE DATABASE PhotoFrame";
        stmt.executeUpdate(create_db_sql);

        //photoframes
        String create_tPhotoframes_sql = "CREATE TABLE photoframe.photoframes("
                + "frame_id INT NOT NULL  PRIMARY KEY,"
                + "name VARCHAR(45) NOT NULL,"
                + "price VARCHAR(5) NOT NULL,"
                + "color VARCHAR(10) NOT NULL,"
                + "type VARCHAR(10) not null)";
        stmt.executeUpdate(create_tPhotoframes_sql);


        //digitalframe
        String create_tDigitalfrmaes_sql = "CREATE TABLE photoframe.digitalframes("
                + "frame_id INT not null PRIMARY KEY,"
                + "memory VARCHAR(45) not null,"
                + "viewing_angle VARCHAR(45) not null,"
                + "size VARCHAR(45) not null,"
                + "FOREIGN KEY (frame_id) REFERENCES photoframe.photoframes(frame_id))";
        stmt.executeUpdate(create_tDigitalfrmaes_sql);

        String create_tPlainfrmaes_sql = "CREATE TABLE photoframe.plaineframes("
                + "frame_id INT not null PRIMARY KEY,"
                + "material VARCHAR(45) not null,"
                + "width VARCHAR(45) not null,"
                + "material_insert VARCHAR(45) not null,"
                + "FOREIGN KEY (frame_id) REFERENCES photoframe.photoframes(frame_id))";
        stmt.executeUpdate(create_tPlainfrmaes_sql);

    }

//    public void InsertPhotoFrame(PhotoFrame frame) throws Exception {
//        Statement stmt;
//        if (frame instanceof PlainFrame) {
//            PlainFrame plainFrame = (PlainFrame) frame;
//            plainFrame.getMaterial_insert();
//        } else if (frame instanceof DigitalFrame) {
//            DigitalFrame plainFrame = (DigitalFrame) frame;
//        }
//
//    }

    public void InsertPhotoFrame(List<String[]> mass) throws Exception {

        String sql_photoframe = "INSERT INTO photoframe.photoframes( frame_id,name,price,color,type) VALUES(?,?,?,?,?)";
        String sql_digitalframe = "INSERT INTO photoframe.digitalframes(frame_id,memory,viewing_angle,size) VALUES(?,?,?,?)";
        String sql_plainframe = "INSERT INTO photoframe.plaineframes(frame_id,material,width,material_insert) VALUES(?,?,?,?)";


        for (String[] row : mass) {
            for (String a : row) {
                String[] k = a.split(";");
                PreparedStatement stmt1 = conn.prepareStatement(sql_photoframe);
                stmt1.setString(1, k[0]);
                stmt1.setString(2, k[1]);
                stmt1.setString(3, k[2]);
                stmt1.setString(4, k[3]);
                stmt1.setString(5, k[4]);
                stmt1.executeUpdate();

                if (k[4].equals("digital")) {
                    PreparedStatement stmt2 = conn.prepareStatement(sql_digitalframe);
                    stmt2.setString(1, k[0]);
                    stmt2.setString(2, k[5]);
                    stmt2.setString(3, k[6]);
                    stmt2.setString(4, k[7]);
                    stmt2.executeUpdate();

                } else if (k[4].equals("plain")) {
                    PreparedStatement stmt3 = conn.prepareStatement(sql_plainframe);
                    stmt3.setString(1, k[0]);
                    stmt3.setString(2, k[5]);
                    stmt3.setString(3, k[6]);
                    stmt3.setString(4, k[7]);
                    stmt3.executeUpdate();
                }
            }
        }
    }

    //Select all
    public void SelectAllPhotoFrames() throws SQLException {
        String selectAllPhotoFrames = "SELECT * FROM ((photoframe.photoframes" +
                " LEFT JOIN photoframe.digitalframes ON photoframe.photoframes.frame_id =  photoframe.digitalframes.frame_id)" +
                " LEFT JOIN photoframe.plaineframes ON  photoframe.photoframes.frame_id =  photoframe.plaineframes.frame_id ) ORDER BY photoframe.photoframes.frame_id";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(selectAllPhotoFrames);
        StringBuilder sb = new StringBuilder();
        while (rs.next()) {
            int frame_id = rs.getInt("frame_id");
            String name = rs.getString("name");
            String price = rs.getString("price");
            String color = rs.getString("color");
            String type = rs.getString("type");

            //digitalFrame parametrs
            String memory = rs.getString("memory");
            String viewing_angle = rs.getString("viewing_angle");
            String size = rs.getString("size");

            //plaine Frame parametrs
            String material = rs.getString("material");
            String width = rs.getString("width");
            String material_insert = rs.getString("material_insert");
            if (type.equals("digital")) {
                sb.append("frame_id: ").append(frame_id)
                        .append("; name: ").append(name)
                        .append("; price: ").append(price)
                        .append("; color: ").append(color)
                        .append("; type: ").append(type)
                        .append("; memory: ").append(memory)
                        .append("; viewing_angle: ").append(viewing_angle)
                        .append("; size: ").append(size)
                        .append("\n");
            } else {
                sb.append("frame_id: ").append(frame_id)
                        .append("; name: ").append(name)
                        .append("; price: ").append(price)
                        .append("; color: ").append(color)
                        .append("; type: ").append(type)
                        .append("; material: ").append(material)
                        .append("; width: ").append(width)
                        .append("; material_insert: ").append(material_insert)
                        .append("\n");
            }
        }
        stmt.close();
        System.out.println(sb.toString());
    }

    public void SelectDigitalFrame() throws SQLException {
        String selectAllPhotoFrames = "SELECT * FROM photoframe.photoframes  INNER JOIN photoframe.digitalframes ON photoframe.photoframes.frame_id =  photoframe.digitalframes.frame_id";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(selectAllPhotoFrames);
        StringBuilder sb = new StringBuilder();
        while (rs.next()) {
            int frame_id = rs.getInt("frame_id");
            String name = rs.getString("name");
            String price = rs.getString("price");
            String color = rs.getString("color");
            String type = rs.getString("type");

            //digitalFrame parametrs
            String memory = rs.getString("memory");
            String viewing_angle = rs.getString("viewing_angle");
            String size = rs.getString("size");

            sb.append("frame_id: ").append(frame_id)
                    .append("; name: ").append(name)
                    .append("; price: ").append(price)
                    .append("; color: ").append(color)
                    .append("; type: ").append(type)
                    .append("; memory: ").append(memory)
                    .append("; viewing_angle: ").append(viewing_angle)
                    .append("; size: ").append(size)
                    .append("\n");
        }
        stmt.close();
        System.out.println(sb.toString());
    }

    public void SelectPlaineFrame() throws SQLException {
        String selectAllPhotoFrames = "SELECT * FROM photoframe.photoframes INNER JOIN photoframe.plaineframes ON  photoframe.photoframes.frame_id =  photoframe.plaineframes.frame_id ";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(selectAllPhotoFrames);
        StringBuilder sb = new StringBuilder();
        while (rs.next()) {
            int frame_id = rs.getInt("frame_id");
            String name = rs.getString("name");
            String price = rs.getString("price");
            String color = rs.getString("color");
            String type = rs.getString("type");

            //plaine Frame parametrs
            String material = rs.getString("material");
            String width = rs.getString("width");
            String material_insert = rs.getString("material_insert");

            sb.append("frame_id: ").append(frame_id)
                    .append("; name: ").append(name)
                    .append("; price: ").append(price)
                    .append("; color: ").append(color)
                    .append("; type: ").append(type)
                    .append("; material: ").append(material)
                    .append("; width: ").append(width)
                    .append("; material_insert: ").append(material_insert)
                    .append("\n");
        }
        stmt.close();
        System.out.println(sb.toString());
    }

    public void DropShema() throws SQLException {
        Statement stmt = null;
        //Execute a query

        stmt = conn.createStatement();

        String create_db_sql = "drop schema photoframe";
        stmt.executeUpdate(create_db_sql);
        System.out.println("База удалена");

    }

}