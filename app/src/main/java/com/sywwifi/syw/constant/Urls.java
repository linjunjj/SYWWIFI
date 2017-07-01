package com.sywwifi.syw.constant;

/**
 * Created by shaowen on 2017/6/30.
 */

public class Urls {

  //---------------------------魏来-----------------------------------------------

    //  public static final String URL_SERVER = "http://139.196.104.69/syw-api";//生产环境

      public static final String URL_SERVER = "http://weilai.ngrok.io/syw-api";//开发环境

   //  public static final String URL_SERVER = "http://weilai.ngrok.io/syw-api";//测试环境


      //获取验证码
     public static final String URL_GET_CODE = URL_SERVER + "/client/getCode";

      //客户注册
      public static final String URL_REG = URL_SERVER + "/client/login";




}
