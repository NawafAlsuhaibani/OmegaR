package com.example.omegar.NonActivityClasses;
/*
//delete import statements?
import android.app.Activity;
import android.content.Context;
import android.os.DropBoxManager;
import android.util.Log;
import android.widget.Toast;
*/

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.sql.*;


/*
check https://developer.android.com/reference/android/app/Service.html
to see if we can use "Service" app component to handle DBConnections
or, do we need Async class.
*/

public class DBConnector {
    String url = "jdbc:postgresql://rds-omegaratio-db1.cpvvpcxloqfu.us-west-2.rds.amazonaws.com:5432/capstoneTesting";
    String user = "Igat";
    String pw = "omegaR19";


    public DBConnector(/*String url, String user, String pw*/){
        /*try {
            Connection con = DriverManager.getConnection(url,user,pw);
        }catch (SQLException e){
            //Toast.makeText(activity.this, "DB Connection error!",Toast.LENGTH_SHORT).show();
            //(1) I want to make a toast msg in the case where exception is thrown.
            //(2) The problem is getting the correct class name to pass as context in 1st param.
        }*/

    }

    public  String connectThenSelect(/*String attributes, String table*/){
        DBConnector myDB = new DBConnector();

        // try using string builder instead?
        // StringBuilder s = new StringBuilder();
        String s= "";
        try{
            Connection con = DriverManager.getConnection(url,user,pw);

            s = s + "Connection sucessful.\n";
            Statement st = con.createStatement();
            ResultSet rst = st.executeQuery(myDB.select("*","dept"));

            s += ">>>Printing result set.\n";
            s+="+-------------------------------------+\n";
            s+="| dno   |   dname         |    age    |\n";
            s+="|-------------------------------------|\n";
            while(rst.next()) {
                s += (rst.getString("dno")) + "     "
                        + rst.getString("dname") + "  " + rst.getString("age") + "\n";
                //System.out.format("| %2s | %15s | %2s|\n", rst.getString("dno"),
                //                  rst.getString("dname"), rst.getString("age"));
            }
            s+="+-------------------------------------+\n";
            s+="/n>>>Printing done.\n";
            con.close();

        }catch(SQLException e){
            s+=e;

        }
        return s;
    }

    public String test(){
        String aa="";
        try {
            Connection con = DriverManager.getConnection(url,user,pw);
            Statement st = con.createStatement();
            ResultSet rst = st.executeQuery("SELECT * FROM dept;");

            rst.next();
            aa = rst.getString("uid") + "        " +
                    rst.getString("uemail") + "      " +
                    rst.getString("age");

        }catch(SQLException e){
            aa= "Error: " + e + "||";
        }

        return aa;
    }
    /*public boolean isConnected(){
        try{
            Connection con = DriverManager.getConnection(url,user,pw);
            Statement
        } catch (SQLException e) {

        }
        return false;
    }*/

    /*
    This method currently will get userProfile's values
    (uid,email,pass(?maybe?),age,blood Pressure,chronic sickness,gender,weight,)
    by looking at email from userProfile relation.
    We expect each email to have only one email. NO duplicate accounts!!
     */

    public String[] dbGetUserByEmail(String email){
        String[] s = new String[8]; // for 8 values mentioned above.
        try {
            Connection con = DriverManager.getConnection(url, user, pw);
            Statement st1 = con.createStatement();
            ResultSet rst = st1.executeQuery(selectUserByEmail(email));
            rst.next(); //delete
            s[0] = rst.getString("uid");
            s[1] = rst.getString("uemail");
            s[2] = rst.getString("upass");
            s[3] = rst.getString("age");
            s[4] = rst.getString("bp");
            s[5] = rst.getString("chronicDisease");
            s[6] = rst.getString("gender");
            s[7] = rst.getString("weight");
            con.close();
        }catch(Exception e){
            Log.e("DB con", Log.getStackTraceString(e));
        }
        return s;
    }

    String select(String attributes, String table){
        return "SELECT " + attributes + " FROM " + table + ";";
    }

    String selectUserByEmail(String email){
        return select("*","userProfile WHERE uemail='" + email + "'");
    }

    private String selectUserPassword(String pass){
        return select("upass","userProfile WHERE upass='" + pass + "'");
    }

    String insert(String relation, String values) {
        return "INSERT INTO " + relation + " VALUES " + values + ";";
    }

    public Boolean emailExists(String email) throws SQLException{
        /*String s = selectUserByEmail(email);
        Connection con = DriverManager.getConnection(url,user,pw);
        Statement st = con.createStatement();
        ResultSet rst = st.executeQuery(s);
        con.close();
        rst.next();
        return rst.isBeforeFirst();*/


        String s = selectUserByEmail(email);
        Connection con = DriverManager.getConnection(url,user,pw);
        Statement st = con.createStatement();
        ResultSet rst = st.executeQuery(s);
        boolean ha = rst.next();
        con.close();
        return ha;
    }

    public Boolean pwMatch(String email, String password) throws SQLException{
        String s = selectUserByEmail(email);
        Connection con = DriverManager.getConnection(url,user,pw);
        Statement st = con.createStatement();
        ResultSet rst = st.executeQuery(s);
        con.close();
        if(!rst.isBeforeFirst()){return false;}
        //logic is shaky below
        rst.next();
        return password.equals(rst.getString("upass").trim());
    }

    public void signUserUp(String name, String email, String pwd) throws SQLException{
        //name is not defined in userProfile relation on AWS DB. Therefore, it is not used here.
        String s = insert("userProfile (uemail,upass) ", "('" + email + "','" + pwd + "')");
        Connection con = DriverManager.getConnection(url,user,pw);
        Statement st = con.createStatement();
        st.executeUpdate(s);
        con.close();
    }

    public boolean isOnline(){
        boolean truth = false;
        try {
            Connection con = DriverManager.getConnection(url, user, pw);
            truth = true;
            con.close();
        } catch (SQLException e){


        }
        return truth;
    }


    public void pushMeal(String uid, String mealDate, String mealName, double o3, double o6, double amount) throws SQLException{
        String s = insert("userMeals", "('" + uid + "','" + mealDate + "','" + mealName + "','" + o3 + "','" + o6 + "','" + amount + "')");
        Connection con = DriverManager.getConnection(url,user,pw);
        Statement st = con.createStatement();
        st.executeUpdate(s);
        con.close();
    }

    //Get user's meals at a date range. (can be one day or whole month)
    public ResultSet getMealsFromDB(String uid, String startDate, String endDate) throws SQLException{
        //String sql = select("*", "userMeals WHERE uid='" + uid + "'");

        String sql = buildQueryStmt(uid, "userMeals", startDate, endDate);
        Connection con = DriverManager.getConnection(url,user,pw);
        Statement st = con.createStatement();
        ResultSet rst = st.executeQuery(sql);

        con.close();
        return rst;
    }


    //Robin's method
    public String buildQueryStmt(String uid, String tableName, String date1, String date2) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ");

        sb.append(tableName);
        sb.append(" WHERE uid = '");
        sb.append(uid);
        sb.append("' AND mealDate > '");
        sb.append(date1);
        sb.append("' AND mealDate < '");
        sb.append(date2 + "';");

        return sb.toString();
    }


}
