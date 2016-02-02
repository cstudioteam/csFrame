package com.csframe.test.app.log;

import java.util.Set;

import javax.websocket.Session;

public class ErrorLogReader extends LogReader {

  public ErrorLogReader(int myInterval) {
    super("/opt/tomcat/logs/cfw_error.log", myInterval);
  }

  @Override
  public Set<Session> getSession() {

    return ErrorLogView.sessions;
  }

}
