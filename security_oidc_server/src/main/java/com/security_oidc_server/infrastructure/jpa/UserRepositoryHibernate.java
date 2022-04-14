package com.security_oidc_server.infrastructure.jpa;

import com.google.common.collect.ImmutableMap;
import com.security_oidc_server.domain.user.OauthUser;
import com.security_oidc_server.domain.user.Privilege;
import com.security_oidc_server.domain.user.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Repository
public class UserRepositoryHibernate extends AbstractRepositoryHibernate<OauthUser> implements UserRepository {

    @Override
    public OauthUser findLoginUserByUsername(String username) {
        final String hql = "from OauthUser ou where ou.username = :username and ou.archived = false";
        final List<OauthUser> list = find(hql, ImmutableMap.of("username", username));

        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Privilege> findUserPrivileges(String userUuid) {
        final String hql = "select op.privilege from OauthPrivilege op where op.archived = false and op.user.uuid = :userUuid";
        final Query query = session().createQuery(hql).setParameter("userUuid", userUuid);
        return query.getResultList();
    }

    @Override
    public List<OauthUser> findUsersByUsername(String username) {
        if (StringUtils.isNoneBlank(username)) {
            final String hql = "from OauthUser ou where ou.username like :username and ou.archived = false order by ou.id desc ";
            // 右半 %,  使用索引
            return find(hql, ImmutableMap.of("username", username + "%"));
        } else {
            return find("from OauthUser ou where ou.archived = false order by ou.id desc ");
        }
    }

    @Override
    public OauthUser findUserByUsernameNoArchived(String username) {
        final String hql = "from OauthUser ou where ou.username = :username ";
        final List<OauthUser> list = find(hql, ImmutableMap.of("username", username));
        return list.isEmpty() ? null : list.get(0);
    }

}
