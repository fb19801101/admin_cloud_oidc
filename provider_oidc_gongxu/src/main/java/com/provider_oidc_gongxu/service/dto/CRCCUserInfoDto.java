package com.provider_oidc_gongxu.service.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

/**
 * <pre>
 *     {"openid":"Ajlt9ZwVyGUvxrJCdKlFA4AataAVKVgH6gxYeCxD6J","username":"mobile","phone":null,"email":"mobile@qc8.me","create_time":0,"privileges":["USER","ADMIN","UNITY","MOBILE"]}
 * </pre>
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CRCCUserInfoDto extends AbstractOauthDto implements Cloneable {

    private Integer id;

    private String provider;

    private String name;

    private Integer gender;

    private String genderName;

    private Integer catagory;

    private Integer positionStatus;

    private Integer order;

    private JSONObject position;

    private JSONObject department;

    private JSONObject company;

    private String positionName;

    private String departmentName;

    private String companyName;

    private String path;

    private Integer index;

    public void set(Integer index, String provider, JSONObject company, JSONObject department, JSONObject position) {
        this.index = index;
        this.provider = provider;
        this.company = company;
        this.department = department;
        this.position = position;
        if(this.gender != null) {
            this.genderName = this.gender == 2 ? "女" : "男";
        }
        this.companyName = company.getString("name");
        this.departmentName = department.getString("name");
        this.positionName = position.getString("name");
    }

    public void set(CRCCUserInfoDto user) {
        if(user != null) {
            this.id = user.id;
            this.provider = user.provider;
            this.name = user.name;
            this.gender = user.gender;
            this.genderName = user.genderName;
            this.catagory = user.catagory;
            this.positionStatus = user.positionStatus;
            this.order = user.order;
            this.position = CloneUtils.cloneTo(user.position);
            this.department = CloneUtils.cloneTo(user.department);
            this.company = CloneUtils.cloneTo(user.company);
            this.positionName = user.positionName;
            this.departmentName = user.departmentName;
            this.companyName = user.companyName;
            this.path = user.path;
            this.index = user.index;
        }
    }

    public void set(JSONObject json) {
        if(json != null) {
            this.id = json.getInteger("id");
            this.provider = json.getString("provider");
            this.name = json.getString("name");
            this.gender = json.getInteger("gender");
            if(this.gender != null) {
                this.genderName = this.gender == 2 ? "女" : "男";
            }
            this.catagory = json.getInteger("catagory");
            this.positionStatus = json.getInteger("positionStatus");
            this.order = json.getInteger("order");
            this.company = json.getJSONObject("company");
            this.department = json.getJSONObject("department");
            this.position = json.getJSONObject("position");
            if(this.company != null) {
                this.companyName = this.company.getString("name");
            }
            if(this.department != null) {
                this.departmentName = this.department.getString("name");
            }
            if(this.position != null) {
                this.positionName = this.position.getString("name");
            }
            this.path = json.getString("path");
            this.index = json.getInteger("index");
        }
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void insertUserInfo(List<CRCCUserInfoDto> userInfoDtoList, CRCCUserInfoDto userInfo, CRCCUserInfoDto userInsert) {
        if (userInfoDtoList == null || userInfo == null || userInsert == null) {
            return;
        }

        if(!userInfoDtoList.isEmpty()) {
            for (int i=0; i<userInfoDtoList.size(); i++) {
                CRCCUserInfoDto user = userInfoDtoList.get(i);
                if (user != null && user.getId().equals(userInfo.getId()) && user.getProvider().equals(userInfo.getProvider())) {
                    userInfoDtoList.add(i,userInsert);
                }
            }
        }
    }
    public static void insertUserInfo(List<CRCCUserInfoDto> userInfoDtoList, String provider, Integer userId, CRCCUserInfoDto userInsert) {
        if (userInfoDtoList == null || provider == null || provider.length() == 0 || userId == null || userInsert == null) {
            return;
        }

        if(!userInfoDtoList.isEmpty()) {
            for (int i=0; i<userInfoDtoList.size(); i++) {
                CRCCUserInfoDto user = userInfoDtoList.get(i);
                if (user != null && user.getId().equals(userId) && user.getProvider().equals(provider)) {
                    userInfoDtoList.add(i,userInsert);
                }
            }
        }
    }
    public static void updateUserInfo(List<CRCCUserInfoDto> userInfoDtoList, CRCCUserInfoDto userUpdate) {
        if (userInfoDtoList == null || userUpdate == null) {
            return;
        }

        if(!userInfoDtoList.isEmpty()) {
            for (int i=0; i<userInfoDtoList.size(); i++) {
                CRCCUserInfoDto user = userInfoDtoList.get(i);
                if (user != null && user.getId().equals(userUpdate.getId()) && user.getProvider().equals(userUpdate.getProvider())) {
                    userInfoDtoList.set(i,userUpdate);
                }
            }
        }
    }
    public static void removeUserInfo(List<CRCCUserInfoDto> userInfoDtoList, CRCCUserInfoDto userRemove) {
        if (userInfoDtoList == null || userRemove == null) {
            return;
        }

        if(!userInfoDtoList.isEmpty()) {
            for (int i=0; i<userInfoDtoList.size(); i++) {
                CRCCUserInfoDto user = userInfoDtoList.get(i);
                if (user != null && user.getId().equals(userRemove.getId()) && user.getProvider().equals(userRemove.getProvider())) {
                    userInfoDtoList.remove(userRemove);
                }
            }
        }
    }
    public static void removeUserInfo(List<CRCCUserInfoDto> userInfoDtoList, String provider, Integer userId) {
        if (userInfoDtoList == null || provider == null || provider.length() == 0 || userId == null) {
            return;
        }

        if(!userInfoDtoList.isEmpty()) {
            for (int i=0; i<userInfoDtoList.size(); i++) {
                CRCCUserInfoDto user = userInfoDtoList.get(i);
                if (user != null && user.getId().equals(userId) && user.getProvider().equals(provider)) {
                    userInfoDtoList.remove(user);
                }
            }
        }
    }

    public static void orderUserInfoListAsc(List<CRCCUserInfoDto> userInfoDtoList) {
        if (userInfoDtoList != null) {
            userInfoDtoList.sort(Comparator.comparing(CRCCUserInfoDto::getOrder));
        }
    }
    public static void orderUserInfoListDesc(List<CRCCUserInfoDto> userInfoDtoList) {
        if (userInfoDtoList != null) {
            userInfoDtoList.sort(Comparator.comparing(CRCCUserInfoDto::getOrder).reversed());
        }
    }
    public static void sortUserInfoListAsc(List<CRCCUserInfoDto> userInfoDtoList) {
        if (userInfoDtoList != null) {
            userInfoDtoList.sort(Comparator.comparing(CRCCUserInfoDto::getIndex));
        }
    }
    public static void sortUserInfoListDesc(List<CRCCUserInfoDto> userInfoDtoList) {
        if (userInfoDtoList != null) {
            userInfoDtoList.sort(Comparator.comparing(CRCCUserInfoDto::getIndex).reversed());
        }
    }
    public static void orderUserInfoListOfProviderAsc(List<CRCCUserInfoDto> userInfoDtoList) {
        if (userInfoDtoList != null) {
            userInfoDtoList.sort(Comparator.comparing(CRCCUserInfoDto::getProvider).thenComparing(CRCCUserInfoDto::getOrder));
        }
    }
    public static void orderUserInfoListOfProviderDesc(List<CRCCUserInfoDto> userInfoDtoList) {
        if (userInfoDtoList != null) {
            userInfoDtoList.sort(Comparator.comparing(CRCCUserInfoDto::getProvider).thenComparing(CRCCUserInfoDto::getOrder).reversed());
        }
    }
    public static void sortUserInfoListOfProviderAsc(List<CRCCUserInfoDto> userInfoDtoList) {
        if (userInfoDtoList != null) {
            userInfoDtoList.sort(Comparator.comparing(CRCCUserInfoDto::getProvider).thenComparing(CRCCUserInfoDto::getIndex));
        }
    }
    public static void sortUserInfoListOfProviderDesc(List<CRCCUserInfoDto> userInfoDtoList) {
        if (userInfoDtoList != null) {
            userInfoDtoList.sort(Comparator.comparing(CRCCUserInfoDto::getProvider).thenComparing(CRCCUserInfoDto::getIndex).reversed());
        }
    }

    public static CRCCUserInfoDto findUserInfoById(List<CRCCUserInfoDto> userInfoDtoList, String provider, Integer id) {
        if (provider == null || provider.length() == 0 || id == null) {
            return null;
        }

        if(!userInfoDtoList.isEmpty()) {
            for (Iterator<CRCCUserInfoDto> iterator = userInfoDtoList.iterator(); iterator.hasNext(); ) {
                CRCCUserInfoDto user = iterator.next();
                if (user != null && user.getId().equals(id) && user.getProvider().equals(provider)) {
                    return user;
                }
            }
        }

        return null;
    }
    public static List<CRCCUserInfoDto> findUserInfoByIdList(List<CRCCUserInfoDto> userInfoDtoList, String provider, List<Integer> listId) {
        if (provider == null || provider.length() == 0 || listId == null) {
            return null;
        }

        List<CRCCUserInfoDto> listUsers = new ArrayList<>();
        if(!userInfoDtoList.isEmpty()) {
            for (Iterator<CRCCUserInfoDto> iterator = userInfoDtoList.iterator(); iterator.hasNext(); ) {
                CRCCUserInfoDto user = iterator.next();
                if (user != null && listId.contains(user.getId()) && user.getProvider().equals(provider)) {
                    listUsers.add(user);
                }
            }
        }

        return !listUsers.isEmpty() ? listUsers : null;
    }
    public static List<CRCCUserInfoDto> findUserInfoList(List<CRCCUserInfoDto> userInfoDtoList, Integer offset, Integer limit) {
        int endIndex = userInfoDtoList.size();
        int fromIndex = offset == null ? 0 : Math.min(offset, endIndex);
        int toIndex = limit == null ? endIndex : Math.min(fromIndex+limit, endIndex);

        List<CRCCUserInfoDto> listUsers = userInfoDtoList.subList(fromIndex, toIndex);

        int index = fromIndex+1;
        for(CRCCUserInfoDto x: listUsers) {
            x.setIndex(index++);

            String company = x.getCompanyName();

            if(company != null && company.contains("中国铁建/")) {
                x.setCompanyName(company.substring(5));
            }
        }

        return !listUsers.isEmpty() ? listUsers : null;
    }

}
