package com.rsd.utils;


/***
 * 常量表
 *
 */
public class Const {

    public static final String CHARSET = "utf-8";


    public static final String TOKEN = "TOKEN";

    //管理端 sysId
    public static final int M_SYS_ID = 1;

    //医院端 sysId
    public static final int H_SYS_ID = 2;

    //企业端 sysId
    public static final int E_SYS_ID = 3;


    //登录账户
    public static final String SESSION_ACCOUNT = "session_account";

    //登录账户机构
    public static final String SESSION_ACCOUNT_ORG = "session_account_org";


    //登录账户角色
    public static final String SESSION_ACCOUNT_ROLE = "session_account_role";


    //登录账户资源
    public static final String SESSION_ACCOUNT_RES = "session_account_res";


    //服务专员
    public static final String SESSION_SERVICE_ACCOUNT = "session_service_account";


    //医院端所有授权code
    public static final String H_RES_ALL_CODE = "h_res_all_code";

    //企业端所有授权code
    public static final String E_RES_ALL_CODE = "e_res_all_code";

    /**
     * 文件分隔符
     */
    public static final String FILE_COMMON_SEPARATOR = "/";

    /**
     * 结构返回状态
     **/
    public static class ApiResult {

        /**
         * 成功
         */
        public static final Integer OK = 200;


        /**
         * 系统级异常
         */
        public static final Integer EXCEPTION = 500;

        /**
         * 业务异常
         */
        public static final Integer BUS_EXCEPTION = 510;

        /**
         * token 已经过期
         */
        public static final Integer TOKEN_PAST_DUE = 401;

        /**
         * NULL 空异常
         */
        public static final Integer NULL = 405;


        /**
         * 无访问权限illegal
         */
        public static final Integer ILLEGAL_ACCESS = 405;


        /**
         * 登录非法
         */
        public static final Integer LOGIN_ILLEGAL_ACCESS = 406;


        /**
         * 请求格式正确，但是由于含有语义错误，无法响应。
         */
        public static final Integer UNPROCESSABLE_ENTITY = 422;


        /**
         * 简化返回参数
         */
        public static final DataTransfer<Integer, String> success() {
            return new DataTransfer<>(OK, "success");
        }


        /**
         * 简化返回参数 成功
         *
         * @param data 提示语
         */
        public static final DataTransfer<Integer, String> success(String data) {
            return new DataTransfer<>(OK, data);
        }

        /**
         * 简化返回参数 异常
         */
        public static final DataTransfer<Integer, String> exception(String data) {
            return new DataTransfer<>(EXCEPTION, data);
        }

        /**
         * 空异常 异常
         */
        public static final DataTransfer<Integer, String> empty(String data) {
            return new DataTransfer<>(NULL, data);
        }


    }


    /**
     * ASC：升序（默认），DESC：降序。
     */
    public static class PageQuery {

        /**
         * 升序
         */
        public static final String ASC = "ASC";


        /**
         * 降序
         */
        public static final String DESC = "DESC";

        public static final String ORDER_BY = "ORDER BY";

        public static final String asc(String filed) {
            return filed.toUpperCase() + " " + ASC;
        }

        public static final String desc(String filed) {
            return filed.toUpperCase() + " " + DESC;
        }

    }

    /***
     * 判断是否全部为大写 或者小写
     * 全部为真 返回 true 否者 false
     */
    private static boolean isFiledLegalByAllUpperLower(String val) {

        val = val.trim();
        char c = val.charAt(0);

        boolean isLower = false;
        if (Character.isLowerCase(c)) {
            isLower = true;
        } else if (Character.isUpperCase(c)) {
        }


        for (int i = 0; i < val.length(); i++) {
            char c0 = val.charAt(i);
            if (c0 == ' ' || c0 == ',' || c0 == '_'|| c0 == '|' || c0 == '.') {
                continue;
            }

            if (isLower && !Character.isLowerCase(c0)) {
                return false;
            }

            if (!isLower && !Character.isUpperCase(c0)) {
                return false;
            }

        }


        return true;
    }


    /**
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：HelloWorld->HELLO_WORLD
     *
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String underscoreName(String name) {


        if (name == null || "".equals(name)) {
            return "";
        }

        //如果单词全部为大写或全部为小写跳过
        if (isFiledLegalByAllUpperLower(name)) {
            return name;
        }


        name = name.trim();
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (!"".equals(s.trim()) && s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }

                // 其他字符直接转成大写
                result.append(s.toUpperCase());

            }
        }
        return result.toString();
    }

    /**
     * 转换驼峰标记
     */
    private static final String MARK = "_";

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains(MARK)) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split(MARK);
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }
}
