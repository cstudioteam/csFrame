/*
 * Copyright (c) 2016 C Studio Co.,Ltd.
 *
 * This software is released under the MIT License.
 *
 * http://opensource.org/licenses/mit-license.php
 */
package com.csframe.user.auth;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.csframe.common.FWConstantCode;
import com.csframe.common.FWPasswordUtil;
import com.csframe.common.FWRuntimeException;
import com.csframe.common.FWStringUtil;
import com.csframe.context.FWApplicationContext;
import com.csframe.db.FWConnection;
import com.csframe.db.FWConnectionManager;
import com.csframe.db.FWPreparedStatement;
import com.csframe.db.FWResultSet;
import com.csframe.db.FWTransactional;
import com.csframe.log.FWLogger;
import com.csframe.user.FWFullUser;
import com.csframe.util.FWThreadLocal;

@ApplicationScoped
public class FWLoginManagerImpl implements FWLoginManager {

  @Inject
  private FWConnectionManager cm;

  @Inject
  private FWFullUser user;

  @Inject
  private FWLogger logger;

  @Inject
  private FWApplicationContext appCtx;

  @FWTransactional(dataSourceName = "jdbc/fw")
  @Override
  public boolean login(String id, String password) {

    long startTime = logger.perfStart("login");

    if (!FWStringUtil.isEmpty(user.getId())) {
      logger.debug("login ok.(exist session)");
      logger.perfEnd("login", startTime);
      return true;
    }

    String dbPass = null;
    FWConnection con = cm.getConnection();
    try {
      dbPass = getPassword(id, con);
    } catch (SQLException e) {
      throw new FWRuntimeException(FWConstantCode.DB_FATAL, e);
    }
    if (dbPass == null) {
      logger.debug("user id not found.");
      logger.perfEnd("login", startTime);
      return false;
    }

    if (FWPasswordUtil.checkPassword(password, dbPass)) {
      setUser(id, con);
      logger.debug("login ok.");
      logger.perfEnd("login", startTime);
      return true;
    } else {
      logger.debug("login failed. no match password.");
      logger.perfEnd("login", startTime);
      return false;
    }
  }

  private String getPassword(String id, FWConnection con) throws SQLException {
    try (FWPreparedStatement ps =
        con.prepareStatement("SELECT * FROM fw_user_passwd WHERE id = ?");) {
      ps.setString(1, id);
      try (FWResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return rs.getString("passwd");
        } else {
          return null;
        }
      }
    }
  }

  @FWTransactional(dataSourceName = "jdbc/fw")
  @Override
  public boolean checkPassword(String id, String password) {

    long startTime = logger.perfStart("checkPassword");
    String dbPass = null;
    FWConnection con = cm.getConnection();
    try {
      dbPass = getPassword(id, con);
    } catch (SQLException e) {
      throw new FWRuntimeException(FWConstantCode.DB_FATAL, e);
    }
    if (dbPass == null) {
      logger.debug("user id not found.");
      logger.perfEnd("checkPassword", startTime);
      return false;
    }

    if (FWPasswordUtil.checkPassword(password, dbPass)) {
      logger.debug("check ok.");
      logger.perfEnd("checkPassword", startTime);
      return true;
    } else {
      logger.debug("check failed. no match password.");
      logger.perfEnd("checkPassword", startTime);
      return false;
    }
  }

  @Override
  public void logout() {

    FWThreadLocal.put(FWThreadLocal.LOGIN, false); // ログアウトリクエストフラグ
  }

  @FWTransactional(dataSourceName = "jdbc/fw")
  @Override
  public String publishAPIToken(String id) {

    long startTime = logger.perfStart("publishAPIToken");
    String token = null;
    logger.debug("generate token start.");
    try {
      // 下記で選択されるNativePRNGBlockingでは、Linux環境だとものすごい遅い（30秒以上かかる）
      // SecureRandom sr = SecureRandom.getInstanceStrong();
      SecureRandom sr = SecureRandom.getInstance("NativePRNGNonBlocking");
      logger.debug("token Algorithme={}", sr.getAlgorithm());
      byte[] b = new byte[16];
      sr.nextBytes(b);
      StringBuilder buf = new StringBuilder();
      for (int i = 0; i < b.length; i++) {
        buf.append(String.format("%02x", b[i]));
      }
      token = buf.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new FWRuntimeException(FWConstantCode.FATAL, e);
    }
    logger.debug("generate token end.");

    FWPreparedStatement ps = null;
    FWResultSet rs = null;
    FWConnection con = cm.getConnection();
    try {
      ps = con.prepareStatement("SELECT id FROM fw_api_token WHERE id = ?");
      ps.setString(1, id);
      rs = ps.executeQuery();
      boolean update = rs.next();
      ps.close();
      ps = null;
      if (update) {
        logger.debug("token update.");
        ps = con
            .prepareStatement("UPDATE fw_api_token SET token = ?, create_date = ? WHERE id = ?");
        ps.setString(1, token);
        ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        ps.setString(3, id);
        ps.executeUpdate();
      } else {
        logger.debug("token insert.");
        ps = con.prepareStatement("INSERT INTO fw_api_token VALUES(?, ?, ?)");
        ps.setString(1, id);
        ps.setString(2, token);
        ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        ps.executeUpdate();
      }
      appCtx.getTokenMap().put(id, token);
    } catch (SQLException e) {
      throw new FWRuntimeException(FWConstantCode.DB_FATAL);
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
      } catch (Exception e) {
      }
      try {
        if (ps != null) {
          ps.close();
        }
      } catch (Exception e) {
      }
    }
    logger.debug("publishAPIToken end. token={}", token);
    logger.perfEnd("publishAPIToken", startTime);
    return token;
  }

  @Override
  public String getAPIToken(String id) {
    long startTime = logger.perfStart("getAPIToken");
    String token = appCtx.getTokenMap().get(id);
    logger.debug("getAPIToken end. id={}, token={}", id, token);
    logger.perfEnd("getAPIToken", startTime);
    return token;
  }

  @FWTransactional(dataSourceName = "jdbc/fw")
  @Override
  public void removeAPIToken(String id) {

    long startTime = logger.perfStart("removeAPIToken");
    FWPreparedStatement ps = null;
    FWConnection con = cm.getConnection();
    try {
      ps = con.prepareStatement("DELETE FROM fw_api_token WHERE id = ?");
      ps.setString(1, id);
      ps.executeUpdate();
      appCtx.getTokenMap().remove(id);
    } catch (SQLException e) {
      throw new FWRuntimeException(FWConstantCode.DB_FATAL);
    } finally {
      try {
        if (ps != null) {
          ps.close();
        }
      } catch (Exception e) {
      }
    }
    logger.perfEnd("removeAPIToken", startTime);
  }

  @FWTransactional(dataSourceName = "jdbc/fw")
  @Override
  public boolean authAPIToken(String token) {
    long startTime = logger.perfStart("authAPIToken");

    Map<String, String> tokenMap = appCtx.getTokenMap();
    Set<Entry<String, String>> entrySet = tokenMap.entrySet();
    Iterator<Entry<String, String>> it = entrySet.iterator();

    String id = null;
    while (it.hasNext()) {
      Entry<String, String> e = it.next();
      if (e.getValue().equals(token)) {
        id = e.getKey();
        break;
      }
    }

    if (FWStringUtil.isEmpty(id)) {
      logger.info("invalid_token.");
      logger.perfEnd("authAPIToken", startTime);
      return false;
    }

    setUser(id, cm.getConnection());

    logger.debug("authAPIToken ok.");
    logger.perfEnd("authAPIToken", startTime);
    return true;
  }

  private void setUser(String id, FWConnection con) {

    // セッションオブジェクトにユーザー情報をセット
    FWPreparedStatement ps = null;
    FWResultSet rs = null;
    try {
      ps = con.prepareStatement("SELECT * FROM fw_user WHERE id = ?");
      ps.setString(1, id);
      rs = ps.executeQuery();
      if (rs.next()) {
        user.setId(id);
        user.setName(rs.getString("name"));
        user.setRole(rs.getString("role"));
        String lang = rs.getString("language");
        if (!FWStringUtil.isEmpty(lang)) {
          String country = rs.getString("country");
          user.setLocale(new Locale.Builder().setLanguage(lang).setRegion(country).build());
        }
        user.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
        FWThreadLocal.put(FWThreadLocal.LOGIN, true); // ログインリクエストフラグ。フィルタで処理をする。
      } else {
        throw new FWRuntimeException(FWConstantCode.FATAL, "ユーザー情報が取得できません。"); // 基本的に来ないはず
      }
    } catch (SQLException e) {
      throw new FWRuntimeException(FWConstantCode.DB_FATAL, e);
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
      } catch (Exception e) {
      }
      try {
        if (ps != null) {
          ps.close();
        }
      } catch (Exception e) {
      }
    }

    // 最終ログイン時間更新
    try {
      ps = con.prepareStatement("UPDATE fw_user SET last_login_date = ? WHERE id = ?");
      ps.setTimestamp(1, user.getLastLoginTime());
      ps.setString(2, user.getId());
      int i = ps.executeUpdate();
      if (i == 1) {
        logger.debug("update 'last_login_date'.");
      } else {
        logger.warn("fail update 'last_login_date'.");
      }
    } catch (SQLException e) {
      logger.warn("fail update 'last_login_date'.", e);
    } finally {
      try {
        if (ps != null) {
          ps.close();
        }
      } catch (Exception e) {
      }
    }
  }
}
