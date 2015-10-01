package com.mcjtf.AuthPlus.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.bukkit.plugin.Plugin;

public class MySQL
  extends Database
{
  private final String user;
  private final String database;
  private final String password;
  private final String port;
  private final String hostname;
  private Connection connection;
  
  public MySQL(Plugin plugin, String hostname, String port, String database, String username, String password)
  {
    super(plugin);
    this.hostname = hostname;
    this.port = port;
    this.database = database;
    this.user = username;
    this.password = password;
    this.connection = null;
  }
  
  public Connection forceConnection()
    throws SQLException, ClassNotFoundException
  {
    Class.forName("com.mysql.jdbc.Driver");
    this.connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.user, this.password);
    return this.connection;
  }
  
  public Connection openConnection()
    throws SQLException, ClassNotFoundException
  {
    if (checkConnection()) {
      return this.connection;
    }
    Class.forName("com.mysql.jdbc.Driver");
    this.connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.user, this.password);
    return this.connection;
  }
  
  public boolean checkConnection()
    throws SQLException
  {
    return (this.connection != null) && (!this.connection.isClosed());
  }
  
  public Connection getConnection()
  {
    return this.connection;
  }
  
  public boolean closeConnection()
    throws SQLException
  {
    if (this.connection == null) {
      return false;
    }
    this.connection.close();
    return true;
  }
  
  public ResultSet querySQL(String query)
    throws SQLException, ClassNotFoundException
  {
    if (checkConnection()) {
      openConnection();
    }
    Statement statement = this.connection.createStatement();
    return statement.executeQuery(query);
  }
  
  public int updateSQL(String query)
    throws SQLException, ClassNotFoundException
  {
    if (checkConnection()) {
      openConnection();
    }
    Statement statement = this.connection.createStatement();
    return statement.executeUpdate(query);
  }
}

