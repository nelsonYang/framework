package com.framework.servlet;

import com.framework.context.ApplicationContext;
import com.framework.context.InvocationContext;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.service.api.WorkHandler;
import com.frameworkLog.factory.LogFactory;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;


/**
 *
 * @author Administrator
 */
@WebServlet(name = "ServiceServlet", urlPatterns = {"/ServiceServlet"})
public class ServiceServlet extends HttpServlet {

    private final Logger logger = LogFactory.getInstance().getLogger(ServiceServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json;charset=utf-8");
        resp.addHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        resp.setHeader("Access-Control-Allow-Credentials", "true") ;
        resp.addHeader("P3P", "CP=CAO PSA OUR"); 
        String act = req.getParameter("act");//调用的方法
        logger.debug("act=".concat(act));
        ApplicationContext applicationContext = ApplicationContext.CTX;
        InvocationContext invocationContext = new InvocationContext(req, resp);
        invocationContext.setAct(act);
        applicationContext.getThreadLocalManager().openThreadLocal(invocationContext);
        WorkHandler service = applicationContext.getService(act);
        if (service != null) {
            service.execute();
        } else {
            applicationContext.getResponseWriter(ResponseTypeEnum.MAP_DATA_JSON).toWriteServiceErrorMsg();
        }
        applicationContext.getThreadLocalManager().closeThreadLocal();

    }
}
