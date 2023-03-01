package com.gamepkg.firstApp.agentweb;

public interface PermissionInterceptor {
    boolean intercept(String url, String[] permissions, String action);
}
