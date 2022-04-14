package com.security_oidc_client.infrastructure;

import com.security_oidc_client.domain.RPHolder;
import com.security_oidc_client.domain.RPHolderRepository;
import com.security_oidc_client.domain.shared.Application;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.*;

/**
 * <p>
 * 默认存储 RPHolder 在临时文件中/
 * <p>
 * 实际使用时用具体的实现（如存数据库）
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Repository
public class RPHolderRepositoryDefault implements RPHolderRepository {

    private static final Logger LOG = LoggerFactory.getLogger(RPHolderRepositoryDefault.class);

    //文件名
    private static final String FILE_LOCAL = "security_oidc_client/oidc-client-rpholder.json";
//    private static final String FILE_LOCAL = "C://oidc-client-rpholder.json";
    private static final String FILE_SERVE = "/opt/oidc-project/config/oidc-client/oidc-client-rpholder.json";

    //临时文件对象
    private File file;

    @Override
    public RPHolder findRPHolder() {
        try {
            File file = getFile();
            String json = FileCopyUtils.copyToString(new FileReader(file));
            if(StringUtils.isNotBlank(json)){
                Map<String, Object> map = JsonUtil.parseJson(json);
                return new RPHolder(map);
            }
        } catch (Exception e) {
            LOG.error("File handle error", e);
        }
        //返回一空对象
        return new RPHolder();
    }

    @Override
    public RPHolder findApiRPHolder(String apiName) {
        try {
            File file = getFile();
            String json = FileCopyUtils.copyToString(new FileReader(file));
            if(StringUtils.isNotBlank(json)){
                Map<String, Object> map = JsonUtil.parseJson(json);
                Map<String, Object> apis = (LinkedHashMap<String, Object>)map.get("apiPrograms");
                for(Map.Entry x : apis.entrySet()) {
                    String k = x.getKey().toString();
                    if(k.compareTo(apiName) == 0) {
                        return new RPHolder((LinkedHashMap<String, Object>)x.getValue());
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("File handle error", e);
        }
        //返回一空对象
        return new RPHolder();
    }

    @Override
    public Map<String, RPHolder> findApiRPHolders() {
        try {
            File file = getFile();
            String json = FileCopyUtils.copyToString(new FileReader(file));
            if(StringUtils.isNotBlank(json)){
                Map<String, Object> map = JsonUtil.parseJson(json);
                Map<String, Object> apis = (LinkedHashMap<String, Object>)map.get("apiPrograms");
                Map<String, Map<String, Object>> programs = new LinkedHashMap<>();
                for(Map.Entry x : apis.entrySet()) {
                    String k = x.getKey().toString();
                    Map<String, Object> v = (LinkedHashMap<String, Object>)x.getValue();
                    programs.put(k,v);
                }

                return RPHolder.getApis(programs);
            }
        } catch (Exception e) {
            LOG.error("File handle error", e);
        }
        //返回一空对象
        return null;
    }

    @Override
    public List<String> findApiRPHolderNameList() {
        try {
            File file = getFile();
            String json = FileCopyUtils.copyToString(new FileReader(file));
            if(StringUtils.isNotBlank(json)){
                Map<String, Object> map = JsonUtil.parseJson(json);
                Map<String, Object> apis = (LinkedHashMap<String, Object>)map.get("apiPrograms");
                return new ArrayList<>(apis.keySet());
            }
        } catch (Exception e) {
            LOG.error("File handle error", e);
        }
        //返回一空对象
        return null;
    }

    @Override
    public boolean saveRPHolder(RPHolder rpHolder) {
        try {
            File file = getFile();
            Map<String, RPHolder> apis = findApiRPHolders();
            String json = RPHolder.apisToJSON(rpHolder, apis);
            FileCopyUtils.copy(json, new FileWriter(file));
            return true;
        } catch (IOException e) {
            LOG.error("File handle error", e);
        }
        return false;
    }

    @Override
    public boolean saveApiRPHolder(String apiName, RPHolder apiRpHolder) {
        try {
            File file = getFile();
            Map<String, RPHolder> apis = findApiRPHolders();
            apis.put(apiName, apiRpHolder);
            RPHolder rpHolder = findRPHolder();
            String json = RPHolder.apisToJSON(rpHolder, apis);
            FileCopyUtils.copy(json, new FileWriter(file));
            return true;
        } catch (IOException e) {
            LOG.error("File handle error", e);
        }
        return false;
    }

    private File getFile() throws IOException {
        if (file != null && file.exists()) {
            LOG.debug("Use cached file: {}", file);
            return file;
        }

//        String path = ResourceUtils.getFile("classpath:config/").getPath();
//        String FILE = path+"/"+FILE_LOCAL;

        String FILE = Application.ip() != null && Application.ip().compareTo("localhost") == 0 ? FILE_LOCAL : FILE_SERVE;
        file = new File(FILE);
        if (!file.exists()) {
            boolean ok = file.createNewFile();
            LOG.debug("Create file: {} result: {}", FILE, ok);
        }
        return file;
    }
}
