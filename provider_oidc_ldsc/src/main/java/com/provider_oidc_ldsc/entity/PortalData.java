package com.provider_oidc_ldsc.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.Objects;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-04-28 18:04
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class PortalData  implements Cloneable{
    /**
     * 代办消息集合接口字段映射
     */
    private static final String PORTAL_ID = "id";
    private static final String PORTAL_STATUS = "status";
    private static final String PORTAL_MSG = "msg";
    private static final String PORTAL_DATA = "data";
    private static final String PORTAL_TYPE = "sso";
    private static final String PORTAL_PRESSES = "code";


    /**
     * 信息id
     */
    private Integer id;
    /**
     * 代办消息响应状态
     */
    private String status;
    /**
     * 代办消息响应信息
     */
    private String msg;
    /**
     * 代办消息响应数据
     */
    private JSONObject data;
    /**
     * 消息响应编码
     */
    private static String code = PORTAL_PRESSES;
    /**
     * 代办消息响应类型
     */
    private static String type = PORTAL_TYPE;
    /**
     * 代办响应数据
     */
    private static TodoEntity todo;
    /**
     * 消息响应数据
     */
    private static PressesEntity presses;


    public PortalData() {
        this.id = 1;
        this.status = "C00001";
        this.msg = "操作成功，应用没有任何待办或消息";
        this.data = new JSONObject();
    }

    public PortalData(Integer id, String status, String msg, JSONObject data) {
        this.id = id;
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public PortalData(String status, String msg) {
        this.id = 1;
        this.status = status;
        this.msg = msg;
        this.data = new JSONObject();
    }

    public PortalData(String msg) {
        this.id = 1;
        this.status = "C00001";
        this.msg = msg;
        this.data = new JSONObject();
    }

    public PortalData(TodoEntity todo) {
        if(todo != null) {
            this.id = todo.getId();
            this.status = todo.getStatus();
            this.msg = todo.getMsg();
            this.data = todo.todoData();
            PortalData.todo = todo;
        }
    }

    public PortalData(PressesEntity presses) {
        if(presses != null) {
            this.id = presses.getId();
            this.status = presses.getStatus();
            this.msg = presses.getMsg();
            this.data = presses.pressesData();
            PortalData.presses = presses;
        }
    }

    public PortalData(Map<String, Object> map) {
        if(map != null) {
            this.id = (Integer) map.get(PORTAL_ID);
            this.status = (String) map.get(PORTAL_STATUS);
            this.msg = (String) map.get(PORTAL_MSG);
            this.data = (JSONObject) map.get(PORTAL_DATA);
        }
    }

    public JSONObject todoData() {
        JSONObject json = new JSONObject();
        json.put(PORTAL_ID, this.id);
        json.put(PORTAL_STATUS, this.status);
        json.put(PORTAL_MSG, this.msg);
        json.put(PORTAL_DATA, this.data);
        return json;
    }

    public void setTodo(TodoEntity todo) {
        if(todo != null) {
            this.id = todo.getId();
            this.status = todo.getStatus();
            this.msg = todo.getMsg();
            this.data = todo.todoData();
            PortalData.todo = todo;
        }
    }

    public void setPresses(PressesEntity presses) {
        if(presses != null) {
            this.id = presses.getId();
            this.status = presses.getStatus();
            this.msg = presses.getMsg();
            this.data = presses.pressesData();
            PortalData.presses = presses;
        }
    }

    public static void nullTodo() {
        PortalData.todo = null;
    }

    public static void nullPresses() {
        PortalData.presses = null;
    }

    public static void setType(String type) {
        PortalData.type = type != null ? type : "sso";
    }

    public static String getType() {
        return PortalData.type;
    }

    public static void setCode(String code) {
        PortalData.code = code;
    }

    public static String getCode() {
        return PortalData.code;
    }

    public static Boolean isTodo() {
        return "todo".equals(PortalData.type);
    }

    public static Boolean isPresses() {
        return "presses".equals(PortalData.type);
    }

    public static Boolean isSso() {
        return "sso".equals(PortalData.type);
    }

    public static Boolean isCode() {
        return PortalData.type != null && PortalData.type.length() > 0;
    }

    public static TodoEntity getToto() {
        return PortalData.todo;
    }

    public static PressesEntity getPresses() {
        return PortalData.presses;
    }
}
