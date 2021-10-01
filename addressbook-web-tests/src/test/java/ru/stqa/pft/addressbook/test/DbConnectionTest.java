package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.sql.*;

public class DbConnectionTest {

  @Test
  public void testDbConnection(){ //7.1.
    Connection conn = null;
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?user=root&password=");
      Statement st1 = conn.createStatement();
      ResultSet rsG = st1.executeQuery("SELECT * FROM group_list");
      Groups groups = new Groups();
      while (rsG.next()) {
        groups.add(new GroupData().setId(rsG.getInt("group_id"))
                .setName(rsG.getString("group_name"))
                .setHeader(rsG.getString("group_header"))
                .setFooter(rsG.getString("group_footer")));
      }
      Statement st2 = conn.createStatement();
      ResultSet rsC = st2.executeQuery("SELECT user_id, lastname, firstname, email FROM users");
      Contacts contacts = new Contacts();
      while (rsC.next()) {
        contacts.add(new ContactData().setId(rsC.getInt("user_id"))
                .setLastname(rsC.getString("lastname"))
                .setFirstname(rsC.getString("firstname"))
                .setEmail(rsC.getString("email")));
      }
      rsG.close();
      rsC.close();
      st1.close();
      st2.close();
      conn.close();
      System.out.println(groups);
      System.out.println(contacts);

    } catch (SQLException ex) {
      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }
}
