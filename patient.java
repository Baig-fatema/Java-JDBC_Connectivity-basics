package JDBC.HospitalManagment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class patient {
    private Connection con;
    private Scanner sc;

    public patient(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    // methods
    public void AddPatient() {
      sc.nextLine();
        System.out.print("Enter Patient Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Patient Age: ");
        int age = sc.nextInt();
        System.out.print("Enter Patient Gender: ");
        String gender = sc.next();
        System.out.println();
        try {
            String query = "INSERT INTO patients(name,age,gender) VALUES(?,?,?);";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            int rowinserted = ps.executeUpdate();
            if (rowinserted > 0) {
                System.out.println("Patient Added Successfully...");
            } else {
                System.out.println("Failed to Add Patient..");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
  public void ViewPatients()
  {
    String query="SELECT * FROM patients;";
    try{
      PreparedStatement ps=con.prepareStatement(query);
      ResultSet rs=ps.executeQuery();
      System.out.println("Patients Details...");
      System.out.println("+-----------+----------------------------+---------+----------------+");
      System.out.println("| PatientId |        Name                |  Age    |  Gender        |");
      System.out.println("+-----------+----------------------------+---------+----------------+");
    
      while(rs.next())
      {
        int id=rs.getInt("id");
        String name=rs.getString("name");
        int age=rs.getInt("age");
        String gender=rs.getString("gender");
        System.out.printf("|%-11s|%-28s|%-9s|%-16s|\n",id,name,age,gender);
        System.out.println("+-----------+----------------------------+---------+----------------+");

      }
    }catch(Exception e)
    {
        System.out.println(e.getMessage());
    }
  }
  public boolean getPatientbyid(int id)
  {
    String query="SELECT * FROM patients WHERE ID=? ; ";
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