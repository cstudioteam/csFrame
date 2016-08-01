/*
 * Copyright (c) 2016 C Studio Co.,Ltd.
 *
 * This software is released under the MIT License.
 *
 * http://opensource.org/licenses/mit-license.php
 */
package com.csframe.role;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FWAction implements Serializable {

  private static final long serialVersionUID = 1L;
  private String action_code;
  private String action;
  private String pre_status;
  private String post_status;
}
