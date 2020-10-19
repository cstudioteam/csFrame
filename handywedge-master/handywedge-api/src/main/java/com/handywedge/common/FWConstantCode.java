/*
 * Copyright (c) 2019 Handywedge Co.,Ltd.
 *
 * This software is released under the MIT License.
 *
 * http://opensource.org/licenses/mit-license.php
 */
package com.handywedge.common;

/**
 * handywedgeのエラーコードとREST機能のリターンコードを定義するインターフェースです。<br>
 * エラーコードの末尾はF=Fatal、E=Exceptionを示し、Fatalはシステムで予期しないエラーとなります。<br>
 */
public interface FWConstantCode {

  /**
   * 補足できない想定外のエラーが発生。
   */
  String FATAL = "00001F";

  /**
   * web.xmlにhandywedge.app_idの設定がない。
   */
  String NO_APP_ID = "00002E";

  /**
   * セッションタイムアウト。
   */
  String SESSION_TIMEOUT = "00003E";

  /**
   * 予期しないDB関連のエラー。
   */
  String DB_FATAL = "00101F";

  /**
   * データソースのlookupに失敗。
   */
  String DS_LOOKUP_FAIL = "00102E";

  /**
   * プロパティファイルに指定されたkeyが存在しない。
   */
  @Deprecated
  String PROPERTIES_KEY_MISSING = "00201E";

  /**
   * 帳票テンプレートファイルが見つからない。
   */
  String PDF_TEMPLATE_FILE_MISSING = "00301E";

  /**
   * 帳票テンプレートファイルの読み込みエラー。
   */
  String PDF_TEMPLATE_LOAD_FAIL = "00302E";

  /**
   * PDF生成時にエラー。
   */
  String PDF_OUTPUT_FAIL = "00303E";

  /**
   * 帳票データがセットされていない。
   */
  String PDF_DATA_MISSING = "00304E";

  /**
   * 暗号処理でエラー。
   */
  String CIPHER_ENCRYPT_FAIL = "00401E";

  /**
   * 復号処理でエラー。
   */
  String CIPHER_DECRYPT_FAIL = "00402E";

  /**
   * SMTPサーバーのホスト名設定がない。
   */
  String MAIL_HOST_MISSING = "00501E";

  /**
   * メール宛先アドレスのパラメータがない。
   */
  String MAIL_ADDR_MISSING = "00502E";

  /**
   * メール添付ファイルがサイズオーバー。
   */
  String MAIL_FILE_SIZE_OVER = "00503E";

  /**
   * メール送信処理でエラー。
   */
  String MAIL_SEND_FAIL = "00504E";

  /**
   * ロールに対してアクションする権限がない。
   */
  String ROLE_UNAUTHORIZED = "00601E";

  /**
   * 変換元のファイルが読み込めない。
   */
  String OFFICE_TO_PDF_UNREAD = "00701E";

  /**
   * サポートしていないファイル形式。
   */
  String OFFICE_TO_PDF_UNSUPPORTED = "00702E";

  /**
   * 変換に失敗。
   */
  String OFFICE_TO_PDF_FAIL = "00703E";

  /**
   * 変換元のファイルが読み込めない。
   */
  String PDF_TO_SVG_UNREAD = "00801E";

  /**
   * サポートしていないファイル形式。
   */
  String PDF_TO_SVG_UNSUPPORTED = "00802E";

  /**
   * 変換に失敗。
   */
  String PDF_TO_SVG_FAIL = "00803E";

  /**
   * 変換処理でタイムアウト。
   */
  String PDF_TO_SVG_TIMEOUT = "00804E";

  /* REST機能のFWコード */

  /**
   * 予期しないエラー。
   */
  int FW_REST_ERROR = -9000;

  /**
   * サポートされていないHTTPメソッド。
   */
  int FW_REST_UNSUPPORTED = -9001;

  /**
   * リクエストされたパス・クラス名が存在しない。
   */
  int FW_REST_ROUTING_ERROR = -9002;

  /**
   * ユーザーID・パスワード認証に失敗しトークン発行ができない。
   */
  int FW_REST_TOKENPUB_UNAUTHORIZED = -9003;

  /**
   * パラメータが不足している。
   */
  int FW_REST_TOKENPUB_INVALID = -9004;

  /**
   * ユーザーIDの文字数が長すぎる。
   */
  int FW_REST_USER_REG_ID_INVALID = -9005;

  /**
   * ユーザーIDがすでに存在している。（重複）
   */
  int FW_REST_USER_REG_ID_EXISTS = -9006;

  /**
   * パラメータが不足している。
   */
  int FW_REST_USER_CHANGE_PASSWD_INVALID = -9007;

  /**
   * パスワード認証に失敗。
   */
  int FW_REST_USER_CHANGE_PASSWD_UNAUTHORIZED = -9008;

  /**
   * リクエストをユーザークラスに変換に失敗。
   */
  int FW_REST_UNMARSHAL_ERROR = -9009;

  /**
   * 存在しない仮登録トークン。
   */
  int FW_REST_USER_ACTUAL_REG_TOKEN_INVALID = -9010;

  /**
   * 有効期限の切れた仮登録トークン。
   */
  int FW_REST_USER_ACTUAL_REG_TOKEN_EXPIRE = -9011;

  /**
   * 本登録済の仮登録トークン。
   */
  int FW_REST_USER_ACTUAL_REG_TOKEN_REGISTERED = -9012;

  /**
   * 仮登録時にメールアドレスが無い。
   */
  int FW_REST_USER_PRE_REG_MAIL_EMPTY = -9013;

  /**
   * トークンが無い。
   */
  int FW_REST_USER_TOKEN_EMPTY = -9014;

  /**
   * ユーザーIDが無い。
   */
  int FW_REST_USER_RESET_PASSWD_ID_EMPTY = -9015;

  /**
   * OICログインに必要なパラメータが不足している。
   */
  int FW_REST_OIC_LOGIN_BAD_REQUEST = -9016;

}
