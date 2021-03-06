/*
 * Copyright (c) 2019 Handywedge Co.,Ltd.
 *
 * This software is released under the MIT License.
 *
 * http://opensource.org/licenses/mit-license.php
 */
package com.handywedge.workflow;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
class FWWFIDManagement implements Serializable {

  private static final long serialVersionUID = 1L;
  private String wfId;
  private String statusCode;
  private Timestamp createDate;
  private Timestamp updateDate;

}
