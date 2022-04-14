package com.security_oidc_server.domain.shared;


import com.security_oidc_server.domain.AbstractDomain;

import java.util.Collection;
import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public interface Repository {

    <T extends AbstractDomain> T findById(Integer id, Class<T> clazz);

    <T extends AbstractDomain> T findByUuid(Class<T> clazz, String uuid);

    <T extends AbstractDomain> void saveOrUpdate(T domain);

    <T extends AbstractDomain> void saveOrUpdateAll(Collection<T> collection);

    <T extends AbstractDomain> void delete(T domain);

    <T extends AbstractDomain> void deleteByUuid(Class<T> clazz, String uuid);

    <T extends AbstractDomain> void deleteAll(Collection<T> elements);

    <T extends AbstractDomain> List<T> findAll(Class<T> clazz, boolean active);

    <T extends AbstractDomain> List<T> findByUuids(Class<T> clazz, List<String> uuids);

}