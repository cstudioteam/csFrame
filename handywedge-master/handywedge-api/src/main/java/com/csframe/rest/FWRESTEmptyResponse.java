/*
 * Copyright (c) 2016 C Studio Co.,Ltd.
 *
 * This software is released under the MIT License.
 *
 * http://opensource.org/licenses/mit-license.php
 */
package com.csframe.rest;

/**
 * 共通属性以外を持たないJSONレスポンスクラスです。
 */
public class FWRESTEmptyResponse extends FWRESTResponse {

  @Override
  public String toString() {
    return "FWRESTEmptyResponse [getReturn_cd()=" + getReturn_cd() + ", getReturn_msg()="
        + getReturn_msg() + "]";
  }

}
