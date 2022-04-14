package com.provider_oidc_gongxu.infrastructure;

import com.provider_oidc_gongxu.domain.RPHolder;
import com.provider_oidc_gongxu.domain.RPHolderRepository;
import com.provider_oidc_gongxu.domain.shared.Application;
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
    private static final String FILE_LOCAL = "provider_oidc_gongxu/oidc-gongxu-rpholder.json";
    private static final String FILE_SERVE = "/opt/oidc-project/config/oidc-gongxu/oidc-gongxu-rpholder.json";

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
    public boolean saveRPHolder(RPHolder rpHolder) {
        try {
            File file = getFile();
            String json = rpHolder.toJSON();
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
