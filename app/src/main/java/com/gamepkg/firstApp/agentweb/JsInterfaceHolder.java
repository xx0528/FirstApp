package com.gamepkg.firstApp.agentweb;

import java.util.Map;

public interface JsInterfaceHolder {

    JsInterfaceHolder addJavaObjects(Map<String, Object> maps);

    JsInterfaceHolder addJavaObject(String k, Object v);

    boolean checkObject(Object v);

}
