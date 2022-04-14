package com.gateway.entity;

import com.alibaba.fastjson.util.TypeUtils;

import java.util.Objects;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-04-28 18:04
 */
public class ResultData<T>  implements Cloneable{
    private int code;
    private String msg;
    private long count;
    private T data;

    public ResultData() {
    }

    public ResultData(int code, String msg, T data, long count) {
        this.msg = msg;
        this.data = data;
        this.code = code;
        this.count = count;
    }

    public ResultData(int code, String msg, T data) {
        this.msg = msg;
        this.data = data;
        this.code = code;
    }

    public ResultData(int code, T data, long count) {
        this.data = data;
        this.code = code;
        this.count = count;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public long getCount() {
        return count;
    }
    public void setCount(long count) {
        this.count = count;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public T data(Class<T> clazz) {
        return (T)TypeUtils.castToJavaBean(this.data, clazz);
    }

    @Override
    public T clone() throws CloneNotSupportedException {
        T org = (T)super.clone();
        return CloneUtils.cloneTo(org);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ResultData<T> success(T data) {
        return new ResultData<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param msg 提示信息
     */
    public static <T> ResultData<T> success(T data, String msg) {
        return new ResultData<T>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> ResultData<T> failed(IErrorCode errorCode) {
        return new ResultData<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     *
     * @param msg 提示信息
     */
    public static <T> ResultData<T> failed(String msg) {
        return new ResultData<T>(ResultCode.FAILED.getCode(), msg, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> ResultData<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> ResultData<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param msg 提示信息
     */
    public static <T> ResultData<T> validateFailed(String msg) {
        return new ResultData<T>(ResultCode.VALIDATE_FAILED.getCode(), msg, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> ResultData<T> unauthorized(T data) {
        return new ResultData<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> ResultData<T> forbidden(T data) {
        return new ResultData<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResultData<?> resultData = (ResultData<?>) o;
        return code == resultData.code &&
                count == resultData.count &&
                Objects.equals(msg, resultData.msg) &&
                Objects.equals(data, resultData.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, msg, count, data);
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data.toString() +
                '}';
    }
}
