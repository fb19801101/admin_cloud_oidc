package com.provider_oidc_gongchengwbs.entity;

import lombok.*;

/**
 * @author 薛朝阳
 * @date 2021-03-24 20:57
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class MenuAuth {
    /**
     * 名字
     */
    private String  type;
    /**
     * 管理
     */
    private Boolean manage;
    /**
     * 浏览
     */
    private Boolean scan;
    /**
     * 上级管理
     */
    private Boolean _manage;
    /**
     * 上级浏览
     */
    private Boolean _scan;
    /**
     * 权限  0 1 2
     */
    private Integer auth;

    public MenuAuth() {

    }

    public MenuAuth(String type, Integer auth){
        this.type = type;
        this.auth = auth;
        switch (auth) {
            case 0: {
                this.manage = false;
                this.scan = false;
                this._manage = true;
                this._scan = true;
            }
            break;
            case 1: {
                this.manage = false;
                this.scan = true;
                this._manage = true;
                this._scan = false;
            }
            break;
            case 2: {
                this.manage = true;
                this.scan = true;
                this._manage = false;
                this._scan = false;
            }
            break;
            default:
                break;
        }
    }

    public MenuAuth(Integer auth){
        this.auth = auth;
        switch (auth) {
            case 0: {
                this.manage = false;
                this.scan = false;
                this._manage = true;
                this._scan = true;
            }
            break;
            case 1: {
                this.manage = false;
                this.scan = true;
                this._manage = true;
                this._scan = false;
            }
            break;
            case 2: {
                this.manage = true;
                this.scan = true;
                this._manage = false;
                this._scan = false;
            }
            break;
            default:
                break;
        }
    }

    public MenuAuth(String type, Integer auth, Boolean _manage, Boolean _scan){
        this.type = type;
        this.auth = auth;
        this._manage = _manage;
        this._scan = _scan;
        switch (auth) {
            case 0: {
                this.manage = false;
                this.scan = false;
            }
            break;
            case 1: {
                this.manage = false;
                this.scan = true;
            }
            break;
            case 2: {
                this.manage = true;
                this.scan = true;
            }
            break;
            default:
                break;
        }
    }

    public MenuAuth(String type, Boolean manage, Boolean scan, Boolean _manage, Boolean _scan) {
        this.type = type;
        this.manage = manage;
        this.scan = scan;
        this._manage = _manage;
        this._scan = _scan;

        if(!manage && !scan) {
            this.auth = 0;
        }
        if(!manage && scan) {
            this.auth = 1;
        }
        if(manage && scan) {
            this.auth = 2;
        }
    }

    public String log() {
        return type+"[功能]："+ (manage?"有[管理权限]，":"") +(scan?"有[浏览权限]；":"无[浏览权限]；");
    }
}
