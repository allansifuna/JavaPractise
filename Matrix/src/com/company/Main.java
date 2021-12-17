package com.company;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    static Mat2D gui = new Mat2D();
    static Mat3D gui2 = new Mat3D();
    static JFrame frame = new JFrame();
    public static void main(String[] args) throws SQLException {
        doRun(0);
    }
    public static void doRun(int i){

        if(i==0){
            frame.remove(gui2);
            gui2=new Mat3D();
            run2D();
        }else{
            frame.remove(gui);
            gui=new Mat2D();
            run3D();
        }
    }
    private static void run3D() {
        frame.getContentPane().add(gui2);
        frame.pack();
        frame.setVisible(true);
    }

    public static void run2D() {
        frame.getContentPane().add(gui);
        frame.pack();
        frame.setVisible(true);
    }
}
