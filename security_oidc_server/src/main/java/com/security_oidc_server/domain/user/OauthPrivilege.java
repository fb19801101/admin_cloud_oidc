package com.security_oidc_server.domain.user;



import com.security_oidc_server.domain.AbstractDomain;

import javax.persistence.*;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Entity
@Table(name = "oauth_privilege")
public class OauthPrivilege extends AbstractDomain {

    private static final long serialVersionUID = -7207158121413995079L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private OauthUser user;

    @Column(name = "privilege")
    @Enumerated(value = EnumType.STRING)
    private Privilege privilege;

    public OauthPrivilege() {
    }

    public OauthPrivilege(OauthUser user, Privilege privilege) {
        this.user = user;
        this.privilege = privilege;
    }

    public Privilege privilege(String privilege) {
        this.privilege = Privilege.valueOf(privilege);
        return this.privilege;
    }

    public OauthUser user() {
        return user;
    }
}