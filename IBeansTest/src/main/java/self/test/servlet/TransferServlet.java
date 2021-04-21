package self.test.servlet;

import self.ibeans.anno.AutoSet;
import self.ibeans.anno.BeanType;
import self.ibeans.factory.ClassBeanFactory;
import self.ibeans.factory.transaction.CgLibProxyBeanFactory;
import self.ibeans.util.TransactionManager;
import self.test.Application;
import self.test.pojo.Result;
import self.test.service.TransferService;
import self.test.service.impl.TransferServiceImpl;
import self.test.utils.JsonUtils;
import self.test.utils.TransactionManagerImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author 应癫
 */
@WebServlet(name = "transferServlet", urlPatterns = "/transferServlet")
@BeanType
public class TransferServlet extends HttpServlet {

    @AutoSet
    private TransferServiceImpl transferService;

    @Override
    public void init() throws ServletException {
        try {
            ClassBeanFactory classBeanFactory = new ClassBeanFactory(Application.class);
            //设置代理
            CgLibProxyBeanFactory instance = CgLibProxyBeanFactory.getInstance();
            classBeanFactory.setTransactionProxyFactory(instance);
            classBeanFactory.scan();

            TransactionManager transactionManagerImpl = (TransactionManager) classBeanFactory.getBean("transactionManagerImpl");
            instance.setTransactionManager(transactionManagerImpl);

            transferService = (TransferServiceImpl) classBeanFactory.getBean("transferServiceImpl");
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 设置请求体的字符编码
        req.setCharacterEncoding("UTF-8");

        String fromCardNo = req.getParameter("fromCardNo");
        String toCardNo = req.getParameter("toCardNo");
        String moneyStr = req.getParameter("money");
        int money = Integer.parseInt(moneyStr);

        Result result = new Result();

        try {
            // 2. 调用service层方法
            transferService.transfer(fromCardNo, toCardNo, money);
            result.setStatus("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus("201");
            result.setMessage(e.toString());
        }

        // 响应
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(JsonUtils.object2Json(result));
    }
}
