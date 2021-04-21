package self.test.utils;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author 应癫
 */
public class DruidUtils {

    private DruidUtils() {
    }

    private static DruidDataSource druidDataSource = new DruidDataSource();


    static {
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://118.24.126.49:3306/test");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("ycs@1996515");

    }

    public static DruidDataSource getInstance() {
        return druidDataSource;
    }

}
