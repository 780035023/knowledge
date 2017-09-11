package com.ixinnuo.financial.framework;

import java.util.List;

public class LoginInfo {

    /**
     * 错误代码
     */
    public int errorCode;

    /**
     * 错误信息
     */
    public String errorString;

    /**
     * 业务数据对象
     */
    public DataBody dataBody;

    public LoginInfo() {
    }

    /**
     * 业务对象类
     * 
     * @author aisino
     */
    public class DataBody {

        /**
         * 用户信息
         */
        public UserMap userMap;

        /**
         * 选中的企业
         */
        public SelectedEtrMap selectedEtrMap;

        /**
         * 企业列表
         */
        public List<SelectedEtrMap> companyList;

        public DataBody() {
        }

        /**
         * 用户信息
         * 
         * @author aisino
         */
        public class UserMap {

            public String user_name;

            public String mobile;

            /**
             * 唯一标识
             */
            public String uuk;

            public String real_name;

            /**
             * 是否实名，0未实名，1实名
             */
            public String is_verified;

            public String email;

            public UserMap() {
            }
        }

    }

}
