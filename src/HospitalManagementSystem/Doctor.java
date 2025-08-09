package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Doctor {

    private Connection con;

    public  Doctor(Connection con )
    {
        this.con = con;

    }


    public void view_doctors()
    {
        String view_doctors_query = "select * from doctors";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(view_doctors_query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors : ");
            System.out.println("+-----------------+-----------------------------------+------------------------------+");
            System.out.println("|   Doctor ID     |             Name                  |      Specialization          |");
            System.out.println("+-----------------+-----------------------------------+------------------------------+");

            while(resultSet.next() == true)
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("|  %-15s|  %-33s|  %-28s|\n",id,name,specialization);
                System.out.println("+-----------------+-----------------------------------+------------------------------+");

            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public boolean get_doctors_by_id(int id)
    {
        String get_doctors_by_id_query = "select * from doctors where id=?";

        try
        {
            PreparedStatement preparedStatement=con.prepareStatement(get_doctors_by_id_query);
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
