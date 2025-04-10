
package JDBC.HospitalManagment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;



//main class
public class HospitalManagment {
    private static final String url = "jdbc:mysql://localhost:3306/hospitalmanagment";
    private static final String username = "root";
    private static final String password = "fatema@1012";

    public static void main(String[] args) throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Loaded successfully..");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        Scanner sc = new Scanner(System.in);
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection created succesfully");
            patient pt = new patient(con, sc);
            doctors dc = new doctors(con);
            while (true) {
                System.out.println("********* HOSPITAL MANAGMENT SYSTEM *************");
                System.out.println("1.Add Patient.");
                System.out.println("2.View Patients.");
                System.out.println("3.View Doctor's.");
                System.out.println("4.Book Appoinment.");
                System.out.println("5.view Appoinment.");
                System.out.println("6.Exit.");
                System.out.println("Enter You Choice.");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        pt.AddPatient();
                        System.out.println();
                        break;
                    case 2:
                        pt.ViewPatients();
                        System.out.println();
                        break;
                    case 3:
                        dc.ViewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        // book Appoinment
                        BookAppoinment(con, sc, pt, dc);
                        System.out.println();
                        break;
                    case 5:
                        // book Appoinment
                        ViewAppoinment(con, sc);
                        System.out.println();
                        break;    
                    case 6:
                    System.out.println("Thank you For Using Hospital managment System.");
                    System.out.print("Exiting System");
                    Exit e = new Exit();
                    e.run();
                        return;
                    default:
                        System.out.println("enter Valid Choice: ");
                        break;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    // check doctor is available at perticular date or not
    public static boolean checkDoctorAvailability(int did, String date, Connection con) {
        // give no of rows
        String query = "SELECT COUNT(*) FROM appoinments WHERE doctor_id=? AND appoinment_date=? ;";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, did);
            ps.setString(2, date);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //view appoinment
    public static void ViewAppoinment(Connection con, Scanner sc) {
    	System.out.println("*********** Patients appoinment: ******************");
    	System.out.println("Enter your Patient id: ");
    	int pid=sc.nextInt();
    	String docNameString="";
    	String patiensNameString="";
    	String queryString="SELECT * from appoinments WHERE patient_id=? ;";
    	try {
    		PreparedStatement pdPreparedStatement=con.prepareStatement(queryString);
        	pdPreparedStatement.setInt(1, pid);
        	ResultSet rSet=pdPreparedStatement.executeQuery();

        	while(rSet.next()) {
        		int docId=rSet.getInt("doctor_id");
        		String dateString=rSet.getString("appoinment_date");
        		String qString="SELECT name FROM doctors WHERE id=? ;";
        		String PatqString="SELECT name FROM patients WHERE id=? ;";
        		try {
        			PreparedStatement pdStatement=con.prepareStatement(qString);
        			pdStatement.setInt(1, docId);
        			ResultSet rsSet=pdStatement.executeQuery();
        			if(rsSet.next()) {
        			 docNameString=rsSet.getString("name");
        			}
        		}catch (Exception e) {
					// TODO: handle exception
        		    e.printStackTrace();
				}
        		try {
        			PreparedStatement pdStatement=con.prepareStatement(PatqString);
        			pdStatement.setInt(1, pid);
        			ResultSet rsSet=pdStatement.executeQuery();
        			if(rsSet.next()) {
        			 patiensNameString=rsSet.getString("name");
        			}
        		}catch (Exception e) {
					// TODO: handle exception
        		    e.printStackTrace();
				}
        		System.out.println("Patients Name: "+patiensNameString);
                System.out.println("Doctors Name: "+docNameString);
                System.out.println("Appoinment Date: "+dateString);
        	}
        	
        	
    	}catch (Exception e) {
			// TODO: handle exception
  
    		e.printStackTrace();
		}
    }

    // book appoinment
    public static void BookAppoinment(Connection con, Scanner sc, patient pat, doctors doc) {
        System.out.print("Enter Patient id: ");
        int pid = sc.nextInt();
        System.out.print("Enter Doctor id: ");
        int did = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Appoinment date:yyyy-mm-dd  : ");
        String date = sc.nextLine();
        // get patient by id //get doctor by id
        if (pat.getPatientbyid(pid) && doc.getDoctorbyid(did)) {
            if (checkDoctorAvailability(did, date, con)) {
                String app_query = "INSERT INTO `appoinments` (`id`, `patient_id`, `doctor_id`, `appoinment_date`) VALUES (NULL, ?, ?,?);";
                try {
                    PreparedStatement ps = con.prepareStatement(app_query);
                    ps.setInt(1, pid);
                    ps.setInt(2, did);
                    ps.setString(3, date);
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Appoinment Book Successfully..");
                    } else {
                        System.out.println("Failed to Book Appoinment...");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Doctor not Available on this date.");
            }
        } else {
            System.out.println("Either doctor or patient doesnot exist..");
        }

    }
}
