/*
 * Copyright (c) 2016-2018 C Studio Co.,Ltd.
 *
 * This software is released under the MIT License.
 *
 * http://opensource.org/licenses/mit-license.php
 */
package com.handywedge.workflow;

import com.handywedge.common.FWException;

public class FWWFException extends FWException {

  private static final long serialVersionUID = 1L;

  public FWWFException(String code, Object... args) {
    super(code, args);
  }
}
