package ru.milandr.courses;

//import com.sun.deploy.util.StringUtils;

import java.sql.*;
import java.util.*;

import java.lang.Object;

public class PostgreSQLJDBC {
    public static void main(String args[]) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/usera_database",
                            "dooly", "WakeMeUp_inside");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
//-------------------------------------------------------------------------
            stmt = c.createStatement();
            ResultSet rs1 = stmt.executeQuery( "SELECT * FROM users;" );
            ArrayList<Integer> id_list=new ArrayList<Integer>();

            while ( rs1.next() ) {

                int id = rs1.getInt("id");
                id_list.add(id);

            }

            Integer i = Collections.max(id_list);
            System.out.println("Maximum id is "+ i);
//--------------------------------------------------------------------------
            ResultSet rs2 = stmt.executeQuery( "SELECT * FROM users WHERE address_id = 2;" );
            System.out.println("\nPeople living at certain address:");
            while(rs2.next()){

                String first_name = rs2.getString("first_name");
                String last_name = rs2.getString("last_name");
                System.out.println(first_name+" "+last_name+"");

            }
//----------------------------------------------------------------------------
            ResultSet rs3 = stmt.executeQuery( "SELECT * FROM users ;" );
            ArrayList<String> nameList = new ArrayList<>();
            while(rs3.next()){
                String name = rs3.getString("last_name");
                nameList.add(name);
            }

            nameList.sort(String::compareToIgnoreCase);
            //sorry, I didn't know if it was necessary to print surnames
            //so I just printed in an ArrayList
            //hope, it won't be bad :3
            System.out.println(nameList+"\n");
//----------------------------------------------------------------------------
            ResultSet rs4 = stmt.executeQuery( "SELECT postal_code FROM addresses;" );
            ArrayList<Integer> indexs = new ArrayList<>();
            while(rs4.next()) {
                int index = 0;
                boolean numeric = true;
                try {
                    index = Integer.parseInt(rs4.getString("postal_code"));
                } catch (NumberFormatException e) {
                    numeric = false;
                }

                indexs.add(index);
            }
            int sum = 0;
            for(int j = 0; j < indexs.size(); j++)
                sum+= indexs.get(j);
            System.out.println("The medium number of postal addresses: "+ sum/indexs.size());
//----------------------------------------------------------------------------
            ResultSet rs5 = stmt.executeQuery("SELECT addresses.id FROM addresses " +
                    "RIGHT JOIN users " +
                    "ON users.address_id = addresses.id");

            Set<Integer> userAddresses = new HashSet<>();
            List<Integer> extraAddresses = new ArrayList<>();
            int size;
            PreparedStatement pstmt;

            while (rs5.next())
                userAddresses.add(rs5.getInt("id"));
            rs5 = stmt.executeQuery("SELECT id FROM addresses ");

            while (rs5.next()) {
                if (!userAddresses.contains(rs5.getInt("id")))
                    extraAddresses.add(rs5.getInt("id"));
            }

            size = extraAddresses.size();
            pstmt = c.prepareStatement(" SELECT * FROM addresses where id = ?");

            for (int k = 0; k < size; k++) {
                if (k == 0)
                    System.out.println("\nExtra addresses:");

                pstmt.setInt(1, extraAddresses.get(k));
                rs5 = pstmt.executeQuery();
                rs5.next();

                System.out.println(rs5.getString("address")
                        + "id = " + rs5.getString("id")
                        + ", city = " + rs5.getString("city")
                        + ", postal code = " + rs5.getString("postal_code"));
            }
//----------------------------------------------------------------------------
            rs1.close();
            rs2.close();
            rs3.close();
            rs4.close();
            rs5.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("COULD NOT OPEN DATABASE\n");
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
}