/*
 * Copyright (c) 2016 C Studio Co.,Ltd.
 *
 * This software is released under the MIT License.
 *
 * http://opensource.org/licenses/mit-license.php
 */
package com.csframe.common;

import com.csframe.cdi.FWBeanManager;
import com.csframe.config.FWMessageResources;
import com.csframe.context.FWContext;

// TODO 合成文字「"か" + '\u3099'」とかは考慮しない
public class FWStringUtil {

  public static boolean isEmpty(String src) {

    return src == null || src.trim().length() == 0;
  }

  public static String getLoginUrl() {

    FWMessageResources resources = FWBeanManager.getBean(FWMessageResources.class);
    FWContext ctx = FWBeanManager.getBean(FWContext.class);

    String contextPath = ctx.getContextPath();
    if (contextPath.endsWith("/")) {
      contextPath = contextPath.substring(0, contextPath.length() - 1);
    }
    String loginUrl = resources.get(FWMessageResources.LOGIN_URL);
    if (!loginUrl.startsWith("/")) {
      loginUrl = "/" + loginUrl;
    }
    return contextPath + loginUrl;
  }

  public static String splitBearerToken(String tokenHeader) {
    String token = null;
    String[] bearerToken = tokenHeader.split(" ");
    if (bearerToken.length == 2 && bearerToken[0].equals("Bearer")) {
      token = bearerToken[1];
    }
    return token;
  }

  /**
   * 引数の文字列がnullもしくは空文字の場合、置換文字に置換する。
   *
   * @param src 対象文字列
   * @param replace 置換文字列
   * @return 対象文字列そのまま、もしくは置換文字列
   */
  public static String replaceNullString(String src, String replace) {

    if (isEmpty(src)) {
      return replace;
    } else {
      return src;
    }
  }
}
