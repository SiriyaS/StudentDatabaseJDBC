/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentdatabasejdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author siriya_s
 */
public class StudentDatabaseJDBC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String derbyClientDriver = "org.apache.derby.jdbc.ClientDriver";

        //load driver
        Class.forName(derbyClientDriver);

        //create connection
        String url = "jdbc:derby://localhost:1527/Student";
        String user = "app";
        String passwd = "app";

        Connection con = DriverManager.getConnection(url, user, passwd);
        
        // insert student
        Student stud1 = new Student(1, "Alien", 3.99);
        Student stud2 = new Student(2, "Manud", 3.86);
        insertStudentPreparedStatement(con, stud1);
        insertStudentPreparedStatement(con, stud2);
        
        // update student
        // Student newStud2 = new Student(2, "Manud", 3.89);
        // updateStudentGPAPreparedStatement(con, newStud2);
        
        // delete student
        // deleteStudentPreparedStatement(con, stud1);
        
        // select by id
        // getStudentByIdPreparedStatement(con, 2);

        // select all
        ArrayList<Student> studentList = getAllStudent(con);
        printAllStudent(studentList);
        
        //close connection
        con.close();
    }
    
    public static void printAllStudent(ArrayList<Student> studentList) {
        for(Student stud : studentList) {
           System.out.print(stud.getID() + " ");
           System.out.print(stud.getName() + " ");
           System.out.println(stud.getGPA() + " ");
       }
    }
    
    public static ArrayList<Student> getAllStudent (Connection con) throws SQLException {
        String sql = "select * from student order by id";
        PreparedStatement ps = con.prepareStatement(sql);
        ArrayList<Student> studentList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
           Student student = new Student();
           student.setID(rs.getInt("id"));
           student.setName(rs.getString("name"));
           student.setGPA(rs.getDouble("gpa"));
           studentList.add(student);
        }
        rs.close();
        return studentList;
    }

    public static void insertStudentPreparedStatement(Connection con, Student stud) throws SQLException {
        String sql = "insert into student (id, name, gpa)" + 
                " values (?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, stud.getID());
        ps.setString(2, stud.getName());
        ps.setDouble(3, stud.getGPA());
        int result = ps.executeUpdate();
        //display result
        System.out.println("Insert " + result + " row");
    }
    
    public static void deleteStudentPreparedStatement(Connection con, Student stud) throws SQLException {
        String sql ="delete from student where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, stud.getID());
        int result = ps.executeUpdate();
        //display result
        System.out.println("Delete " + result + " row");
    }
    
    public static void updateStudentGPAPreparedStatement(Connection con, Student stud) throws SQLException {
        String sql = "update student set gpa  = ? where id = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDouble(1, stud.getGPA());
        ps.setInt(2, stud.getID());
        int result = ps.executeUpdate();
        //display result
        System.out.println("Update " + result + " row");
    }
    
    public static void updateStudentNamePreparedStatement(Connection con, Student stud) throws SQLException {
        String sql = "update student set name  = ? where id = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, stud.getName());
        ps.setInt(2, stud.getID());
        int result = ps.executeUpdate();
        //display result
        System.out.println("Update " + result + " row");
    }
    
    public static Student getStudentByIdPreparedStatement(Connection con, int id) throws SQLException {
        Student stud = null;
        String sql = "select * from student where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            stud = new Student();
            stud.setID(rs.getInt("id"));
            stud.setName(rs.getString("name"));
            stud.setGPA(rs.getDouble("gpa"));
        }
        return stud;
    }
}
