package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.sql.*;

public class DbConnectionTest {

  @Test
  public void testDbConnectionGroup(){ //7.1.
    Connection conn = null;
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?user=root&password=");
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("SELECT * FROM group_list");
      Groups groups = new Groups();
      while (rs.next()) {
        groups.add(new GroupData().setId(rs.getInt("group_id"))
                .setName(rs.getString("group_name"))
                .setHeader(rs.getString("group_header"))
                .setFooter(rs.getString("group_footer")));
      }
      rs.close();
      st.close();
      conn.close();
      System.out.println(groups);

    } catch (SQLException ex) {
      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }

  @Test
  public void testDBconnectContacts(){
    Connection conn = null;

    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?user=root&password=");
      Statement st = conn.createStatement();
      ResultSet rS = st.executeQuery("SELECT id, firstname, lastname, nickname, company, email, " +
              "address, home, mobile, work FROM addressbook");
      Contacts contacts = new Contacts();
      while (rS.next()) {
        contacts.add(new ContactData().setId(rS.getInt("id"))
                .setFirstname(rS.getString("firstname")).setLastname(rS.getString("lastname"))
                .setNickname(rS.getString("nickname")).setCompany(rS.getString("company"))
                .setEmail(rS.getString("email")).setAddress(rS.getString("address"))
                .setHomePhone("home").setMobilePhone("mobile").setWorkPhone("work"));
      }
      rS.close();
      st.close();
      conn.close();
      System.out.println(contacts);

      // Do something with the Connection

    } catch (SQLException ex) {
      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }
}
