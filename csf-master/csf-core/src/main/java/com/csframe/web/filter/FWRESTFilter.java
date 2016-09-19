/*
 * Copyright (c) 2016 C Studio Co.,Ltd.
 *
 * This software is released under the MIT License.
 *
 * http://opensource.org/licenses/mit-license.php
 */
package com.csframe.web.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.csframe.common.FWStringUtil;
import com.csframe.context.FWFullRESTContext;
import com.csframe.log.FWLogger;
import com.csframe.log.FWMDC;
import com.csframe.user.auth.FWLoginManager;
import com.csframe.util.FWThreadLocal;

@WebFilter(filterName = "csf_rest_filter")
public class FWRESTFilter implements Filter {

  @Inject
  private FWLogger logger;

  @Inject
  private FWLoginManager loginMgr;

  @Inject
  private FWFullRESTContext restCtx;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    long startTime = logger.perfStart("doFilter");
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    httpServletRequest.getSession(true);// REST内ではセッションへのアクセスがなくinvalidateが出来ないので念のため作って最後に廃棄
    try {
      final String requestUrl = httpServletRequest.getRequestURI();

      if (httpServletRequest.getMethod().equalsIgnoreCase("POST")
          && (requestUrl.equals(restCtx.getContextPath() + "/csf/rest/api/token/pub")
              || requestUrl.equals(restCtx.getContextPath() + "/csf/rest/api/token/pub/"))) {
        logger.info("API Token publish request.");
      } else if (httpServletRequest.getMethod().equalsIgnoreCase("POST")
          && (requestUrl.equals(restCtx.getContextPath() + "/csf/rest/api/user")
              || requestUrl.equals(restCtx.getContextPath() + "/csf/rest/api/user/"))) {
        logger.info("User register request.");
      } else if (httpServletRequest.getMethod().equalsIgnoreCase("GET")
          && (requestUrl.equals(restCtx.getContextPath() + "/csf/rest/api/user/actual"))) {
        logger.info("User actual register request.");
      } else if (httpServletRequest.getMethod().equalsIgnoreCase("POST")
          && (requestUrl.equals(restCtx.getContextPath() + "/csf/rest/api/user/password/reset"))
          || requestUrl.equals(restCtx.getContextPath() + "/csf/rest/api/user/password/reset/")) {
        logger.info("password reset request.(POST)");
      } else if (httpServletRequest.getMethod().equalsIgnoreCase("GET")
          && requestUrl.equals(restCtx.getContextPath() + "/csf/rest/api/user/password/reset")) {
        logger.info("password reset request.(GET)");
      } else {
        String tokenHeader = httpServletRequest.getHeader("Authorization");
        logger.info("TOKEN:[{}]", tokenHeader);
        if (FWStringUtil.isEmpty(tokenHeader)) {
          Cookie cookies[] = httpServletRequest.getCookies();// HTTPヘッダにトークンがない場合はCookieから取得を試みる
          if (cookies != null) {
            for (Cookie ck : cookies) {
              logger.debug("COOKIE:[{}][{}]", ck.getName(), ck.getValue());
              if (ck.getName().equalsIgnoreCase("Authorization")) {
                tokenHeader = "Bearer " + ck.getValue();
              }
            }
          }
        }
        if (!FWStringUtil.isEmpty(tokenHeader)) { // トークン認証
          String token = FWStringUtil.splitBearerToken(tokenHeader);
          if (FWStringUtil.isEmpty(token) || !loginMgr.authAPIToken(token)) {
            logger.warn("APIToken Authorization false. Authorization={}", tokenHeader);
            httpServletResponse.setHeader("WWW-Authenticate", "Bearer error=\"invalid_token\"");
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
          } else {
            logger.info("APIToken Authorization true. token={}", token);
          }
        } else {
          logger.warn("APIToken Header nothing.");
          httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          return;
        }
      }
      FWMDC.put(FWMDC.USER_ID, restCtx.getUserId());
      chain.doFilter(httpServletRequest, httpServletResponse);
    } finally {
      logger.perfEnd("doFilter", startTime);
      FWThreadLocal.put(FWThreadLocal.LOGIN, false); // RESTは常にセッション破棄する
    }
  }

  @Override
  public void destroy() {}

}
