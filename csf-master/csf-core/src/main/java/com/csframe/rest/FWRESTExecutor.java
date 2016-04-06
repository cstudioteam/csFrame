/*
 * Copyright (c) 2016 C Studio Co.,Ltd.
 *
 * This software is released under the MIT License.
 *
 * http://opensource.org/licenses/mit-license.php
 */
package com.csframe.rest;

import java.io.InputStream;
import java.lang.reflect.Method;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.csframe.cdi.FWBeanManager;
import com.csframe.common.FWConstantCode;
import com.csframe.common.FWException;
import com.csframe.common.FWStringUtil;
import com.csframe.config.FWMessageResources;
import com.csframe.log.FWLogger;
import com.csframe.log.FWLoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/")
@ApplicationScoped
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class FWRESTExecutor {

  private FWLogger logger = FWLoggerFactory.getLogger(FWRESTExecutor.class);


  @POST
  @Path("/{logicClass}")
  public Response doPost(@PathParam("logicClass") String logicClass, InputStream in) {

    logger.info("REST doPost start. class={}", logicClass);
    try {
      Class<?> logicClazz = getLogicClazz(logicClass);
      if (logicClazz == null) {
        return Response.ok(createError()).build();
      }
      FWRESTController logic = (FWRESTController) FWBeanManager.getBean(logicClazz);
      Method method = logicClazz.getMethod("doPost", FWRESTRequest.class);
      FWRESTRequestClass annotation = method.getAnnotation(FWRESTRequestClass.class);
      Class<?> requestClazz = annotation.value();

      ObjectMapper om = new ObjectMapper();
      FWRESTRequest req = (FWRESTRequest) om.readValue(in, requestClazz);
      logger.debug(req.toString());
      FWRESTResponse res = logic.doPost(req);
      logger.debug(res.toString());
      logger.info("REST doPost end.");
      return Response.ok(res).build();
    } catch (Exception e) {
      logger.error("予期しないエラーが発生しました。", e);
      FWRESTErrorResponse res = new FWRESTErrorResponse();
      res.setReturn_cd(FWConstantCode.FW_REST_ERROR);
      res.setReturn_msg(e.getMessage());
      return Response.ok(res).build();
    }
  }

  @GET
  @Path("/{logicClass}/{param}")
  public Response doGet(@PathParam("logicClass") String logicClass,
      @PathParam("param") String param) {

    logger.info("REST doGet start. class={}", logicClass);
    try {
      Class<?> logicClazz = getLogicClazz(logicClass);
      if (logicClazz == null) {
        return Response.ok(createError()).build();
      }
      FWRESTController logic = (FWRESTController) FWBeanManager.getBean(logicClazz);

      logger.debug("get parameter={}", param);
      FWRESTResponse res = logic.doGet(param);
      logger.debug(res.toString());
      logger.info("REST doGet end.");
      return Response.ok(res).build();
    } catch (Exception e) {
      logger.error("予期しないエラーが発生しました。", e);
      FWRESTErrorResponse res = new FWRESTErrorResponse();
      res.setReturn_cd(FWConstantCode.FW_REST_ERROR);
      res.setReturn_msg(e.getMessage());
      return Response.ok(res).build();
    }
  }

  @PUT
  @Path("/{logicClass}")
  public Response doPut(@PathParam("logicClass") String logicClass, InputStream in) {

    logger.info("REST doPut start. class=" + logicClass);
    try {
      Class<?> logicClazz = getLogicClazz(logicClass);
      if (logicClazz == null) {
        return Response.ok(createError()).build();
      }
      FWRESTController logic = (FWRESTController) FWBeanManager.getBean(logicClazz);
      Method method = logicClazz.getMethod("doPut", FWRESTRequest.class);
      FWRESTRequestClass annotation = method.getAnnotation(FWRESTRequestClass.class);
      Class<?> requestClazz = annotation.value();

      ObjectMapper om = new ObjectMapper();
      FWRESTRequest req = (FWRESTRequest) om.readValue(in, requestClazz);
      logger.debug(req.toString());
      FWRESTResponse res = logic.doPut(req);
      logger.debug(res.toString());
      logger.info("REST doPut end.");
      return Response.ok(res).build();
    } catch (Exception e) {
      logger.error("予期しないエラーが発生しました。", e);
      FWRESTErrorResponse res = new FWRESTErrorResponse();
      res.setReturn_cd(FWConstantCode.FW_REST_ERROR);
      res.setReturn_msg(e.getMessage());
      return Response.ok(res).build();
    }
  }

  @DELETE
  @Path("/{logicClass}")
  public Response doDelete(@PathParam("logicClass") String logicClass, InputStream in) {

    logger.info("REST doDelete start. class=" + logicClass);
    try {
      Class<?> logicClazz = getLogicClazz(logicClass);
      if (logicClazz == null) {
        return Response.ok(createError()).build();
      }
      FWRESTController logic = (FWRESTController) FWBeanManager.getBean(logicClazz);
      Method method = logicClazz.getMethod("doDelete", FWRESTRequest.class);
      FWRESTRequestClass annotation = method.getAnnotation(FWRESTRequestClass.class);
      Class<?> requestClazz = annotation.value();

      ObjectMapper om = new ObjectMapper();
      FWRESTRequest req = (FWRESTRequest) om.readValue(in, requestClazz);
      logger.debug(req.toString());
      FWRESTResponse res = logic.doDelete(req);
      logger.debug(res.toString());
      logger.info("REST doDelete end.");
      return Response.ok(res).build();
    } catch (Exception e) {
      logger.error("予期しないエラーが発生しました。", e);
      FWRESTErrorResponse res = new FWRESTErrorResponse();
      res.setReturn_cd(FWConstantCode.FW_REST_ERROR);
      res.setReturn_msg(e.getMessage());
      return Response.ok(res).build();
    }
  }

  private Class<?> getLogicClazz(String path) throws ClassNotFoundException {

    Class<?> logicClazz = null;
    try {
      logicClazz = Class.forName(path);
    } catch (ClassNotFoundException e) {
    }

    if (logicClazz == null) {
      FWMessageResources config = FWBeanManager.getBean(FWMessageResources.class);
      String logicClazzName = config.get("fw.rest." + path);
      if (!FWStringUtil.isEmpty(logicClazzName)) {
        logicClazz = Class.forName(logicClazzName);// ここでNotFoundは予期しないエラーにしておく
        logger.info("actual logicClass={}", logicClazzName);
      }
    }
    return logicClazz;
  }

  private FWRESTResponse createError() {
    FWException e = new FWException(String.valueOf(FWConstantCode.FW_REST_ROOTING_ERROR));
    FWRESTResponse res = new FWRESTErrorResponse();
    res.setReturn_cd(FWConstantCode.FW_REST_ROOTING_ERROR);
    res.setReturn_msg(e.getMessage());
    return res;
  }
}
