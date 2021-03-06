/*
 * Copyright (c) 2019 Handywedge Co.,Ltd.
 *
 * This software is released under the MIT License.
 *
 * http://opensource.org/licenses/mit-license.php
 */
package com.handywedge.config;

import com.handywedge.common.FWRuntimeException;

/**
 * プロパティファイルに設定されていないkeyを取得した場合にスローされる例外です。
 */
public class FWMissingResourceException extends FWRuntimeException {

  private static final long serialVersionUID = 1L;

  public FWMissingResourceException(String code, Object... args) {
    super(code, args);
  }

  public FWMissingResourceException(String code, Throwable cause, Object... args) {
    super(code, cause, args);
  }
}
