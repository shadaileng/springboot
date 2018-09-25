package com.qpf.advanced.components;

import com.qpf.advanced.bean.Menu;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class InitHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        List<Menu> menus = new ArrayList<Menu>();
        menus.add(new Menu("E1001", 1, "E1", "员工列表", "/empls", "list", "VIP1,VIP2,VIP3"));
        menus.add(new Menu("E1002", 2, "E1", "添加员工", "/empl", "plus", "VIP1,VIP2,VIP3"));
        menus.add(new Menu("S1001", 1, "S1", "VIP1", "/level/level1", "plus", "VIP1,VIP2,VIP3"));
        menus.add(new Menu("S1002", 2, "S1", "VIP2", "/level/level2", "plus", "VIP1,VIP2,VIP3"));
        menus.add(new Menu("S1003", 3, "S1", "VIP3", "/level/level3", "plus", "VIP1,VIP2,VIP3"));

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
