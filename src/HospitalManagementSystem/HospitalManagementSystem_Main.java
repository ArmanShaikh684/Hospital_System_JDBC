package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem_Main {

    private static final String url ="jdbc:mysql://localhost:3306/hospital_system";
    private static final String username="root";
    private static final String password="arman@684";

    public static void main(String[] args)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
         catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner sc = new Scanner(System.in);

        try{
            Connection con = DriverManager.getConnection(url,username,password);

            Patient patient = new Patient(con,sc);
            Doctor doctor = new Doctor(con);

            while(true)
            {
                System.out.println("***** HOSPITAL MANAGEMENT SYSTEM *****");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.print("Enter your choice : ");
                int choice=sc.nextInt();

                switch (choice)
                {
                    case 1:
                        patient.add_patient();
                        System.out.println();
                        break;
                    case 2:
                        patient.view_patient();
                        System.out.println();
                        break;
                    case 3:
                        doctor.view_doctors();
                        System.out.println();
                        break;
                    case 4:
                        book_appointment(patient,doctor,con,sc);
                        System.out.println();
                        break;
                    case 5:
                        System.out.println("Exiting System .....");
                        return;
                    default:
                        System.out.println("Enter a valid choice !!!");
                        break;
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static void book_appointment(Patient patient,Doctor doctor,Connection con,Scanner sc)
    {
        System.out.println("----- BOOK APPOINTMENT -----");
        sc.nextLine();
        System.out.print("Enter Patient ID : ");
        int patient_id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Doctor ID : ");
        int doctor_id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Appointment Date (YYYY-MM-DD) : ");
        String appointment_date = sc.nextLine();

        if(patient.get_patient_by_id(patient_id) && doctor.get_doctors_by_id(doctor_id))
        {
            if(check_doctor_availabality(doctor_id,appointment_date,con))
            {
                String appointmentQuery = "insert into appointments (patient_id ,doctor_id,appointment_date) values(?,?,?)";
                try
                {
                    PreparedStatement preparedStatement = con.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patient_id);
                    preparedStatement.setInt(2,doctor_id);
                    preparedStatement.setString(3,appointment_date);
                    int rows_affected = preparedStatement.executeUpdate();
                    if(rows_affected>0)
                    {
                        System.out.println("Appointment booked !");
                    }
                    else {
                        System.out.println("Failed to Book Appointment !");
                    }

                }catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Doctor not available on this date !");
            }
        }
        else {
            System.out.println("Either Doctor or Patient Doesn't Exists !");
        }
    }

    public static boolean check_doctor_availabality(int doctor_id , String appointment_date,Connection con)
    {
        String check_doctor_availabality_query = "select count(*) from appointments where doctor_id = ? and appointment_date = ? ";

        try{
            PreparedStatement preparedStatement = con.prepareStatement(check_doctor_availabality_query);
            preparedStatement.setInt(1,doctor_id);
            preparedStatement.setString(2,appointment_date);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                int count = resultSet.getInt(1);
                if(count == 0)
                {
                    return true;
                }
                else {
                    return false;
                }
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }






}
