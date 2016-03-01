package com.csframe.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.csframe.common.FWConstantCode;
import com.csframe.common.FWRuntimeException;

public class FWInternalConnectionManager {

  public static Connection getConnection() {
    DataSource dataSource;
    try {
      dataSource = (DataSource) InitialContext.doLookup("java:comp/env/jdbc/fw");
    } catch (NamingException e) {
      throw new FWRuntimeException(FWConstantCode.DS_LOOKUP_FAIL, e);
    }
    try {
      return dataSource.getConnection();
    } catch (SQLException e) {
      throw new FWRuntimeException(FWConstantCode.DB_FATAL, e);
    }
  }

}
