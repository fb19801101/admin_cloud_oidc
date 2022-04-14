package com.provider_oidc_gongchengsl.service.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
public class CRCCOrganizationDto extends AbstractOauthDto implements Cloneable{

    private Integer type;

    private Integer id;

    private String provider;

    private String name;

    private String fullname;

    private String code;

    private Integer order;

    private Boolean show;

    private Boolean virtual;

    private Integer level;

    private Integer index;

    private List<CRCCOrganizationDto> children;

    private List<CRCCUserInfoDto> users;

    public static Integer orgIndex;
    public static Integer userIndex;

    public void set(CRCCOrganizationDto org) {
        if(org != null) {
            this.type = org.type;
            this.id = org.id;
            this.provider = org.provider;
            this.name = org.name;
            this.fullname = org.fullname;
            this.code = org.code;
            this.order = org.order;
            this.show = org.show;
            this.virtual = org.virtual;
            this.level = org.level;
            this.index = org.index;
            this.children = CloneUtils.cloneTo(org.children);
            this.users = CloneUtils.cloneTo(org.users);
        }
    }

    @Override
    public CRCCOrganizationDto clone() throws CloneNotSupportedException {
        CRCCOrganizationDto org = (CRCCOrganizationDto) super.clone();
        return CloneUtils.cloneTo(org);
    }


    public static void stackOrganization(CRCCOrganizationDto organizationDto, Stack<CRCCOrganizationDto> stackOrganizationDto) {
        if (organizationDto != null && stackOrganizationDto != null) {
            stackOrganizationDto.push(organizationDto);
            if (organizationDto.getChildren() != null) {
                for (CRCCOrganizationDto x : organizationDto.getChildren()) {
                    stackOrganization(x, stackOrganizationDto);
                }
            }
        }
    }
    public static void queueOrganization(CRCCOrganizationDto organizationDto, Queue<CRCCOrganizationDto> queueOrganizationDto) {
        if (organizationDto != null && queueOrganizationDto != null) {
            queueOrganizationDto.offer(organizationDto);
            if (organizationDto.getChildren() != null) {
                for (CRCCOrganizationDto x : organizationDto.getChildren()) {
                    queueOrganization(x, queueOrganizationDto);
                }
            }
        }
    }
    public static void listOrganization(CRCCOrganizationDto organizationDto, List<CRCCOrganizationDto> listOrganizationDto) {
        if (organizationDto != null && listOrganizationDto != null) {
            listOrganizationDto.add(organizationDto);
            if (organizationDto.getChildren() != null) {
                for (CRCCOrganizationDto x : organizationDto.getChildren()) {
                    listOrganization(x, listOrganizationDto);
                }
            }
        }
    }

    public static void filterOrganizationByType(CRCCOrganizationDto organizationDto, String provider, Integer type) {
        if (provider == null || provider.length() == 0 || type == null) {
            return;
        }

        if(organizationDto != null) {
            if (organizationDto.getChildren() != null) {
                List<CRCCOrganizationDto> orgList = new ArrayList<>();
                for (CRCCOrganizationDto x : organizationDto.getChildren()) {
                    if (!x.getType().equals(type) && !x.getProvider().equals(provider)) {
                        orgList.add(x);
                        filterOrganizationByType(x, provider, type);
                    }
                }
                organizationDto.setChildren(orgList.size() == 0 ? null : orgList);
            }
        }
    }
    public static void filterOrganizationByType(CRCCOrganizationDto organizationDto, Integer type) {
        if (organizationDto == null || type == null) {
            return;
        }

        List<CRCCOrganizationDto> orgChildren = organizationDto.getChildren();
        if (orgChildren != null) {
            for (int i= 0; i< orgChildren.size(); i++) {
                CRCCOrganizationDto org = orgChildren.get(i);
                if (org != null) {
                    if (org.getType().equals(type)) {
                        orgChildren.remove(org);
                        return;
                    } else {
                        filterOrganizationByType(org, type);
                    }
                }
            }
        }
    }
    public static void plusOrganizationFromUsers(CRCCOrganizationDto organizationDto) {
        if(organizationDto != null) {
            if (organizationDto.getChildren() != null) {
                for (CRCCOrganizationDto x : organizationDto.getChildren()) {
                    if (x.getType().equals(3)) {
                        List<CRCCUserInfoDto> userList = CloneUtils.cloneTo(x.getUsers());
                        List<CRCCOrganizationDto> orgList = new ArrayList<>();
                        if(userList != null) {
                            for(CRCCUserInfoDto y: userList) {
                                CRCCOrganizationDto org  = new CRCCOrganizationDto();
                                org.setId(y.getId());
                                org.setProvider(y.getProvider());
                                org.setName(y.getName());
                                org.setFullname(y.getName());
                                org.setLevel(x.getLevel()+1);
                                org.setOrder(y.getOrder());
                                org.setType(x.getType()+1);
                                orgList.add(org);
                            }
                        }
                        x.setChildren(orgList);
                        x.setUsers(null);
                    } else {
                        plusOrganizationFromUsers(x);
                    }
                }
            }
        }
    }
    public static void initIndex() {
        orgIndex = 0;
        userIndex = 0;
    }
    public static void initOrganization(CRCCOrganizationDto organizationDto, JSONObject company, JSONObject department) {
        if (organizationDto == null || company == null || department == null) {
            return;
        }

        String providerId = organizationDto.getProvider();
        Integer orgId = organizationDto.getId();
        Integer orgType = organizationDto.getType();
        Integer level = organizationDto.getLevel();
        String name = organizationDto.getName();
        Boolean virtual = organizationDto.getVirtual();

        JSONObject _company = CloneUtils.cloneTo(company);
        JSONObject _department = CloneUtils.cloneTo(department);
        String _name = _department.containsKey("name") ? _department.getString("name")+"/" : "";
        if(orgType == 1 && !virtual) {
            _company.put("provider", providerId);
            _company.put("id", orgId);
            _company.put("name", company.getString("name")+"/"+name);
        } else if(orgType == 2) {
            _department.put("provider", providerId);
            _department.put("id", orgId);
            _department.put("name", _name+name);
        }

        List<CRCCOrganizationDto> orgChildren = organizationDto.getChildren();
        if (orgChildren != null) {
            orgChildren.sort(Comparator.comparing(CRCCOrganizationDto::getOrder));

            for (CRCCOrganizationDto x : orgChildren) {
                x.setLevel(level + 1);
                x.setIndex(orgIndex++);

                if(x.getProvider() == null) {
                    x.setProvider(organizationDto.getProvider());
                }

                if (x.getType() < 3) {
                    initOrganization(x, _company, _department);
                } else if (x.getType() == 3) {
                    ExecutorService es = Executors.newCachedThreadPool();
                    es.submit(() -> {
                        try {
                            JSONObject _position = new JSONObject();
                            _position.put("provider", x.getProvider());
                            _position.put("id", x.getId());
                            _position.put("name", x.getName());

                            List<CRCCUserInfoDto> listUsers = x.getUsers();
                            if(listUsers != null) {
                                listUsers.sort(Comparator.comparing(CRCCUserInfoDto::getOrder));

                                for (CRCCUserInfoDto y : listUsers) {
                                    y.set(userIndex++, x.getProvider(), _company, _department, _position);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    es.shutdown();
                }
            }
        }
    }
    public static void initOrganizationLevel(CRCCOrganizationDto organizationDto) {
        if (organizationDto != null) {
            Integer level = organizationDto.getLevel();

            List<CRCCOrganizationDto> orgChildren = organizationDto.getChildren();
            if (orgChildren != null && level != null) {
                orgChildren.sort(Comparator.comparing(CRCCOrganizationDto::getOrder));

                for (CRCCOrganizationDto x : orgChildren) {
                    x.setLevel(level + 1);

                    if (x.getType() < 3) {
                        initOrganizationLevel(x);
                    }
                }
            }
        }
    }
    public static void initOrganizationLevel(CRCCOrganizationDto organizationDto, Integer level) {
        if (organizationDto != null && level != null) {
            organizationDto.setLevel(level);

            List<CRCCOrganizationDto> orgChildren = organizationDto.getChildren();
            if (orgChildren != null) {
                orgChildren.sort(Comparator.comparing(CRCCOrganizationDto::getOrder));

                for (CRCCOrganizationDto x : orgChildren) {
                    x.setLevel(level);

                    if (x.getType() < 3) {
                        initOrganizationLevel(x, level);
                    }
                }
            }
        }
    }
    public static void initOrganizationIndex(CRCCOrganizationDto organizationDto) {
        if (organizationDto != null) {
            Integer index = organizationDto.getIndex();

            List<CRCCOrganizationDto> orgChildren = organizationDto.getChildren();
            if (orgChildren != null && index != null) {
                orgChildren.sort(Comparator.comparing(CRCCOrganizationDto::getOrder));

                for (CRCCOrganizationDto x : orgChildren) {
                    x.setIndex(index++);

                    if (x.getType() < 3) {
                        initOrganizationIndex(x);
                    }
                }
            }
        }
    }
    public static void initOrganizationIndex(CRCCOrganizationDto organizationDto, Integer index) {
        if (organizationDto != null && index != null) {
            organizationDto.setIndex(index);

            List<CRCCOrganizationDto> orgChildren = organizationDto.getChildren();
            if (orgChildren != null) {
                orgChildren.sort(Comparator.comparing(CRCCOrganizationDto::getOrder));

                for (CRCCOrganizationDto x : orgChildren) {
                    x.setIndex(index);

                    if (x.getType() < 3) {
                        initOrganizationIndex(x, index);
                    }
                }
            }
        }
    }

    public static void orderOrganizationListAsc(List<CRCCOrganizationDto> organizationDtoList) {
        if (organizationDtoList != null) {
            organizationDtoList.sort(Comparator.comparing(CRCCOrganizationDto::getOrder));
        }
    }
    public static void orderOrganizationListDesc(List<CRCCOrganizationDto> organizationDtoList) {
        if (organizationDtoList != null) {
            organizationDtoList.sort(Comparator.comparing(CRCCOrganizationDto::getOrder).reversed());
        }
    }
    public static void sortOrganizationListAsc(List<CRCCOrganizationDto> organizationDtoList) {
        if (organizationDtoList != null) {
            organizationDtoList.sort(Comparator.comparing(CRCCOrganizationDto::getIndex));
        }
    }
    public static void sortOrganizationListDesc(List<CRCCOrganizationDto> organizationDtoList) {
        if (organizationDtoList != null) {
            organizationDtoList.sort(Comparator.comparing(CRCCOrganizationDto::getIndex).reversed());
        }
    }
    public static void orderOrganizationListOfProviderAsc(List<CRCCOrganizationDto> organizationDtoList) {
        if (organizationDtoList != null) {
            organizationDtoList.sort(Comparator.comparing(CRCCOrganizationDto::getProvider).thenComparing(CRCCOrganizationDto::getOrder));
        }
    }
    public static void orderOrganizationListOfProviderDesc(List<CRCCOrganizationDto> organizationDtoList) {
        if (organizationDtoList != null) {
            organizationDtoList.sort(Comparator.comparing(CRCCOrganizationDto::getProvider).thenComparing(CRCCOrganizationDto::getOrder).reversed());
        }
    }
    public static void sortOrganizationListOfProviderAsc(List<CRCCOrganizationDto> organizationDtoList) {
        if (organizationDtoList != null) {
            organizationDtoList.sort(Comparator.comparing(CRCCOrganizationDto::getProvider).thenComparing(CRCCOrganizationDto::getIndex));
        }
    }
    public static void sortOrganizationListOfProviderDesc(List<CRCCOrganizationDto> organizationDtoList) {
        if (organizationDtoList != null) {
            organizationDtoList.sort(Comparator.comparing(CRCCOrganizationDto::getProvider).thenComparing(CRCCOrganizationDto::getIndex).reversed());
        }
    }

    public static void insertOrganization(CRCCOrganizationDto organizationDto, CRCCOrganizationDto organization, CRCCOrganizationDto orgInsert) {
        if (organizationDto == null || organization == null || orgInsert == null) {
            return;
        }

        List<CRCCOrganizationDto> orgChildren = organizationDto.getChildren();
        if (orgChildren != null) {
            for (CRCCOrganizationDto x: orgChildren) {
                if (x.getProvider().equals(organization.getProvider()) && x.getId().equals(organization.getId())) {
                    List<CRCCOrganizationDto> orgList = x.getChildren();
                    if(orgList != null) {
                        orgList.add(orgInsert);
                        orgList.sort(Comparator.comparing(CRCCOrganizationDto::getOrder));

                        Integer index = orgList.indexOf(orgInsert);
                        if(index > 0) {
                            CRCCOrganizationDto org = orgList.get(index - 1);
                            if(org != null) {
                                initOrganizationIndex(orgInsert, org.getIndex());
                            } else {
                                initOrganizationIndex(orgInsert, x.getIndex());
                            }
                            orgInsert.setLevel(x.getLevel());
                            initOrganizationLevel(orgInsert);
                        } else {
                            initOrganizationIndex(orgInsert, x.getIndex());

                            orgInsert.setLevel(x.getLevel());
                            initOrganizationLevel(orgInsert);
                        }
                        return;
                    }
                } else {
                    insertOrganization(x, organization, orgInsert);
                }
            }
        }
    }
    public static void insertOrganization(CRCCOrganizationDto organizationDto, String provider, Integer orgId, CRCCOrganizationDto orgInsert) {
        if (organizationDto == null || provider == null || provider.length() == 0 || orgId == null || orgInsert == null) {
            return;
        }

        List<CRCCOrganizationDto> orgChildren = organizationDto.getChildren();
        if (orgChildren != null) {
            for (CRCCOrganizationDto x: orgChildren) {
                if (x.getProvider().equals(provider) && x.getId().equals(orgId)) {
                    List<CRCCOrganizationDto> orgList = x.getChildren();
                    if(orgList != null) {
                        orgList.add(orgInsert);
                        orgList.sort(Comparator.comparing(CRCCOrganizationDto::getOrder));

                        Integer index = orgList.indexOf(orgInsert);
                        if(index > 0) {
                            CRCCOrganizationDto org = orgList.get(index - 1);
                            if(org != null) {
                                initOrganizationIndex(orgInsert, org.getIndex());
                            } else {
                                initOrganizationIndex(orgInsert, x.getIndex());
                            }
                            orgInsert.setLevel(x.getLevel());
                            initOrganizationLevel(orgInsert);
                        } else {
                            initOrganizationIndex(orgInsert, x.getIndex());

                            orgInsert.setLevel(x.getLevel());
                            initOrganizationLevel(orgInsert);
                        }
                        return;
                    }
                } else {
                    insertOrganization(x, provider, orgId, orgInsert);
                }
            }
        }
    }
    public static void updateOrganization(CRCCOrganizationDto organizationDto, CRCCOrganizationDto orgUpdate) {
        if (organizationDto == null || orgUpdate == null) {
            return;
        }

        List<CRCCOrganizationDto> orgChildren = organizationDto.getChildren();
        if (orgChildren != null) {
            for (int i= 0; i< orgChildren.size(); i++) {
                CRCCOrganizationDto org = orgChildren.get(i);
                if (org != null) {
                    if (org.getProvider().equals(orgUpdate.getProvider()) && org.getId().equals(orgUpdate.getId())) {
                        orgChildren.set(i, orgUpdate);
                        return;
                    } else {
                        updateOrganization(org, orgUpdate);
                    }
                }
            }
        }
    }
    public static void removeOrganization(CRCCOrganizationDto organizationDto, CRCCOrganizationDto orgRemove) {
        if (organizationDto == null || orgRemove == null) {
            return;
        }

        List<CRCCOrganizationDto> orgChildren = organizationDto.getChildren();
        if (orgChildren != null) {
            for (CRCCOrganizationDto x: orgChildren) {
                if (x.getProvider().equals(orgRemove.getProvider()) && x.getId().equals(orgRemove.getId())) {
                    orgChildren.remove(orgRemove);
                    return;
                } else {
                    removeOrganization(x, orgRemove);
                }
            }
        }
    }
    public static void removeOrganization(CRCCOrganizationDto organizationDto, String provider, Integer orgId) {
        if (organizationDto == null || provider == null || provider.length() == 0 || orgId == null) {
            return;
        }

        List<CRCCOrganizationDto> orgChildren = organizationDto.getChildren();
        if (orgChildren != null) {
            for (CRCCOrganizationDto x: orgChildren) {
                if (x.getProvider().equals(provider) && x.getId().equals(orgId)) {
                    orgChildren.remove(x);
                    return;
                } else {
                    removeOrganization(x, provider, orgId);
                }
            }
        }
    }

    public static void updateOrganizationUser(CRCCOrganizationDto organizationDto, CRCCUserInfoDto userInfo) {
        if (userInfo == null) {
            return;
        }

        List<CRCCOrganizationDto> orgChildren = organizationDto.getChildren();
        if (orgChildren != null) {
            for (CRCCOrganizationDto x: orgChildren) {
                if (x.getType() == 3) {
                    List<CRCCUserInfoDto> listUser = x.getUsers();
                    if(listUser != null) {
                        for(int i=0; i<listUser.size(); i++) {
                            CRCCUserInfoDto user = listUser.get(i);
                            if(user.getProvider().equals(userInfo.getProvider()) && user.getId().equals(userInfo.getId())) {
                                listUser.set(i, userInfo);
                                return;
                            }
                        }
                    }
                    return;
                } else {
                    updateOrganizationUser(x, userInfo);
                }
            }
        }
    }

    public static void treeOrganizationList(List<CRCCOrganizationDto> orgList) {
        if (orgList != null) {
            for (int i=1; i<orgList.size(); i++) {
                CRCCOrganizationDto org = orgList.get(i);
                CRCCOrganizationDto _org = orgList.get(i-1);
                if(org != null && _org != null) {
                    List<CRCCOrganizationDto> listOrg = new ArrayList<>();
                    listOrg.add(_org);
                    org.setChildren(listOrg);
                }
            }
        }
    }

    public static CRCCOrganizationDto findOrganizationById(CRCCOrganizationDto organizationDto, String provider, Integer id) {
        if (provider == null || provider.length() == 0 || id == null) {
            return null;
        }

        Queue<CRCCOrganizationDto> queueOrganizationDto = new LinkedList<>();
        queueOrganization(organizationDto, queueOrganizationDto);

        if(!queueOrganizationDto.isEmpty()) {
            for (Iterator<CRCCOrganizationDto> iterator = queueOrganizationDto.iterator(); iterator.hasNext(); ) {
                CRCCOrganizationDto org = iterator.next();
                if (org != null && org.getId().equals(id) && org.getProvider().equals(provider)) {
                    return org;
                }
            }
        }

        return null;
    }
    public static List<CRCCOrganizationDto> findOrganizationByIdList(CRCCOrganizationDto organizationDto, String provider, List<Integer> listOrgId) {
        if (provider == null || provider.length() == 0 || listOrgId == null) {
            return null;
        }

        Queue<CRCCOrganizationDto> queueOrganizationDto = new LinkedList<>();
        queueOrganization(organizationDto, queueOrganizationDto);

        List<CRCCOrganizationDto> orgList = new ArrayList<>();
        if(!queueOrganizationDto.isEmpty()) {
            for (Iterator<CRCCOrganizationDto> iterator = queueOrganizationDto.iterator(); iterator.hasNext(); ) {
                CRCCOrganizationDto org = iterator.next();
                if (org != null && listOrgId.contains(org.getId()) && org.getProvider().equals(provider)) {
                    orgList.add(org);
                }
            }
        }

        return !orgList.isEmpty() ? orgList : null;
    }
    public static List<CRCCOrganizationDto> findOrganizationListByType(CRCCOrganizationDto organizationDto, String provider, Integer type) {
        if (provider == null || provider.length() == 0 || type == null) {
            return null;
        }

        Queue<CRCCOrganizationDto> queueOrganizationDto = new LinkedList<>();
        queueOrganization(organizationDto, queueOrganizationDto);

        List<CRCCOrganizationDto> list = new ArrayList<>();
        if(!queueOrganizationDto.isEmpty()) {
            for (Iterator<CRCCOrganizationDto> iterator = queueOrganizationDto.iterator(); iterator.hasNext(); ) {
                CRCCOrganizationDto org = iterator.next();
                if (org != null && org.getType().equals(type) && org.getProvider().equals(provider)) {
                    list.add(org);
                }
            }
        }

        return list.size() == 0 ? null : list;
    }
    public static CRCCUserInfoDto findOrganizationUserByUserId(CRCCOrganizationDto organizationDto, String provider, Integer id) {
        if (provider == null || provider.length() == 0 || id == null) {
            return null;
        }

        Queue<CRCCOrganizationDto> queueOrganizationDto = new LinkedList<>();
        queueOrganization(organizationDto, queueOrganizationDto);

        if(!queueOrganizationDto.isEmpty()) {
            for (Iterator<CRCCOrganizationDto> iterator = queueOrganizationDto.iterator(); iterator.hasNext(); ) {
                CRCCOrganizationDto org = iterator.next();
                if (org != null && org.getType().equals(3) && org.getProvider().equals(provider)) {
                    List<CRCCUserInfoDto> list = org.getUsers();
                    if(list != null) {
                        for(CRCCUserInfoDto x: list) {
                            if(x.getId().equals(id)) {
                                return x;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }
    public static List<CRCCUserInfoDto> findOrganizationUsers(CRCCOrganizationDto organizationDto) {
        Queue<CRCCOrganizationDto> queueOrganizationDto = new LinkedList<>();
        queueOrganization(organizationDto, queueOrganizationDto);

        List<CRCCUserInfoDto> listUsers = new ArrayList<>();
        if(!queueOrganizationDto.isEmpty()) {
            for (Iterator<CRCCOrganizationDto> iterator = queueOrganizationDto.iterator(); iterator.hasNext(); ) {
                CRCCOrganizationDto org = iterator.next();
                if (org != null && org.getType().equals(3)) {
                    List<CRCCUserInfoDto> list = org.getUsers();
                    if(list != null) {
                        listUsers.addAll(list);
                    }
                }
            }
        }

        return listUsers;
    }
    public static List<CRCCUserInfoDto> findOrganizationUsers(CRCCOrganizationDto organizationDto, Integer offset, Integer limit) {
        Queue<CRCCOrganizationDto> queueOrganizationDto = new LinkedList<>();
        queueOrganization(organizationDto, queueOrganizationDto);

        Integer index = 0;
        Integer _offset = offset == null ? 0 : offset;
        Integer _limit = limit == null ? -1 : limit;
        List<CRCCUserInfoDto> listUsers = new ArrayList<>();
        if(!queueOrganizationDto.isEmpty()) {
            for (Iterator<CRCCOrganizationDto> iterator = queueOrganizationDto.iterator(); iterator.hasNext(); ) {
                CRCCOrganizationDto org = iterator.next();
                if (org != null && org.getType().equals(3)) {
                    List<CRCCUserInfoDto> list = org.getUsers();
                    if(list != null) {
                        if(index >= _offset) {
                            for(int i=0; i< list.size(); i++) {
                                listUsers.add(list.get(i));
                                if(_limit > 0 && listUsers.size() >= _limit) {
                                    return listUsers;
                                }
                            }
                        }

                        index += list.size();
                    }
                }
            }
        }

        return listUsers.isEmpty() ? null : listUsers;
    }
    public static CRCCOrganizationDto findOrganizationByUserId(CRCCOrganizationDto organizationDto, String provider, Integer userId) {
        if (provider == null || provider.length() == 0 || userId == null) {
            return null;
        }

        Queue<CRCCOrganizationDto> queueOrganizationDto = new LinkedList<>();
        queueOrganization(organizationDto, queueOrganizationDto);

        if(!queueOrganizationDto.isEmpty()) {
            for (Iterator<CRCCOrganizationDto> iterator = queueOrganizationDto.iterator(); iterator.hasNext(); ) {
                CRCCOrganizationDto org = iterator.next();
                if (org != null && org.getType() == 3 && org.getProvider().equals(provider)) {
                    List<CRCCUserInfoDto> list = org.getUsers();
                    if(list != null) {
                        for(CRCCUserInfoDto x: list) {
                            if(x.getId().equals(userId)) {
                                return org;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }
    public static List<CRCCOrganizationDto> findOrganizationByUserIdList(CRCCOrganizationDto organizationDto, String provider, List<Integer> listUserId) {
        if (provider == null || provider.length() == 0 || listUserId == null) {
            return null;
        }

        Queue<CRCCOrganizationDto> queueOrganizationDto = new LinkedList<>();
        queueOrganization(organizationDto, queueOrganizationDto);

        List<CRCCOrganizationDto> orgList = new ArrayList<>();
        if(!queueOrganizationDto.isEmpty()) {
            for (Iterator<CRCCOrganizationDto> iterator = queueOrganizationDto.iterator(); iterator.hasNext(); ) {
                CRCCOrganizationDto org = iterator.next();
                if (org != null && org.getType() == 3 && org.getProvider().equals(provider)) {
                    List<CRCCUserInfoDto> list = org.getUsers();
                    if(list != null) {
                        for(CRCCUserInfoDto x: list) {
                            if(listUserId.contains(x.getId())) {
                                orgList.add(org);
                            }
                        }
                    }
                }
            }
        }

        return !orgList.isEmpty() ? orgList : null;
    }

    public static Integer findOrganizationIndexByUserId(CRCCOrganizationDto organizationDto, String provider, Integer id) {
        if (provider == null || provider.length() == 0 || id == null) {
            return null;
        }

        Queue<CRCCOrganizationDto> queueOrganizationDto = new LinkedList<>();
        queueOrganization(organizationDto, queueOrganizationDto);

        if(!queueOrganizationDto.isEmpty()) {
            for (Iterator<CRCCOrganizationDto> iterator = queueOrganizationDto.iterator(); iterator.hasNext(); ) {
                CRCCOrganizationDto org = iterator.next();
                if (org != null && org.getType().equals(3) && org.getProvider().equals(provider)) {
                    List<CRCCUserInfoDto> list = org.getUsers();
                    if(list != null) {
                        for(CRCCUserInfoDto x: list) {
                            if(x.getId().equals(id)) {
                                return org.getIndex();
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    public static CRCCOrganizationDto findOrganizationParentById(CRCCOrganizationDto organizationDto, String provider, Integer id) {
        if (provider == null || provider.length() == 0 || id == null) {
            return null;
        }

        Stack<CRCCOrganizationDto> stackOrganizationDto = new Stack<>();
        stackOrganization(organizationDto, stackOrganizationDto);

        if(!stackOrganizationDto.isEmpty()) {
            while (!stackOrganizationDto.empty()) {
                CRCCOrganizationDto org = stackOrganizationDto.pop();
                if (org != null && org.getId().equals(id) && org.getProvider().equals(provider)) {
                    return stackOrganizationDto.pop();
                }
            }
        }

        return null;
    }
    public static List<CRCCOrganizationDto> findOrganizationPathById(CRCCOrganizationDto organizationDto, String provider, Integer id) {
        if (provider == null || provider.length() == 0 || id == null) {
            return null;
        }

        Stack<CRCCOrganizationDto> stackOrganizationDto = new Stack<>();
        stackOrganization(organizationDto, stackOrganizationDto);

        List<CRCCOrganizationDto> list = new ArrayList<>();
        if(!stackOrganizationDto.isEmpty()) {
            while (!stackOrganizationDto.empty()) {
                CRCCOrganizationDto org = stackOrganizationDto.pop();
                if (org != null && org.getId().equals(id) && org.getProvider().equals(provider)) {
                    list.add(org);

                    while (!stackOrganizationDto.empty()) {
                        CRCCOrganizationDto _org = stackOrganizationDto.pop();
                        if(_org != null && _org.getLevel() < org.getLevel()) {
                            org = _org;
                            list.add(org);
                        }
                    }
                }
            }
        }

        return list.isEmpty() ? null : list;
    }
    public static String findOrganizationPathStrById (CRCCOrganizationDto organizationDto, String provider, Integer id) {
        if (provider == null || provider.length() == 0 || id == null) {
            return null;
        }

        StringBuilder path = new StringBuilder();
        List<CRCCOrganizationDto> orgList = findOrganizationPathById(organizationDto, provider, id);

        if(orgList != null) {
            for (int i = orgList.size() - 1; i >= 0; i--) {
                path.append(orgList.get(i).getName()).append("/");
            }
        }

        return path.substring(0,path.length()-1);
    }

    public static String findOrganizationNameFromPath (String path, Integer level) {
        if (path == null || path.length() == 0 || level == null) {
            return null;
        }

        String[] split = path.split("/");

        if(split.length <= 1) {
            return path;
        }

        if(split.length >= level) {
            if(level == -1) {
                return split[split.length - 1];
            } else if(level == 0) {
                    return path;
            } else {
                return split[level - 1];
            }
        }

        return null;
    }
    public static String findOrganizationSubFromPath(String path, Integer level) {
        if (path == null || path.length() == 0 || level == null) {
            return null;
        }

        String[] split = path.split("/");

        if(split.length <= 1) {
            return path;
        }

        if(split.length >= level) {
            if(level == -1) {
                StringBuilder _path = new StringBuilder();
                for(int i=0; i<split.length-1; i++) {
                    String item = split[i];
                    _path.append(item).append("/");
                }
                return _path.substring(0, _path.length()-1);
            } else if(level == 0) {
                return path;
            } else {
                StringBuilder _path = new StringBuilder();
                for(int i=0; i<split.length; i++) {
                    String item = split[i];
                    _path.append(item).append("/");
                    if(i == level-1) {
                        return _path.substring(0, _path.length()-1);
                    }
                }
            }
        }

        return null;
    }
}