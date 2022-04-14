package com.provider_oidc_ldsc.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.provider_oidc_ldsc.domain.DateUtils;
import com.provider_oidc_ldsc.domain.shared.Application;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jose4j.jwt.JwtClaims;

import java.sql.Struct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
public class PressesEntity {
    /**
     * 消息集合接口字段映射
     */
    private static final String PRESSES_ID = "id";
    private static final String PRESSES_STATUS = "status";
    private static final String PRESSES_MSG = "msg";
    private static final String PRESSES_DATA_COLUMN = "column";
    private static final String PRESSES_DATA_MORE = "more";
    private static final String PRESSES_DATA_ITEMS = "items";
    private static final String PRESSES_DATA_ITEM_CODE = "code";
    private static final String PRESSES_DATA_ITEM_HEAD_LINE = "headline";
    private static final String PRESSES_DATA_ITEM_DETAIL_URL = "detailUrl";
    private static final String PRESSES_DATA_ITEM_ROUTE_NAME = "routeName";
    private static final String PRESSES_DATA_ITEM_STICKY = "sticky";
    private static final String PRESSES_DATA_ITEM_UNREAD = "unread";
    private static final String PRESSES_DATA_ITEM_CREATE_DATETIME = "createdDT";
    private static final String PRESSES_DATA_ITEM_UPDATE_DATETIME = "updatedDT";


    /**
     * 信息id
     */
    private Integer id;
    /**
     * 请求响应状态
     */
    private String status;
    /**
     * 信息响应信息
     */
    private String msg;
    /**
     * 信息栏目名称
     */
    private String column;
    /**
     * 更多信息链接
     */
    private String more;
    /**
     * 信息响应数据
     */
    private JSONArray items;
    /**
     * 信息响应数据子项
     */
    private JSONObject item;


    public PressesEntity() {
        this.id = 1;
        this.status = "C00001";
        this.msg = "操作成功，应用没有任何聚合信息";
        this.column = "数字铁建";
        this.more = "http://cr12g.crcc.cn";
        this.items = new JSONArray();
    }

    public PressesEntity(Integer id, String status, String msg, String column, String more, JSONArray items) {
        this.id = id;
        this.status = status;
        this.msg = msg;
        this.column = column;
        this.more = more;
        this.items = items;
    }

    public PressesEntity(String status, String msg) {
        this.id = 1;
        this.status = status;
        this.msg = msg;
        this.items = new JSONArray();
        this.column = "数字铁建";
        this.more = "http://cr12g.crcc.cn";
        this.items = new JSONArray();
    }

    public PressesEntity(String msg) {
        this.id = 1;
        this.status = "C00001";
        this.msg = msg;
        this.items = new JSONArray();
        this.column = "数字铁建";
        this.more = "http://cr12g.crcc.cn";
        this.items = new JSONArray();
    }

    public PressesEntity(Map<String, Object> map) {
        if(map != null) {
            this.id = (Integer) map.get(PRESSES_ID);
            this.status = (String) map.get(PRESSES_STATUS);
            this.msg = (String) map.get(PRESSES_MSG);
            this.column = (String) map.get(PRESSES_DATA_COLUMN);
            this.more = (String) map.get(PRESSES_DATA_MORE);
            this.items = (JSONArray) map.get(PRESSES_DATA_ITEMS);
        }
    }

    public PressesEntity(JSONObject json) {
        if(json != null) {
            this.id = json.getInteger(PRESSES_ID);
            this.status = json.getString(PRESSES_STATUS);
            this.msg = json.getString(PRESSES_MSG);
            this.column = json.getString(PRESSES_DATA_COLUMN);
            this.more = json.getString(PRESSES_DATA_MORE);
            this.items = json.getJSONArray(PRESSES_DATA_ITEMS);
        }
    }

    public JSONObject pressesData() {
        JSONObject json = new JSONObject();
        json.put(PRESSES_DATA_COLUMN, this.column);
        json.put(PRESSES_DATA_MORE, this.more);
        json.put(PRESSES_DATA_ITEMS, this.items);
        return json;
    }

    public Integer getItemsSize() {
        return items.size();
    }

    public void addDataItem(JSONObject item) {
        if(this.items != null) {
            this.items.add(item);
        }
    }

    public void addDataItem(String code, String headline, String detailUrl, String routeName, Boolean sticky, Boolean unread, LocalDateTime createDateTime, LocalDateTime updateDateTime) {
        JSONObject item = new JSONObject();
        item.put(PRESSES_DATA_ITEM_CODE, code);
        item.put(PRESSES_DATA_ITEM_HEAD_LINE, headline);
        item.put(PRESSES_DATA_ITEM_DETAIL_URL, String.format("%s?%s=%s", detailUrl, PRESSES_DATA_ITEM_CODE, code));
        item.put(PRESSES_DATA_ITEM_ROUTE_NAME, routeName);
        item.put(PRESSES_DATA_ITEM_STICKY, sticky);
        item.put(PRESSES_DATA_ITEM_UNREAD, unread);
        item.put(PRESSES_DATA_ITEM_CREATE_DATETIME, createDateTime);
        item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, updateDateTime);
        addDataItem(item);
    }

    public void addDataItem(String code, String headline, String detailUrl, String routeName, Boolean sticky, Boolean unread) {
        JSONObject item = new JSONObject();
        item.put(PRESSES_DATA_ITEM_CODE, code);
        item.put(PRESSES_DATA_ITEM_HEAD_LINE, headline);
        item.put(PRESSES_DATA_ITEM_DETAIL_URL, String.format("%s?%s=%s", detailUrl, PRESSES_DATA_ITEM_CODE, code));
        item.put(PRESSES_DATA_ITEM_ROUTE_NAME, routeName);
        item.put(PRESSES_DATA_ITEM_STICKY, sticky);
        item.put(PRESSES_DATA_ITEM_UNREAD, unread);
        item.put(PRESSES_DATA_ITEM_CREATE_DATETIME, DateUtils.currentLocalDateTime());
        item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        addDataItem(item);
    }

    public void addDataItem(String code, String headline, String routeName, Boolean sticky, Boolean unread) {
        JSONObject item = new JSONObject();
        item.put(PRESSES_DATA_ITEM_CODE, code);
        item.put(PRESSES_DATA_ITEM_HEAD_LINE, headline);
        item.put(PRESSES_DATA_ITEM_DETAIL_URL, String.format("%s?%s=%s", Application.host("ldsc/web/presses"), PRESSES_DATA_ITEM_CODE, code));
        item.put(PRESSES_DATA_ITEM_ROUTE_NAME, routeName);
        item.put(PRESSES_DATA_ITEM_STICKY, sticky);
        item.put(PRESSES_DATA_ITEM_UNREAD, unread);
        item.put(PRESSES_DATA_ITEM_CREATE_DATETIME, DateUtils.currentLocalDateTime());
        item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        addDataItem(item);
    }

    public void addDataItem(String code, String headline, String routeName) {
        JSONObject item = new JSONObject();
        item.put(PRESSES_DATA_ITEM_CODE, code);
        item.put(PRESSES_DATA_ITEM_HEAD_LINE, headline);
        item.put(PRESSES_DATA_ITEM_DETAIL_URL, String.format("%s?%s=%s", Application.host("ldsc/web/presses"), PRESSES_DATA_ITEM_CODE, code));
        item.put(PRESSES_DATA_ITEM_ROUTE_NAME, routeName);
        item.put(PRESSES_DATA_ITEM_STICKY, true);
        item.put(PRESSES_DATA_ITEM_UNREAD, false);
        item.put(PRESSES_DATA_ITEM_CREATE_DATETIME, DateUtils.currentLocalDateTime());
        item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        addDataItem(item);
    }

    public Integer findDataItem(JSONObject item) {
        if(this.items != null && item != null) {
            int index = this.items.indexOf(item);
            if( index != -1) {
                this.item = item;
                return index;
            }
        }

        return -1;
    }

    public Integer findDataItem(Integer index) {
        if(this.items != null && index >= 0) {
            JSONObject item = this.items.getJSONObject(index);
            if( item != null) {
                this.item = item;
                return index;
            }
        }

        return -1;
    }

    public Integer findDataItem(String code) {
        if(this.items != null) {
            for (int i = 0; i < this.items.size(); i++) {
                this.item = this.items.getJSONObject(i);
                if (this.item != null && this.item.getString(PRESSES_DATA_ITEM_CODE).equals(code)) {
                    return i;
                }
            }
        }

        return -1;
    }

    public Integer findDataItem(String headline, String detailUrl, String routeName) {
        if(this.items != null) {
            for (int i = 0; i < this.items.size(); i++) {
                this.item = this.items.getJSONObject(i);
                if (this.item != null && this.item.getString(PRESSES_DATA_ITEM_HEAD_LINE).equals(headline) &&
                        this.item.getString(PRESSES_DATA_ITEM_DETAIL_URL).equals(detailUrl) &&
                        this.item.getString(PRESSES_DATA_ITEM_ROUTE_NAME).equals(routeName)) {
                    return i;
                }
            }
        }

        return -1;
    }

    public Integer findDataItem(String headline, String routeName) {
        if(this.items != null) {
            for (int i = 0; i < this.items.size(); i++) {
                this.item = this.items.getJSONObject(i);
                if (this.item != null && this.item.getString(PRESSES_DATA_ITEM_HEAD_LINE).equals(headline) &&
                        this.item.getString(PRESSES_DATA_ITEM_ROUTE_NAME).equals(routeName)) {
                    return i;
                }
            }
        }

        return -1;
    }

    public JSONArray findDataUnreadItems(Boolean unread) {
        if(this.items != null) {
            JSONArray jsons = new JSONArray();
            for (int i = 0; i < this.items.size(); i++) {
                JSONObject item = this.items.getJSONObject(i);
                if (item != null && item.getBoolean(PRESSES_DATA_ITEM_UNREAD).equals(unread)) {
                    jsons.add(item);
                }
            }

            if(!jsons.isEmpty()) {
                this.item = jsons.getJSONObject(0);
            }

            return jsons;
        }

        return null;
    }

    public JSONArray findDataStickyItems(Boolean sticky) {
        if(this.items != null) {
            JSONArray jsons = new JSONArray();
            for (int i = 0; i < this.items.size(); i++) {
                JSONObject item = this.items.getJSONObject(i);
                if (item != null && item.getBoolean(PRESSES_DATA_ITEM_STICKY).equals(sticky)) {
                    jsons.add(item);
                }
            }

            if(!jsons.isEmpty()) {
                this.item = jsons.getJSONObject(0);
            }

            return jsons;
        }

        return null;
    }

    public JSONArray findDataHeadlineItems(String headline) {
        if(this.items != null) {
            JSONArray jsons = new JSONArray();
            for (int i = 0; i < this.items.size(); i++) {
                JSONObject item = this.items.getJSONObject(i);
                if (item != null && item.getBoolean(PRESSES_DATA_ITEM_HEAD_LINE).equals(headline)) {
                    jsons.add(item);
                }
            }

            if(!jsons.isEmpty()) {
                this.item = jsons.getJSONObject(0);
            }

            return jsons;
        }

        return null;
    }

    public JSONArray findDataRouteNameItems(String routeName) {
        if(this.items != null) {
            JSONArray jsons = new JSONArray();
            for (int i = 0; i < this.items.size(); i++) {
                JSONObject item = this.items.getJSONObject(i);
                if (item != null && item.getBoolean(PRESSES_DATA_ITEM_ROUTE_NAME).equals(routeName)) {
                    jsons.add(item);
                }
            }

            if(!jsons.isEmpty()) {
                this.item = jsons.getJSONObject(0);
            }

            return jsons;
        }

        return null;
    }

    public void delDataItem(JSONObject item) {
        if(this.items != null) {
            this.items.remove(item);
        }
    }

    public void delDataItem(Integer index) {
        if(this.items != null && index >= 0) {
            this.items.remove(index);
        }
    }

    public void delDataItem(String code) {
        if(this.items != null) {
            for (int i = 0; i < this.items.size(); i++) {
                JSONObject item = this.items.getJSONObject(i);
                if (item != null && item.getString(PRESSES_DATA_ITEM_CODE).equals(code)) {
                    this.items.remove(item);
                }
            }
        }
    }

    public void delDataUnreadItems(Boolean unread) {
        if(this.items != null) {
            for (int i = 0; i < this.items.size(); i++) {
                JSONObject item = this.items.getJSONObject(i);
                if (item != null && item.getBoolean(PRESSES_DATA_ITEM_UNREAD).equals(unread)) {
                    this.items.remove(item);
                }
            }
        }
    }

    public void delDataStickyItems(Boolean sticky) {
        if(this.items != null) {
            for (int i = 0; i < this.items.size(); i++) {
                JSONObject item = this.items.getJSONObject(i);
                if (item != null && item.getBoolean(PRESSES_DATA_ITEM_STICKY).equals(sticky)) {
                    this.items.remove(item);
                }
            }
        }
    }

    public void delDataHeadlineItems(String headline) {
        if(this.items != null) {
            for (int i = 0; i < this.items.size(); i++) {
                JSONObject item = this.items.getJSONObject(i);
                if (item != null && item.getString(PRESSES_DATA_ITEM_HEAD_LINE).equals(headline)) {
                    this.items.remove(item);
                }
            }
        }
    }

    public void delDataRouteNameItems(String routeName) {
        if(this.items != null) {
            for (int i = 0; i < this.items.size(); i++) {
                JSONObject item = this.items.getJSONObject(i);
                if (item != null && item.getString(PRESSES_DATA_ITEM_ROUTE_NAME).equals(routeName)) {
                    this.items.remove(item);
                }
            }
        }
    }

    public void clearItems() {
        if(this.items != null) {
            this.items.clear();
        }
    }

    public JSONObject getDataItem(Integer index) {
        return this.items != null && index >= 0 ? this.items.getJSONObject(index) : null;
    }

    public JSONObject getDataItem(String code) {
        if(this.items != null) {
            for (int i = 0; i < this.items.size(); i++) {
                JSONObject item = this.items.getJSONObject(i);
                if (item != null && item.getString(PRESSES_DATA_ITEM_CODE).equals(code)) {
                    return item;
                }
            }
        }

        return null;
    }

    public String getPressesDataItemHeadLine(Integer index) {
        JSONObject item = getDataItem(index);
        return item != null ? item.getString(PRESSES_DATA_ITEM_HEAD_LINE) : null;
    }

    public String getPressesDataItemDetailUrl(Integer index) {
        JSONObject item = getDataItem(index);
        return item != null ? item.getString(PRESSES_DATA_ITEM_DETAIL_URL) : null;
    }

    public String getPressesDataItemRouteName(Integer index) {
        JSONObject item = getDataItem(index);
        return item != null ? item.getString(PRESSES_DATA_ITEM_ROUTE_NAME) : null;
    }

    public Boolean getPressesDataItemSticky(Integer index) {
        JSONObject item = getDataItem(index);
        return item != null ? item.getBoolean(PRESSES_DATA_ITEM_STICKY) : null;
    }

    public Boolean getPressesDataItemUnread(Integer index) {
        JSONObject item = getDataItem(index);
        return item != null ? item.getBoolean(PRESSES_DATA_ITEM_UNREAD) : null;
    }

    public LocalDateTime getPressesDataItemCreateDatetime(Integer index) {
        JSONObject item = getDataItem(index);
        return item != null ? DateUtils.convertDateToLocalDateTime(item.getDate(PRESSES_DATA_ITEM_CREATE_DATETIME)) : null;
    }

    public LocalDateTime getPressesDataItemUpdateDatetime(Integer index) {
        JSONObject item = getDataItem(index);
        return item != null ? DateUtils.convertDateToLocalDateTime(item.getDate(PRESSES_DATA_ITEM_UPDATE_DATETIME)) : null;
    }

    public String getPressesDataItemHeadLine(String code) {
        JSONObject item = getDataItem(code);
        return item != null ? item.getString(PRESSES_DATA_ITEM_HEAD_LINE) : null;
    }

    public String getPressesDataItemDetailUrl(String code) {
        JSONObject item = getDataItem(code);
        return item != null ? item.getString(PRESSES_DATA_ITEM_DETAIL_URL) : null;
    }

    public String getPressesDataItemRouteName(String code) {
        JSONObject item = getDataItem(code);
        return item != null ? item.getString(PRESSES_DATA_ITEM_ROUTE_NAME) : null;
    }

    public Boolean getPressesDataItemSticky(String code) {
        JSONObject item = getDataItem(code);
        return item != null ? item.getBoolean(PRESSES_DATA_ITEM_STICKY) : null;
    }

    public Boolean getPressesDataItemUnread(String code) {
        JSONObject item = getDataItem(code);
        return item != null ? item.getBoolean(PRESSES_DATA_ITEM_UNREAD) : null;
    }

    public LocalDateTime getPressesDataItemCreateDatetime(String code) {
        JSONObject item = getDataItem(code);
        return item != null ? DateUtils.convertDateToLocalDateTime(item.getDate(PRESSES_DATA_ITEM_CREATE_DATETIME)) : null;
    }

    public LocalDateTime getPressesDataItemUpdateDatetime(String code) {
        JSONObject item = getDataItem(code);
        return item != null ? DateUtils.convertDateToLocalDateTime(item.getDate(PRESSES_DATA_ITEM_UPDATE_DATETIME)) : null;
    }

    public String getPressesDataItemHeadLine() {
        return this.item != null ? this.item.getString(PRESSES_DATA_ITEM_HEAD_LINE) : null;
    }

    public String getPressesDataItemDetailUrl() {
        return this.item != null ? this.item.getString(PRESSES_DATA_ITEM_DETAIL_URL) : null;
    }

    public String getPressesDataItemRouteName() {
        return this.item != null ? this.item.getString(PRESSES_DATA_ITEM_ROUTE_NAME) : null;
    }

    public Boolean getPressesDataItemSticky() {
        return this.item != null ? this.item.getBoolean(PRESSES_DATA_ITEM_STICKY) : null;
    }

    public Boolean getPressesDataItemUnread() {
        return this.item != null ? this.item.getBoolean(PRESSES_DATA_ITEM_UNREAD) : null;
    }

    public LocalDateTime getPressesDataItemCreateDatetime() {
        return this.item != null ? DateUtils.convertDateToLocalDateTime(this.item.getDate(PRESSES_DATA_ITEM_CREATE_DATETIME)) : null;
    }

    public LocalDateTime getPressesDataItemUpdateDatetime() {
        return this.item != null ? DateUtils.convertDateToLocalDateTime(this.item.getDate(PRESSES_DATA_ITEM_UPDATE_DATETIME)) : null;
    }

    public void setDataItem(Integer index, JSONObject item) {
        if(this.items != null && item != null && index >= 0) {
            this.items.set(index, item);
        }
    }

    public void setDataItem(Integer index, String headLine, String detailUrl, String routeName) {
        JSONObject item = getDataItem(index);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_HEAD_LINE, headLine);
            item.put(PRESSES_DATA_ITEM_DETAIL_URL, detailUrl);
            item.put(PRESSES_DATA_ITEM_ROUTE_NAME, routeName);
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setDataItem(Integer index, String detailUrl, String routeName) {
        JSONObject item = getDataItem(index);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_DETAIL_URL, detailUrl);
            item.put(PRESSES_DATA_ITEM_ROUTE_NAME, routeName);
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setDataItem(Integer index, Boolean sticky, Boolean unread) {
        JSONObject item = getDataItem(index);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_STICKY, sticky);
            item.put(PRESSES_DATA_ITEM_UNREAD, unread);
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemHeadLine(Integer index, String headLine) {
        JSONObject item = getDataItem(index);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_HEAD_LINE, headLine);
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemDetailUrl(Integer index, String detailUrl) {
        JSONObject item = getDataItem(index);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_DETAIL_URL, detailUrl);
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemRouteName(Integer index, String routeName) {
        JSONObject item = getDataItem(index);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_ROUTE_NAME, routeName);
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemSticky(Integer index, Boolean sticky) {
        JSONObject item = getDataItem(index);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_STICKY, sticky);
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemUnread(Integer index, Boolean unread) {
        JSONObject item = getDataItem(index);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_UNREAD, unread);
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemCreateDatetime(Integer index, LocalDateTime createDateTime) {
        JSONObject item = getDataItem(index);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_CREATE_DATETIME, createDateTime);
        }
    }

    public void setPressesDataItemUpdateDatetime(Integer index, LocalDateTime updateDateTime) {
        JSONObject item = getDataItem(index);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, updateDateTime);
        }
    }

    public void setDataItem(String code, JSONObject item) {
        if(this.items != null && item != null && code != null && code.length() > 0) {
            Integer index = findDataItem(code);
            if(index != -1) {
                this.items.set(index, item);
            }
        }
    }

    public void setDataItem(String code, String headLine, String detailUrl, String routeName) {
        JSONObject item = getDataItem(code);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_HEAD_LINE, headLine);
            item.put(PRESSES_DATA_ITEM_DETAIL_URL, detailUrl);
            item.put(PRESSES_DATA_ITEM_ROUTE_NAME, routeName);
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setDataItem(String code, String detailUrl, String routeName) {
        JSONObject item = getDataItem(code);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_DETAIL_URL, detailUrl);
            item.put(PRESSES_DATA_ITEM_ROUTE_NAME, routeName);
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setDataItem(String code, Boolean sticky, Boolean unread) {
        JSONObject item = getDataItem(code);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_STICKY, sticky);
            item.put(PRESSES_DATA_ITEM_UNREAD, unread);
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemHeadLine(String code, String headLine) {
        JSONObject item = getDataItem(code);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_HEAD_LINE, headLine);
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemDetailUrl(String code, String detailUrl) {
        JSONObject item = getDataItem(code);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_DETAIL_URL, detailUrl);
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemRouteName(String code, String routeName) {
        JSONObject item = getDataItem(code);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_ROUTE_NAME, routeName);
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemSticky(String code, Boolean sticky) {
        JSONObject item = getDataItem(code);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_STICKY, sticky);
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemUnread(String code, Boolean unread) {
        JSONObject item = getDataItem(code);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_UNREAD, unread);
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemCreateDatetime(String code, LocalDateTime createDateTime) {
        JSONObject item = getDataItem(code);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_CREATE_DATETIME, createDateTime);
        }
    }

    public void setPressesDataItemUpdateDatetime(String code, LocalDateTime updateDateTime) {
        JSONObject item = getDataItem(code);
        if (item != null) {
            item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, updateDateTime);
        }
    }

    public void setDataItem_(String headLine, String detailUrl, String routeName) {
        if(this.item != null) {
            this.item.put(PRESSES_DATA_ITEM_HEAD_LINE, headLine);
            this.item.put(PRESSES_DATA_ITEM_DETAIL_URL, detailUrl);
            this.item.put(PRESSES_DATA_ITEM_ROUTE_NAME, routeName);
            this.item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setDataItem(String detailUrl, String routeName) {
        if(this.item != null) {
            this.item.put(PRESSES_DATA_ITEM_DETAIL_URL, detailUrl);
            this.item.put(PRESSES_DATA_ITEM_ROUTE_NAME, routeName);
            this.item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setDataItem(Boolean sticky, Boolean unread) {
        if(this.item != null) {
            this.item.put(PRESSES_DATA_ITEM_STICKY, sticky);
            this.item.put(PRESSES_DATA_ITEM_UNREAD, unread);
            this.item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemHeadLine(String headLine) {
        if(this.item != null) {
            this.item.put(PRESSES_DATA_ITEM_HEAD_LINE, headLine);
            this.item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemDetailUrl(String detailUrl) {
        if(this.item != null) {
            this.item.put(PRESSES_DATA_ITEM_DETAIL_URL, detailUrl);
            this.item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemRouteName(String routeName) {
        if(this.item != null) {
            this.item.put(PRESSES_DATA_ITEM_ROUTE_NAME, routeName);
            this.item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemSticky(Boolean sticky) {
        if(this.item != null) {
            this.item.put(PRESSES_DATA_ITEM_STICKY, sticky);
            this.item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemUnread(Boolean unread) {
        if(this.item != null) {
            this.item.put(PRESSES_DATA_ITEM_UNREAD, unread);
            this.item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, DateUtils.currentLocalDateTime());
        }
    }

    public void setPressesDataItemCreateDatetime(LocalDateTime createDateTime) {
        if(this.item != null) {
            this.item.put(PRESSES_DATA_ITEM_CREATE_DATETIME, createDateTime);
        }
    }

    public void setPressesDataItemUpdateDatetime(LocalDateTime updateDateTime) {
        if(this.item != null) {
            this.item.put(PRESSES_DATA_ITEM_UPDATE_DATETIME, updateDateTime);
        }
    }
}
