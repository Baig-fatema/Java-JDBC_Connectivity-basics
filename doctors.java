package JDBC.HospitalManagment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class doctors {
        private Connection con;

    public doctors(Connection con) {
        this.con = con;
    }

    // methods
  public void ViewDoctors()
  {
    String query="SELECT * FROM doctors;";
    try{
      PreparedStatement ps=con.prepareStatement(query);
      ResultSet rs=ps.executeQuery();
      System.out.println("Doctor Details...");
      System.out.println("+-----------+----------------------------+--------------------------+");
      System.out.println("| DoctorId  |        Name                |  Specialization          |");
      System.out.println("+-----------+----------------------------+--------------------------+");
    
      while(rs.next())
      {
        int id=rs.getInt("id");
        String name=rs.getString("name");
        String specialization=rs.getString("specialization");
        System.out.printf("|%-11s|%-28s|%-26s|\n",id,name,specialization);
        System.out.println("+-----------+----------------------------+--------------------------+");

      }
    }catch(Exception e)
    {
        System.out.println(e.getMessage());
    }
  }
  public boolean getDoctorbyid(int id)
  {
    String query="SELECT * FROM doctors WHERE ID=? ; ";
    try {
        PreparedStatement ps=con.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs=ps.executeQuery();

       if(rs.next())
       {
        return true;
       }else{
        return false;
       }
    } catch (Exception e) {
        System.out.println(e.getMessage());
  }
  return false;
}
}
