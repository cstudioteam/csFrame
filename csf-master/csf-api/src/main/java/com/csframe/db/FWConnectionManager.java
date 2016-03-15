/*
 * Copyright (c) 2016 C Studio Co.,Ltd.
 *
 * This software is released under the MIT License.
 *
 * http://opensource.org/licenses/mit-license.php
 */
package com.csframe.db;

/**
 * DBコネクションを生成するインターフェースです。<br>
 * DBへアクセスするにはgetConnectionメソッドからFWConnectionインスタンスを取得してアクセスします。 <br>
 * 
 * <pre>
 * 
 * {@code   @Inject}
 * {@code   private FWConnectionManager mgr;
 * ・・・
 *    FWConnection con = mgr.getConnection();
 *    FWPreparedStatement ps = con.prepareStatement("select * from hoge where id = ?");
 * ・・・
 * }
 * 
 * </pre>
 */
public interface FWConnectionManager {

  /**
   * DBコネクションを返します。
   * 
   * @return DBコネクション
   */
  FWConnection getConnection();
}
