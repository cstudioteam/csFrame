/*
 * Copyright (c) 2019 Handywedge Co.,Ltd.
 *
 * This software is released under the MIT License.
 *
 * http://opensource.org/licenses/mit-license.php
 */
package com.handywedge.openidconnect.login;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.handywedge.cdi.FWBeanManager;
import com.handywedge.common.FWConstantCode;
import com.handywedge.common.FWException;
import com.handywedge.common.FWStringUtil;
import com.handywedge.log.FWLogger;
import com.handywedge.log.FWLoggerFactory;
import com.handywedge.rest.api.token.FWAPITokenResponse;
import com.handywedge.user.FWInnerUserService;
import com.handywedge.user.FWUser;

@Path("/")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class FWOICAPITokenPublisher {

  private FWOICUserManager userMgr;
  private FWInnerUserService userService;
  private FWLogger logger = FWLoggerFactory.getLogger(FWOICAPITokenPublisher.class);

  @PostConstruct
  public void init() {
    userMgr = FWBeanManager.getBean(FWOICUserManager.class);
    userService = FWBeanManager.getBean(FWInnerUserService.class);
  }

  @POST
  @Path("/login")
  public Response publish(FWOICAPITokenRequest request) {
    logger.info("publish start. args={}", request);

    FWAPITokenResponse res = new FWAPITokenResponse();
    try {
      if (request == null || FWStringUtil.isEmpty(request.getId())
          || FWStringUtil.isEmpty(request.getProvider())) {
        FWException e = new FWException(String.valueOf(FWConstantCode.FW_REST_OIC_LOGIN_BAD_REQUEST));
        logger.warn(e.getMessage());
        res.setReturn_cd(FWConstantCode.FW_REST_OIC_LOGIN_BAD_REQUEST);
        res.setReturn_msg(e.getMessage());
      } else {
        String token = userMgr.login(request.getId(), request.getName(), request.getMail_address(),
            request.getProvider());
        FWUser user = userService.getUserByToken(token);
        res.getUser().setId(user.getId());
        res.getUser().setName(user.getName());
        res.getUser().setRole(user.getRole());
        res.getUser().setRoleName(user.getRoleName());
        res.setReturn_cd(0);
        res.setToken(token);
      }
    } catch (Exception e) {
      logger.error("予期しないエラーが発生しました。", e);
      res = createError(e.getMessage());
    }
    logger.info("publish end. res={}", res);
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
