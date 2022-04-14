package com.provider_oidc_ldsc.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.provider_oidc_ldsc.domain.DateUtils;
import com.provider_oidc_ldsc.domain.shared.Application;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

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
public class TodoEntity {
    /**
     * 代办集合接口字段映射
     */
    private static final String TODO_ID = "id";
    private static final String TODO_STATUS = "status";
    private static final String TODO_MSG = "msg";
    private static final String TODO_DATA_URL = "url";
    private static final String TODO_DATA_ROUTE_NAME = "routeName";
    private static final String TODO_DATA_CATEGORY = "todoCategory";
    private static final String TODO_DATA_NUMBER = "todoNumber";
    private static final String TODO_DATA_TOTAL_NUM = "totalNum";
    private static final String TODO_DATA_RESERVE_EXTENSIONS = "reserveExtensions";


    /**
     * 信息id
     */
    private Integer id;
    /**
     * 请求响应状态
     */
    private String status;
    /**
     * 代办事项响应信息
     */
    private String msg;
    /**
     * 代办事项处理链接
     */
    private String url;
    /**
     * 代办事项处理路由名称
     */
    private String routeName;
    /**
     * 代办事项分类索引
     */
    private Integer todoCategory;
    /**
     * 当前登录用户代办事项处理数量
     */
    private Integer todoNumber;
    /**
     * 代办事项处理数量总数
     */
    private Integer totalNum;
    /**
     * 代办事项扩展预留信息
     */
    private JSONObject reserveExtensions;


    public TodoEntity() {
        this.id = 1;
        this.status = "C00001";
        this.msg = "操作成功，应用没有任何待办事项";
        this.url = Application.host("ldsc/web/todo");
        this.routeName = "";
        this.todoCategory = 2;
        this.todoNumber = 0;
        this.totalNum = 0;
        this.reserveExtensions = new JSONObject();
    }

    public TodoEntity(Integer id, String status, String msg, String url, String routeName, Integer todoCategory, Integer todoNumber, Integer totalNum, JSONObject reserveExtensions) {
        this.id = id;
        this.status = status;
        this.msg = msg;
        this.url = String.format("%s?%s=%s", url, TODO_DATA_ROUTE_NAME, routeName);
        this.routeName = routeName;
        this.todoCategory = todoCategory;
        this.todoNumber = todoNumber;
        this.totalNum = totalNum;
        this.reserveExtensions = reserveExtensions;
    }

    public TodoEntity(String status, String msg) {
        this.id = 1;
        this.status = status;
        this.msg = msg;
        this.url = Application.host("ldsc/web/todo");
        this.routeName = "";
        this.todoCategory = 2;
        this.todoNumber = 0;
        this.totalNum = 0;
        this.reserveExtensions = new JSONObject();
    }

    public TodoEntity(String msg) {
        this.id = 1;
        this.status = "C00001";
        this.msg = msg;
        this.url = Application.host("ldsc/web/todo");
        this.routeName = "";
        this.todoCategory = 2;
        this.todoNumber = 0;
        this.totalNum = 0;
        this.reserveExtensions = new JSONObject();
    }

    public TodoEntity(Map<String, Object> map) {
        if(map != null) {
            this.id = (Integer) map.get(TODO_ID);
            this.status = (String) map.get(TODO_STATUS);
            this.msg = (String) map.get(TODO_MSG);
            this.url = (String) map.get(TODO_DATA_URL);
            this.routeName = (String) map.get(TODO_DATA_ROUTE_NAME);
            this.todoCategory = (Integer) map.get(TODO_DATA_CATEGORY);
            this.todoNumber = (Integer) map.get(TODO_DATA_NUMBER);
            this.totalNum = (Integer) map.get(TODO_DATA_TOTAL_NUM);
            this.reserveExtensions = (JSONObject) map.get(TODO_DATA_RESERVE_EXTENSIONS);
        }
    }

    public TodoEntity(JSONObject json) {
        if(json != null) {
            this.id = json.getInteger(TODO_ID);
            this.status = json.getString(TODO_STATUS);
            this.msg = json.getString(TODO_MSG);
            this.url = json.getString(TODO_DATA_URL);
            this.routeName = json.getString(TODO_DATA_ROUTE_NAME);
            this.todoCategory = json.getInteger(TODO_DATA_CATEGORY);
            this.todoNumber = json.getInteger(TODO_DATA_NUMBER);
            this.totalNum = json.getInteger(TODO_DATA_TOTAL_NUM);
            this.reserveExtensions = json.getJSONObject(TODO_DATA_RESERVE_EXTENSIONS);
        }
    }

    public void addDataItem(String url, String routeName, Integer todoCategory, Integer todoNumber) {
        this.url = String.format("%s?%s=%s", url, TODO_DATA_ROUTE_NAME, routeName);
        this.routeName = routeName;
        this.todoCategory = todoCategory;
        this.todoNumber = todoNumber;
        this.totalNum = todoNumber;
    }

    public void addDataItem(String routeName, Integer todoNumber) {
        this.url = String.format("%s?%s=%s", Application.host("ldsc/web/todo"), TODO_DATA_ROUTE_NAME, routeName);
        this.routeName = routeName;
        this.todoNumber = todoNumber;
        this.totalNum = todoNumber;
    }

    public JSONObject todoData() {
        JSONObject json = new JSONObject();
        json.put(TODO_DATA_URL, this.url);
        json.put(TODO_DATA_ROUTE_NAME, this.routeName);
        json.put(TODO_DATA_CATEGORY, this.todoCategory);
        json.put(TODO_DATA_NUMBER, this.todoNumber);
        json.put(TODO_DATA_TOTAL_NUM, this.totalNum);
        json.put(TODO_DATA_RESERVE_EXTENSIONS, this.reserveExtensions);
        return json;
    }
}
