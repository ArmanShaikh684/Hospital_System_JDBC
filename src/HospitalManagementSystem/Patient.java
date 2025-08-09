package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

    private Connection con;
    private Scanner sc;

    public  Patient(Connection con , Scanner sc)
    {
        this.con = con;
        this.sc = sc;
    }

    public void add_patient()
    {
        System.out.println("----- ADD PATIENT -----");
        sc.nextLine();
        System.out.print("Enter Patient Name : ");
        String name = sc.nextLine();
        System.out.print("Enter Patient Age : ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Patient Gender : ");
        String gender = sc.nextLine();

        try
        {
            String add_patient_query = "insert into patients (name,age,gender) values (?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(add_patient_query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
            int rows_affected = preparedStatement.executeUpdate();
            if(rows_affected > 0)
            {
                System.out.println("Patient Added Successfully !");
            }
            else {
                System.out.println("Failed to Add Patient !");
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public void view_patient()
    {
        String view_patient_query = "select * from patients";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(view_patient_query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients : ");
            System.out.println("+-----------------+-----------------------------------+---------------+--------------------+");
            System.out.println("|   Patient ID    |             Name                  |     Age       |        Gender      |");
            System.out.println("+-----------------+-----------------------------------+---------------+--------------------+");

            while(resultSet.next() == true)
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("|  %-15s|  %-33s|  %-13s|  %-18s|\n" ,id,name,age,gender);
                System.out.println("+-----------------+-----------------------------------+---------------+--------------------+");

            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public boolean get_patient_by_id(int id)
    {
        String get_patient_by_id_query = "select * from patients where id=?";

        try
        {
            PreparedStatement preparedStatement=con.prepareStatement(get_patient_by_id_query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next() == true)
            {
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
