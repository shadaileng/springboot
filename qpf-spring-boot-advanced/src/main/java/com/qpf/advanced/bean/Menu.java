package com.qpf.advanced.bean;

public class Menu {
    private String menuCid;
    private Integer menuSeq;
    private String menuPid;
    private String menuText;
    private String menuUrl;
    private String menuIcon;
    private String roleVisible;

    public Menu() {}

    public Menu(String menuCid, Integer menuSeq, String menuPid, String menuText, String menuUrl, String menuIcon, String roleVisible) {
        this.menuCid = menuCid;
        this.menuSeq = menuSeq;
        this.menuPid = menuPid;
        this.menuText = menuText;
        this.menuUrl = menuUrl;
        this.menuIcon = menuIcon;
        this.roleVisible = roleVisible;
    }

    public String getMenuCid() {
        return menuCid;
    }

    public void setMenuCid(String menuCid) {
        this.menuCid = menuCid;
    }

    public Integer getMenuSeq() {
        return menuSeq;
    }

    public void setMenuSeq(Integer menuSeq) {
        this.menuSeq = menuSeq;
    }

    public String getMenuPid() {
        return menuPid;
    }

    public void setMenuPid(String menuPid) {
        this.menuPid = menuPid;
    }

    public String getMenuText() {
        return menuText;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getRoleVisible() {
        return roleVisible;
    }

    public void setRoleVisible(String roleVisible) {
        this.roleVisible = roleVisible;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuCid='" + menuCid + '\'' +
                ", menuSeq=" + menuSeq +
                ", menuPid='" + menuPid + '\'' +
                ", menuText='" + menuText + '\'' +
                ", menuUrl='" + menuUrl + '\'' +
                ", menuIcon='" + menuIcon + '\'' +
                ", roleVisible='" + roleVisible + '\'' +
                '}';
    }
}
