package self.test.service.impl;

import self.ibeans.anno.AutoSet;
import self.ibeans.anno.BeanType;
import self.ibeans.anno.Work;
import self.test.dao.AccountDao;
import self.test.dao.impl.JdbcAccountDaoImpl;
import self.test.pojo.Account;
import self.test.service.TransferService;

/**
 * @author 应癫
 */
@BeanType
public class TransferServiceImpl implements TransferService {

    @AutoSet
    private JdbcAccountDaoImpl accountDao;

    // 构造函数传值/set方法传值
    public void setAccountDao(JdbcAccountDaoImpl accountDao) {
        this.accountDao = accountDao;
    }

    @Work
    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
            Account from = accountDao.queryAccountByCardNo(fromCardNo);
            Account to = accountDao.queryAccountByCardNo(toCardNo);

            from.setMoney(from.getMoney()-money);
            to.setMoney(to.getMoney()+money);

            accountDao.updateAccountByCardNo(to);
            int c = 1/0;
            accountDao.updateAccountByCardNo(from);

    }
}
