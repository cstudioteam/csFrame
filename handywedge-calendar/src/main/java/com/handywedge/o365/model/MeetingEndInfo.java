package com.handywedge.o365.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 会議終了情報クラス
 *
 * @author cstudioteam
 *
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonClassDescription("MeetingEndInfo")
public class MeetingEndInfo {
	/**
	 * 会議室ID
	 */
	@JsonProperty(value = "id", required = true)
	private String id;
	/**
	 * 予約者（SMTPメールアドレス）
	 */
	@JsonProperty(value = "subscriber")
	private String subscriber;
}
