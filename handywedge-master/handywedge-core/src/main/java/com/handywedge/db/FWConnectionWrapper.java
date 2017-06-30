/*
 * Copyright (c) 2016-2017 C Studio Co.,Ltd.
 *
 * This software is released under the MIT License.
 *
 * http://opensource.org/licenses/mit-license.php
 */
package com.handywedge.db;

import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import com.handywedge.db.FWFullConnection;
import com.handywedge.db.FWPreparedStatement;
import com.handywedge.db.FWStatement;

// Connectionのメソッドを隠蔽したアプリケーション利用クラス
public class FWConnectionWrapper implements FWFullConnection {

  private Connection connection;

  FWConnectionWrapper(Connection connection) {

    this.connection = connection;
  }

  /* ラッパーメソッド */

  @Override
  public FWStatement createStatement() throws SQLException {

    return new FWStatementWrapper(connection.createStatement());
  }

  @Override
  public FWPreparedStatement prepareStatement(String sql) throws SQLException {

    return new FWPreparedStatementWrapper(connection.prepareStatement(sql));
  }

  @Override
  public FWCallableStatementWrapper prepareCall(String sql) throws SQLException {

    return new FWCallableStatementWrapper(connection.prepareCall(sql));
  }

  @Override
  public String nativeSQL(String sql) throws SQLException {

    return connection.nativeSQL(sql);
  }

  @Override
  public boolean getAutoCommit() throws SQLException {

    return connection.getAutoCommit();
  }

  @Override
  public DatabaseMetaData getMetaData() throws SQLException {

    return connection.getMetaData();
  }

  @Override
  public String getCatalog() throws SQLException {

    return connection.getCatalog();
  }

  @Override
  public void setTransactionIsolation(int level) throws SQLException {

    connection.setTransactionIsolation(level);
  }

  @Override
  public int getTransactionIsolation() throws SQLException {

    return connection.getTransactionIsolation();
  }

  @Override
  public FWStatement createStatement(int resultSetType, int resultSetConcurrency)
      throws SQLException {

    return new FWStatementWrapper(connection.createStatement(resultSetType, resultSetConcurrency));
  }

  @Override
  public FWPreparedStatement prepareStatement(String sql, int resultSetType,
      int resultSetConcurrency) throws SQLException {

    return new FWPreparedStatementWrapper(
        connection.prepareStatement(sql, resultSetType, resultSetConcurrency));
  }

  @Override
  public FWCallableStatementWrapper prepareCall(String sql, int resultSetType,
      int resultSetConcurrency) throws SQLException {

    return new FWCallableStatementWrapper(
        connection.prepareCall(sql, resultSetType, resultSetConcurrency));
  }

  @Override
  public Map<String, Class<?>> getTypeMap() throws SQLException {

    return connection.getTypeMap();
  }

  @Override
  public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

    connection.setTypeMap(map);
  }

  @Override
  public FWStatement createStatement(int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {

    return new FWStatementWrapper(
        connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
  }

  @Override
  public FWPreparedStatement prepareStatement(String sql, int resultSetType,
      int resultSetConcurrency, int resultSetHoldability) throws SQLException {

    return new FWPreparedStatementWrapper(connection.prepareStatement(sql, resultSetType,
        resultSetConcurrency, resultSetHoldability));
  }

  @Override
  public FWCallableStatementWrapper prepareCall(String sql, int resultSetType,
      int resultSetConcurrency, int resultSetHoldability) throws SQLException {

    return new FWCallableStatementWrapper(
        connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
  }

  @Override
  public FWPreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
      throws SQLException {

    return new FWPreparedStatementWrapper(connection.prepareStatement(sql, autoGeneratedKeys));
  }

  @Override
  public FWPreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {

    return new FWPreparedStatementWrapper(connection.prepareStatement(sql, columnIndexes));
  }

  @Override
  public FWPreparedStatement prepareStatement(String sql, String[] columnNames)
      throws SQLException {

    return new FWPreparedStatementWrapper(connection.prepareStatement(sql, columnNames));
  }

  @Override
  public Clob createClob() throws SQLException {

    return connection.createClob();
  }

  @Override
  public Blob createBlob() throws SQLException {

    return connection.createBlob();
  }

  @Override
  public NClob createNClob() throws SQLException {

    return connection.createNClob();
  }

  @Override
  public SQLXML createSQLXML() throws SQLException {

    return connection.createSQLXML();
  }

  @Override
  public Array createArrayOf(String typeName, Object[] elements) throws SQLException {

    return connection.createArrayOf(typeName, elements);
  }

  @Override
  public Struct createStruct(String typeName, Object[] attributes) throws SQLException {

    return connection.createStruct(typeName, attributes);
  }

  @Override
  public void setSchema(String schema) throws SQLException {

    connection.setSchema(schema);
  }

  @Override
  public String getSchema() throws SQLException {

    return connection.getSchema();
  }

  @Override
  public int getNetworkTimeout() throws SQLException {

    return connection.getNetworkTimeout();
  }

  public <T> T unwrap(Class<T> iface) throws SQLException {

    return connection.unwrap(iface);
  }

  public boolean isWrapperFor(Class<?> iface) throws SQLException {

    return connection.isWrapperFor(iface);
  }

  @Override
  public void setAutoCommit(boolean autoCommit) throws SQLException {

    connection.setAutoCommit(autoCommit);
  }

  @Override
  public void commit() throws SQLException {

    connection.commit();
  }

  @Override
  public void rollback() throws SQLException {

    connection.rollback();
  }

  @Override
  public void close() throws SQLException {

    connection.close();
  }

  @Override
  public boolean isClosed() throws SQLException {

    return connection.isClosed();
  }

  @Override
  public void setReadOnly(boolean readOnly) throws SQLException {

    connection.setReadOnly(readOnly);
  }

  @Override
  public boolean isReadOnly() throws SQLException {

    return connection.isReadOnly();
  }

  @Override
  public void setCatalog(String catalog) throws SQLException {

    connection.setCatalog(catalog);
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {

    return connection.getWarnings();
  }

  @Override
  public void clearWarnings() throws SQLException {

    connection.clearWarnings();
  }

  @Override
  public void setHoldability(int holdability) throws SQLException {

    connection.setHoldability(holdability);
  }

  @Override
  public int getHoldability() throws SQLException {

    return connection.getHoldability();
  }

  @Override
  public Savepoint setSavepoint() throws SQLException {

    return connection.setSavepoint();
  }

  @Override
  public Savepoint setSavepoint(String name) throws SQLException {

    return connection.setSavepoint(name);
  }

  @Override
  public void rollback(Savepoint savepoint) throws SQLException {

    connection.rollback(savepoint);
  }

  @Override
  public void releaseSavepoint(Savepoint savepoint) throws SQLException {

    connection.releaseSavepoint(savepoint);
  }

  @Override
  public boolean isValid(int timeout) throws SQLException {

    return connection.isValid(timeout);
  }

  @Override
  public void setClientInfo(String name, String value) throws SQLClientInfoException {

    connection.setClientInfo(name, value);
  }

  @Override
  public void setClientInfo(Properties properties) throws SQLClientInfoException {

    connection.setClientInfo(properties);
  }

  @Override
  public String getClientInfo(String name) throws SQLException {

    return connection.getClientInfo(name);
  }

  @Override
  public Properties getClientInfo() throws SQLException {

    return connection.getClientInfo();
  }

  @Override
  public void abort(Executor executor) throws SQLException {

    connection.abort(executor);
  }

  @Override
  public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

    connection.setNetworkTimeout(executor, milliseconds);
  }

}
