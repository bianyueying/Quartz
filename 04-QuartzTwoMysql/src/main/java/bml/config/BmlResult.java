package bml.config;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 边月白
 * Date 2020/3/7 18:36
 * 自定义返回结果封装类
 * 本类序列化以及无参构造以解决前端不能有效接收并展示缓存里的数据问题
 */
@Data
public class BmlResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 封装数据
     */
    private T data;

    /**
     * 返回状态码
     */
    private Integer status;

    /**
     * 返回自定义信息
     */
    private String msg;


    /**
     * 无参构造，不可或缺
     */
    public BmlResult() {
    }

    /**
     * 如没有数据返回，则可以自定义指定状态码和提示信息
     */
    public BmlResult(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    /**
     * 用于返回查询的集合
     * @param data 封装数据
     * @param status 状态码
     */
    public BmlResult(T data,Integer status) {
        this.data = data;
        this.status = status;
    }

}
