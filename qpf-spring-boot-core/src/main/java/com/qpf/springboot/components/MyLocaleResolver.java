package com.qpf.springboot.components;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String l = request.getParameter("l");
        Locale locale = Locale.getDefault();
        if (!StringUtils.isEmpty(l)) {
            String[] split = l.split("_");
            split = split.length == 2 ? split : new String[]{"zh", "CN"};
            locale = new Locale(split[0], split[1]);

            // 将i18n信息保存到session中
            request.getSession().setAttribute("I18N_LANGUAGE_SESSION", locale);
        } else {
            // 从session中取出区域信息
            Locale i18NLanguageSession = (Locale) request.getSession().getAttribute("I18N_LANGUAGE_SESSION");
            locale = i18NLanguageSession != null? i18NLanguageSession : locale;

        }

        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
