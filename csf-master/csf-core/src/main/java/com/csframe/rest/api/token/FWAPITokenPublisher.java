/*
 * Copyright (c) 2016 C Studio Co.,Ltd.
 *
 * This software is released under the MIT License.
 *
 * http://opensource.org/licenses/mit-license.php
 */
package com.csframe.rest.api.token;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.csframe.cdi.FWBeanManager;
import com.csframe.common.FWConstantCode;
import com.csframe.common.FWException;
import com.csframe.common.FWStringUtil;
import com.csframe.context.FWRESTContext;
import com.csframe.log.FWLogger;
import com.csframe.log.FWLoggerFactory;
import com.csframe.user.auth.FWLoginManager;

@RequestScoped
@Path("/token")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class FWAPITokenPublisher {

  private FWLoginManager loginMrg;

  private FWLogger logger = FWLoggerFactory.getLogger(FWAPITokenPublisher.class);

  private FWRESTContext restCtx;

  @PostConstruct
  public void init() {
    loginMrg = FWBeanManager.getBean(FWLoginManager.class);
    restCtx = FWBeanManager.getBean(FWRESTContext.class);
  }

  @POST
  @Path("/pub")
  public Response publish(FWAPITokenRequest request) {
    logger.info("publish start. args={}", request);

    FWAPITokenResponse res = new FWAPITokenResponse();
    try {
      if (request == null || FWStringUtil.isEmpty(request.getId())
          || FWStringUtil.isEmpty(request.getPassword())) {
        FWException e = new FWException(String.valueOf(FWConstantCode.FW_REST_TOKENPUB_INVALID));
        logger.warn(e.getMessage());
        res.setReturn_cd(FWConstantCode.FW_REST_TOKENPUB_INVALID);
        res.setReturn_msg(e.getMessage());
      } else {
        if (loginMrg.checkPassword(request.getId(), request.getPassword())) {
          String token = loginMrg.publishAPIToken(request.getId());
          res.setReturn_cd(0);
          res.setToken(token);
        } else {
          FWException e =
              new FWException(String.valueOf(FWConstantCode.FW_REST_TOKENPUB_UNAUTHORIZED));
          logger.warn(e.getMessage());
          res.setReturn_cd(FWConstantCode.FW_REST_TOKENPUB_UNAUTHORIZED);
          res.setReturn_msg(e.getMessage());
        }
      }
    } catch (Exception e) {
      logger.error("予期しないエラーが発生しました。", e);
      res = createError(e.getMessage());
    }
    logger.info("publish end. res={}", res);
    return Response.ok(res).build();
  }

  @DELETE
  @Path("/delete")
  public Response delete() {
    logger.info("delete start.");
    FWAPITokenResponse res = new FWAPITokenResponse();
    try {
      loginMrg.removeAPIToken(restCtx.getUserId());
      res.setReturn_cd(0);
    } catch (Exception e) {
      logger.error("予期しないエラーが発生しました。", e);
      res = createError(e.getMessage());
    }

    logger.info("delete end. res={}", res);
    return Response.ok(res).build();
  }

  /* トークンの有効性はフィルターでチェックされるのでコード0を返すだけ */
  @GET
  @Path("/validate")
  public Response validate() {
    logger.info("validate start. token={}", restCtx.getToken());
    FWAPITokenResponse res = new FWAPITokenResponse();
    res.setReturn_cd(0);
    logger.info("validate end.");
    return Response.ok(res).build();
  }

  private FWAPITokenResponse createError(String args) {
    FWException e = new FWException(String.valueOf(FWConstantCode.FW_REST_ERROR), args);
    FWAPITokenResponse res = new FWAPITokenResponse();
    res.setReturn_cd(FWConstantCode.FW_REST_ERROR);
    res.setReturn_msg(e.getMessage());
    return res;
  }
}
