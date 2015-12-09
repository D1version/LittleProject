package com.vladbondar;

import java.sql.*;

/**
 * Created by Влад on 10.10.2015.
 */
public class DBConnect {
    private String userName = "vladbondar";
    private String password = "password";
    private String URL = "jdbc:mysql://localhost:3306/testvlad";
    private String fullNameDriver = "com.mysql.jdbc.Driver";
    private Connection con = null;
    private ResultSet rs = null;
    private PreparedStatement st = null;

    public DBConnect(){
        try{
            Class.forName(fullNameDriver);
            con = DriverManager.getConnection(URL,userName,password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getRs() {
        return rs;
    }

    public ResultSet getInfo(){
        try{
            st = con.prepareStatement("SELECT * FROM testing_tbl");
            rs = st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int addToDataBase(String name, String date, String number){
        int result = 0;
        try {
            st = con.prepareStatement(String.format("INSERT INTO testing_tbl (Name, Date, phoneNumber)" +
                    " VALUES (" +
                    "'%s'  ,'%s'  ,'%s' )", name,date,number));
            result = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int deleteFromDataBaseByID(int id){
        int result = 0;
        try {
            st = con.prepareStatement(String.format("DELETE FROM testing_tbl WHERE ID = %d", id));
            result = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int updateValueByID(String id, String name, String date, String number){
        int result = 0;
        try{
            st = con.prepareStatement(String.format("UPDATE testing_tbl SET Name = '%s', Date = '%s', phoneNumber = '%s'" +
                    " WHERE ID = '%s'", name, date, number, id));
           result =  st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet getOneValue(int id){

        try{
            st = con.prepareStatement("SELECT * FROM testing_tbl WHERE ID = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rs;
    }

    public int updateValueByName(String oldName, String newName, String date, String number){
        int result = 0;
        try{
            st = con.prepareStatement(String.format("UPDATE testing_tbl SET Name = '%s', Date = '%s', phoneNumber = '%s'" +
                    " WHERE Name = '%s'", newName, date, number, oldName));
            result = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int deleteFromDataBaseByName(String name){
        int result = 0;
        try{
            st = con.prepareStatement(String.format(
                            "DELETE FROM testing_tbl WHERE name = '%s'", name));
            result = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
