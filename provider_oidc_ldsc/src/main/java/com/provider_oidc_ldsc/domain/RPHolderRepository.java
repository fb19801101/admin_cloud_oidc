package com.provider_oidc_ldsc.domain;


import java.util.List;
import java.util.Map;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public interface RPHolderRepository {

    RPHolder findRPHolder();

    RPHolder findApiRPHolder(String apiName);

    Map<String, RPHolder> findApiRPHolders();

    List<String> findApiRPHolderNameList();

    boolean saveRPHolder(RPHolder rpHolder);

    boolean saveApiRPHolder(String apiName, RPHolder apiRpHolder);

}
