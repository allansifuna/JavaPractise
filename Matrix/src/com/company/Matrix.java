package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Matrix {
    int[][] mat=new int[2][2];
    int[][] mat3D=new int[3][3];
    int[][] ans = new int[2][1];
    int[][] ans3D = new int[3][1];
    public Matrix(int a,int b, int c,int d,int e, int f){
        mat[0][0]=a;
        mat[0][1]=b;
        mat[1][0]=c;
        mat[1][1]=d;
        ans[0][0]=e;
        ans[1][0]=f;
    }
    public Matrix(int a,int b, int c,int d,int e, int f,int g, int h,int i, int j, int k, int l ){
        mat3D[0][0]=a;
        mat3D[0][1]=b;
        mat3D[0][2]=c;
        mat3D[1][0]=d;
        mat3D[1][1]=e;
        mat3D[1][2]=f;
        mat3D[2][0]=g;
        mat3D[2][1]=h;
        mat3D[2][2]=i;
        ans3D[0][0]=j;
        ans3D[1][0]=k;
        ans3D[2][0]=l;
    }

    private  void insertTo2D(String eq1,String eq2,int x, int y) throws SQLException {
        String str="INSERT INTO matrix2D(eq1,eq2,x,y) VALUES(?,?,?,?);";
        PreparedStatement st = getConn().prepareStatement(str);
        st.setString(1,eq1);
        st.setString(2,eq2);
        st.setInt(3,x);
        st.setInt(4,y);
        st.execute();

    }
    private void insertTo3D(String eq1,String eq2,String eq3,int x, int y, int z) throws SQLException {
        String str="INSERT INTO matrix3D(eq1,eq2,eq3,x,y,z) VALUES(?,?,?,?,?,?);";
        PreparedStatement st = getConn().prepareStatement(str);
        st.setString(1,eq1);
        st.setString(2,eq2);
        st.setString(3,eq3);
        st.setInt(4,x);
        st.setInt(5,y);
        st.setInt(6,z);
        st.execute();
    }

    public Connection getConn() throws SQLException {
        Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/matrices?user=allan&password=Namasaka88!");
        return conn;
    }
    private void createTables() throws SQLException {
        String str="CREATE TABLE IF NOT EXISTS matrix2D( id INT AUTO_INCREMENT,eq1 VARCHAR(50) NOT NULL,eq2 VARCHAR(50) NOT NULL, x int NOT NULL, y INT NOT NULL, PRIMARY KEY(id))";
        String str1="CREATE TABLE IF NOT EXISTS matrix3D( id int AUTO_INCREMENT,eq1 VARCHAR(50) NOT NULL,eq2 VARCHAR(50) NOT NULL,eq3 VARCHAR(50) NOT NULL, x int NOT NULL, y INT NOT NULL,z int NOT NULL, PRIMARY KEY(id))";
        PreparedStatement st = getConn().prepareStatement(str);
        PreparedStatement st1 = getConn().prepareStatement(str1);
        st.execute();
        st1.execute();

    }



    public int[][] solve3DEquation() throws SQLException {
        String[] eqs= build3DEquations();
        //get inverse of the matrix
        int det = get3Ddet();
        //transpose matrix
        mat3D=transposeMatrix(mat3D);
        //find determinants for 2 by 2 matrices in the matrix
        findMinorDets();
        //Change signs for some places in the matrix
        createCoFactors();
        //multiply the inverse matrix by the answer matrix
        multiplyByAns();
        //divide the resultant matrix with the determinant
        divideByDet(det);
        // insert the matrix data and the solutions in the database
        insertTo3D(eqs[0],eqs[1],eqs[2],ans3D[0][0],ans3D[1][0],ans3D[2][0]);
        return ans3D;
    }

    private String[] build3DEquations() {
        String[] eqs=new String[3];
        for (int i = 0; i < 3; i++) {
            StringBuilder sb =new StringBuilder();
            sb.append(String.valueOf(mat3D[i][0]));
            if(mat3D[i][0]!=0) {
                if (mat3D[i][1] < 0) {
                    sb.append("x");
                } else {
                    sb.append("x+");
                }
            }
            sb.append(String.valueOf(mat3D[i][1]));
            if(mat3D[i][1]!=0) {
                if (mat3D[i][2] < 0) {
                    sb.append("y");
                } else {
                    sb.append("y+");
                }
            }
            sb.append(String.valueOf(mat3D[i][2]));
            if(mat3D[i][2]==0){
                sb.append("=");
            }else {
                sb.append("z=");
            }
            sb.append(String.valueOf(ans3D[i][0]));
            eqs[i]=sb.toString();
        }
        return eqs;
    }
    private String[] build2DEquations() {
        String[] eqs=new String[2];
        for (int i = 0; i < 2; i++) {
            StringBuilder sb =new StringBuilder();
            sb.append(String.valueOf(mat[i][0]));
            if(mat[i][0]!=0) {
                if (mat[i][1] < 0) {
                    sb.append("x");
                } else {
                    sb.append("x+");
                }
            }
            sb.append(String.valueOf(mat[i][1]));
            if(mat[i][1]==0){
                sb.append("=");
            }else {
                sb.append("y=");
            }
            sb.append(String.valueOf(ans[i][0]));
            eqs[i]=sb.toString();
        }
        return eqs;
    }

    private void multiplyByAns() {
        int a,b,c,d,e,f,g,h,i,x,y,z;
        a=mat3D[0][0];
        b=mat3D[0][1];
        c=mat3D[0][2];
        d=mat3D[1][0];
        e=mat3D[1][1];
        f=mat3D[1][2];
        g=mat3D[2][0];
        h=mat3D[2][1];
        i=mat3D[2][2];
        x=ans3D[0][0];
        y=ans3D[1][0];
        z=ans3D[2][0];
        ans3D[0][0]=(a*x+ b* y+c*z);
        ans3D[1][0]=(d*x + e* y+f*z);
        ans3D[2][0]=(g*x + h* y+i*z);
    }

    private void divideByDet(int det) {
        ans3D[0][0]=ans3D[0][0]/det;
        ans3D[1][0]=ans3D[1][0]/det;
        ans3D[2][0]=ans3D[2][0]/det;
    }

    private void createCoFactors() {
        mat3D[0][1]=mat3D[0][1]*-1;
        mat3D[1][0]=mat3D[1][0]*-1;
        mat3D[1][2]=mat3D[1][2]*-1;
        mat3D[2][1]=mat3D[2][1]*-1;
    }

    private void findMinorDets() {
        int a,b,c,d,e,f,g,h,i;
        a=mat3D[0][0];
        b=mat3D[0][1];
        c=mat3D[0][2];
        d=mat3D[1][0];
        e=mat3D[1][1];
        f=mat3D[1][2];
        g=mat3D[2][0];
        h=mat3D[2][1];
        i=mat3D[2][2];
        mat3D[0][0]=getDet(formDet(e,f,h,i));
        mat3D[0][1]=getDet(formDet(d,f,g,i));
        mat3D[0][2]=getDet(formDet(d,e,g,h));
        mat3D[1][0]=getDet(formDet(b,c,h,i));
        mat3D[1][1]=getDet(formDet(a,c,g,i));
        mat3D[1][2]=getDet(formDet(a,b,g,h));
        mat3D[2][0]=getDet(formDet(b,c,e,f));
        mat3D[2][1]=getDet(formDet(a,c,d,f));
        mat3D[2][2]=getDet(formDet(a,b,d,e));
    }

    private int[][] formDet(int a, int b, int c, int d) {
        int [][] tMat= new int[2][2];
        tMat[0][0]=a;
        tMat[0][1]=b;
        tMat[1][0]=c;
        tMat[1][1]=d;
        return tMat;
    }

    private  int[][] transposeMatrix(int[][] mat3D) {
        int[][] tempMat = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tempMat[i][j]=mat3D[j][i];
            }
        }
        return tempMat;
    }

    private int get3Ddet() {
        int a,b,c,d,e,f,g,h,i;
        a=mat3D[0][0];
        b=mat3D[0][1];
        c=mat3D[0][2];
        d=mat3D[1][0];
        e=mat3D[1][1];
        f=mat3D[1][2];
        g=mat3D[2][0];
        h=mat3D[2][1];
        i=mat3D[2][2];
        int ans= a*(e*i-f*h)-b*(d*i-f*g)+c*(d*h-e*g);
        return ans;
    }

    public int[][] solve2DEquation() throws SQLException {
        String[] eqs= build2DEquations();
        //get inverse of mat
        getInverse();
        //get determinant
        int det = getDet(mat);
        multiplyInverse();
        multiplyDet(det);
        insertTo2D(eqs[0],eqs[1],ans[0][0],ans[1][0]);
        return ans;

    }

    private void multiplyDet( int det) {
        ans[0][0]= (int) (ans[0][0]/det);
        ans[1][0]= (int) (ans[1][0]/det);
    }

    private void multiplyInverse() {
        int a = mat[0][0];
        int b = mat[0][1];
        int c = mat[1][0];
        int d = mat[1][1];
        int ans1=a*ans[0][0]+b*ans[1][0];
        int ans2=c*ans[0][0]+d*ans[1][0];
        ans[0][0]=ans1;
        ans[1][0]=ans2;
    }

    private int getDet(int[][] mat) {
        int ans=((mat[0][0]*mat[1][1])-(mat[0][1]*mat[1][0]));
        return ans;
    }

    private int[][] getInverse() {
        int a = mat[0][0];
        int d= mat[1][1];
        mat[0][0]=d;
        mat[1][1]=a;
        mat[0][1]=mat[0][1]*-1;
        mat[1][0]=mat[1][0]*-1;
        return mat;
    }
}
