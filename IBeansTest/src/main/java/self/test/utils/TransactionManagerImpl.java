package self.test.utils;

import self.ibeans.anno.AutoSet;
import self.ibeans.anno.BeanType;

import java.sql.SQLException;

/**
 * @author 应癫
 * <p>
 * 事务管理器类：负责手动事务的开启、提交、回滚
 */
@BeanType
public class TransactionManagerImpl implements self.ibeans.util.TransactionManager {

    @AutoSet
    private ConnectionUtils connectionUtils;

    /*private TransactionManager(){

    }

    private static TransactionManager transactionManager = new TransactionManager();

    public static TransactionManager getInstance() {
        return  transactionManager;
    }*/


    // 开启手动事务控制
    @Override
    public void beginTransaction() throws SQLException {
        connectionUtils.getCurrentThreadConn().setAutoCommit(false);
    }


    // 提交事务
    @Override
    public void commit() throws SQLException {
        connectionUtils.getCurrentThreadConn().commit();
    }


    // 回滚事务
    @Override
    public void rollback() throws SQLException {
        connectionUtils.getCurrentThreadConn().rollback();
    }
}
