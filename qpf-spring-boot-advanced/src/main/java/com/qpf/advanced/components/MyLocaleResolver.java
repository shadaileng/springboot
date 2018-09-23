package com.qpf.advanced.components;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {

        String localeParam = httpServletRequest.getParameter("l");

        Locale locale = Locale.getDefault();

        if (!StringUtils.isEmpty(localeParam)) {
            String[] split = localeParam.split("_");
            split = split.length == 2 ? split : new String[]{"zh", "CN"};
            locale = new Locale(split[0], split[1]);
            httpServletRequest.getSession().setAttribute("i18nLanguageSession", locale);
        } else {
            Locale i18nLanguageSession = (Locale) httpServletRequest.getSession().getAttribute("i18nLanguageSession");
            locale = i18nLanguageSession != null ? i18nLanguageSession : locale;
        }

        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
