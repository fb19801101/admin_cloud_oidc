package com.provider_oidc_shebeigys.service.dto;



import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public abstract class AbstractOauthDto implements Serializable {


    private static final long serialVersionUID = -5902798061281438305L;
    //Error if have from oauth server
    protected String errorDescription;
    protected String error;


    //original data
    protected String originalText;


    protected AbstractOauthDto() {
    }


    public boolean error() {
        return StringUtils.isNotEmpty(error) || StringUtils.isNotEmpty(errorDescription);
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}