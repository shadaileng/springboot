package com.qpf.advanced.components;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class InitHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        List<Map<String, String>> menus = new ArrayList<Map<String, String>>();
        Map<String, String> menu = new HashMap<String, String>();
        menu.put("employee.name", "list");
        menu.put("text", "员工列表");
        menu.put("icon", "list");
        menu.put("url", "/empls");
        menus.add(menu);
        menu = new HashMap<String, String>();
        menu.put("employee.name", "add");
        menu.put("text", "添加员工");
        menu.put("icon", "plus");
        menu.put("url", "/empl");
        menus.add(menu);

        httpServletRequest.getSession().setAttribute("SIDE_MENU", menus);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
