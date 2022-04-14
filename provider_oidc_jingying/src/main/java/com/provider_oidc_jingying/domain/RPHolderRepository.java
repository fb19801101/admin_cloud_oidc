package com.provider_oidc_jingying.domain;


/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public interface RPHolderRepository {

    RPHolder findRPHolder();

    boolean saveRPHolder(RPHolder rpHolder);

}
