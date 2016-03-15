/*
 * Copyright (c) 2016 C Studio Co.,Ltd.
 *
 * This software is released under the MIT License.
 *
 * http://opensource.org/licenses/mit-license.php
 */
package com.csframe.db;

import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.NClob;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Struct;

/**
 * Connectionのラッパーインターフェースです。<br>
 * DBへアクセスを行うにはFWConnectionManagerからFWConnectionインスタンスを取得してアクセスします。
 * 
 * @see FWConnectionManager
 * @see Connection
 */
public interface FWConnection {

  FWStatement createStatement() throws SQLException;

  FWPreparedStatement prepareStatement(String sql) throws SQLException;

  FWCallableStatement prepareCall(String sql) throws SQLException;

  String nativeSQL(String sql) throws SQLException;

  Clob createClob() throws SQLException;

  Blob createBlob() throws SQLException;

  NClob createNClob() throws SQLException;

  SQLXML createSQLXML() throws SQLException;

  Array createArrayOf(String typeName, Object[] elements) throws SQLException;

  Struct createStruct(String typeName, Object[] attributes) throws SQLException;

}
