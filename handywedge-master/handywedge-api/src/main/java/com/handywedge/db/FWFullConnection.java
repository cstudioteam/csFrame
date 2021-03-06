/*
 * Copyright (c) 2019 Handywedge Co.,Ltd.
 *
 * This software is released under the MIT License.
 *
 * http://opensource.org/licenses/mit-license.php
 */
package com.handywedge.db;

import java.sql.DatabaseMetaData;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * フレームワーク内部で使用するインターフェースです。<br>
 * アプリケーションでは使用しないで下さい。
 */
public interface FWFullConnection extends FWConnection {

  /* Connectionメソッド */

  void setAutoCommit(boolean autoCommit) throws SQLException;

  boolean getAutoCommit() throws SQLException;

  void commit() throws SQLException;

  void rollback() throws SQLException;

  void close() throws SQLException;

  boolean isClosed() throws SQLException;

  DatabaseMetaData getMetaData() throws SQLException;

  void setReadOnly(boolean readOnly) throws SQLException;

  boolean isReadOnly() throws SQLException;

  void setCatalog(String catalog) throws SQLException;

  String getCatalog() throws SQLException;

  int TRANSACTION_NONE = 0;

  int TRANSACTION_READ_UNCOMMITTED = 1;

  int TRANSACTION_READ_COMMITTED = 2;

  int TRANSACTION_REPEATABLE_READ = 4;

  int TRANSACTION_SERIALIZABLE = 8;

  void setTransactionIsolation(int level) throws SQLException;

  int getTransactionIsolation() throws SQLException;

  SQLWarning getWarnings() throws SQLException;

  void clearWarnings() throws SQLException;

  FWStatement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException;

  FWPreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
      throws SQLException;

  FWCallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
      throws SQLException;

  java.util.Map<String, Class<?>> getTypeMap() throws SQLException;

  void setTypeMap(java.util.Map<String, Class<?>> map) throws SQLException;

  void setHoldability(int holdability) throws SQLException;

  int getHoldability() throws SQLException;

  Savepoint setSavepoint() throws SQLException;

  Savepoint setSavepoint(String name) throws SQLException;

  void rollback(Savepoint savepoint) throws SQLException;

  void releaseSavepoint(Savepoint savepoint) throws SQLException;

  FWStatement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
      throws SQLException;

  FWPreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException;

  FWCallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException;

  FWPreparedStatement prepareStatement(String sql, int columnIndexes[]) throws SQLException;

  FWPreparedStatement prepareStatement(String sql, String columnNames[]) throws SQLException;

  boolean isValid(int timeout) throws SQLException;

  void setClientInfo(String name, String value) throws SQLClientInfoException;

  void setClientInfo(Properties properties) throws SQLClientInfoException;

  String getClientInfo(String name) throws SQLException;

  Properties getClientInfo() throws SQLException;

  void setSchema(String schema) throws SQLException;

  String getSchema() throws SQLException;

  void abort(Executor executor) throws SQLException;

  void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException;

  int getNetworkTimeout() throws SQLException;

}
