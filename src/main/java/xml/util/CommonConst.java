package xml.util;

/**
 * @author Administrator
 *
 */
public interface CommonConst
{
   /**
    * 返回base64照片时 map中的字段名
    */
   String PHOTO_STRING    = "photo";

   /**
    * 返回base64模板时 mpa中的字段名
    */
   String TEMPLATE_STRING = "template";

   String ID              = "id";

   /**
    * 注销成功的result
    */
   int    LOGOUT_SUCCESS  = 0;

   /**
    * 注销失败的result
    */
   int    LOGOUT_NO_LOGIN = -2;

   /**
    * 注销失败的result
    */
   int    LOGOUT_ERROR    = -1;

   /**
    * 开启成功的result
    */
   int    START_SUCCESS   = 0;

   /**
    * 开启失败的result
    */
   int    START_ERROR     = -1;

   /**
    * 成功的result
    */
   int    SUCCESS         = 0;

   /**
    * 失败的result
    */
   int    ERROR           = -1;

   /**
    * 开启重复的result
    */
   int    START_REPEAT    = -2;

   /**
    * 开启时登录失败的result
    */
   int    START_NO_LOGIN  = -3;

   /**
    * 不存在的信息
    */
   int    NO_EXIST        = -4;

   /**
    * xml字段名
    */
   String TOKEN           = "token";

   /**
    * xml字段名
    */
   String LOGIN_ID        = "login_id";

   /**
    * xml字段名
    */
   String DEVICE_NUMBER   = "deviceNumber";

   /**
    * xml字段名
    */
   String RESULT          = "result";

   /**
    * xml字段名
    */
   String DEVICE_ID       = "deviceId";

   /**
    * xml字段名
    */
   String DEVICE_TYPE     = "deviceType";

   String TRUE            = "true";
   String FALSE           = "false";

}
