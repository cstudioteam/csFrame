package jp.cstudio.csfw.log;

import org.slf4j.MDC;

public class FWMDC {

    public static final String LOG_NAME = "logName";
    public static final String REQUEST_ID = "requestId";

    public static FWLogName setLogName(FWLogName name) {

        FWLogName currenLogName = getCurrentLogName();
        if (name == null) {
            remove(LOG_NAME);
        } else {
            MDC.put(LOG_NAME, name.toString());
        }
        return currenLogName;
    }

    public static FWLogName getCurrentLogName() {

        String logName = MDC.get(LOG_NAME);
        FWLogName retName = null;
        if (logName != null) {
            retName = FWLogName.valueOf(logName);
        }
        return retName;
    }

    public static void clear() {

        MDC.clear();
    }

    public static void put(String key, String value) {

        MDC.put(key, value);
    }

    public static void remove(String key) {

        MDC.remove(key);
    }

}
