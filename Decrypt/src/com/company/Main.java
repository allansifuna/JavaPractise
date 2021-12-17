/**
 * @author your name here!!
 * */
/*package com.company;*/

import java.io.BufferedInputStream;
import java.util.Scanner;
public class Main {
    static Scanner in=new Scanner(new BufferedInputStream(System.in));
    static int l;
    public static void main(String[] args) {
        in.useDelimiter("\r?\n");
        while (in.hasNextInt()) {
            l = in.nextInt();
            if(l==-1){
                break;
            }
            String text = in.next();
            int v = (int) text.length()/l;
            String[][] matrix = new String[v][l];
            matrix = populateMatrix(matrix, text);
            String decodedText = decodeMatrix(matrix);
            System.out.println(decodedText);
        }
    }

    private static String[][] populateMatrix(String[][] matrix,String text) {
        int x=0;
        int y=l-1;
        int j=0;
        while (j<text.length()){
            if(x%2==1){
                while(y<=l-1){
                    matrix[x][y]= String.valueOf(text.charAt(j));
                    y++;
                    j++;
                }
                y--;
            }else{
                while(y>=0){
                    matrix[x][y]= String.valueOf(text.charAt(j));
                    y--;
                    j++;
                }
                y++;
            }
            x++;
        }
        return matrix;

    }
    private static String decodeMatrix(String[][] matrix) {
        StringBuffer buff= new StringBuffer();
        int x=0;
        int y=matrix.length-1;
        try {
            while (true){
                if(x%2==1){
                    while(y<=matrix.length-1){
                        buff.append(matrix[y][x]);
                        y++;
                    }
                    y--;
                }else{
                    while(y>=0){
                        buff.append(matrix[y][x]);
                        y--;
                    }
                    y++;
                }
                x++;
            }
        } catch (Exception e){
        }

        return buff.toString();

    }

}
