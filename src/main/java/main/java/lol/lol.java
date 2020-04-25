package main.java.lol;


import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.MappingStrategy;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class lol {

    //Database credentials
    static final String USER = "root";
    static final String PASS = "";
    static final String HOST = "localhost:3307";

    @SuppressWarnings({"rawtypes", "unchecked", "resource"})
    public static void main(String[] args) throws IOException {
                
        List<PhotoFrame> photoFrame = List.of(
                new DigitalFrame(1, "lol", "200f", "RED", "digital", "12", "12", "12"),
                new PlainFrame(2, "lol", "200f", "RED", "plain", "wather", "12", "12"),
                new DigitalFrame(3, "lol", "200f", "RED", "digital", "12", "12", "12"),
                new PlainFrame(4, "lol", "200f", "RED", "plain", "wather", "12", "12")
        );


        //write csv file
        FileWriter csvWrite = new FileWriter("test.csv");
        for (var frame : photoFrame) {
            csvWrite.append(frame.toString());
        }
        csvWrite.close();

        DBConn conn = null;


        try {
            conn = DBConn.OpenConnection(HOST, USER, "");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        try {
            CSVReader reader = new CSVReader(new FileReader("test.csv"), ',', '"', 0);
            List<String[]> allRows = reader.readAll();
            conn.setupPhotoFrameDB();
            conn.InsertPhotoFrame(allRows);

            while (true) {
                System.out.println("1 - Select All Photoframes");
                System.out.println("2 - Select Digitalframes");
                System.out.println("3 - Select Plaineframe");
                System.out.println("0 - close");
                Scanner in = new Scanner(System.in);
                System.out.print("Input a number: ");
                int num = in.nextInt();
                switch (num) {
                    case 1:
                        conn.SelectAllPhotoFrames();
                        break;
                    case 2:
                        conn.SelectDigitalFrame();
                        break;
                    case 3:
                        conn.SelectPlaineFrame();
                        break;
                    case 0:
                        conn.DropShema();
                        System.exit(0);
                    default:
                        System.out.println("Повторите попытку или нажмити 0 для завершения работы программы");
                }
            }
        } catch (Exception e) {
            System.out.println("Error while setup db: " + e.getLocalizedMessage());
            System.exit(-1);
        }
    }
}
