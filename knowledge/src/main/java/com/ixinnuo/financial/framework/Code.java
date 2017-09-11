package com.ixinnuo.financial.framework;

/**
 * 错误信息枚举类型
 * @ClassName: Error
 * @Description: TODO
 *
 */
public enum Code {
    /**
     * 成功
     */
    OK(0, "成功"),
    /**
     * 失败
     */
    ERROR(9999, "成功"),
    /*10段，用于定义入参错误*/
    /**
     * 参数为空
     */
    EMPTY_PARAMETER_ERROR(1001, "参数为空"),
    /*XX段，用于定义XX级错误*/
    PRODUCT_NOT_FOUND(2001, "没有找到对应的产品"),
    /*查询单个产品没有找到对应Product*/
    ;
    /**
     * 错误代码
     */
    private final int code;

    /**
     * 错误信息
     */
    private final String msg;

    private Code(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
