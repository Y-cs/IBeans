package self.test;

import self.ibeans.factory.ClassBeanFactory;
import self.ibeans.factory.transaction.CgLibProxyBeanFactory;
import self.ibeans.factory.transaction.ProxyFactory;
import self.ibeans.util.TransactionManager;
import self.test.service.impl.TransferServiceImpl;
import self.test.utils.TransactionManagerImpl;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Y-cs
 * @date 2021/4/21 18:41
 */
public class Application {

    public static void main(String[] args) throws Exception {
        try {
            //初始化工厂
            ClassBeanFactory classBeanFactory = new ClassBeanFactory(Application.class);
            //设置代理
            CgLibProxyBeanFactory instance = CgLibProxyBeanFactory.getInstance();
            classBeanFactory.setTransactionProxyFactory(instance);
            //启动扫描
            classBeanFactory.scan();
            //设置事务处理器
            TransactionManager transactionManagerImpl = (TransactionManager) classBeanFactory.getBean("transactionManagerImpl");
            instance.setTransactionManager(transactionManagerImpl);
            //执行业务逻辑
            TransferServiceImpl transferServiceImpl = (TransferServiceImpl) classBeanFactory.getBean("transferServiceImpl");
            transferServiceImpl.transfer("6029621011001", "6029621011000", 100);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
