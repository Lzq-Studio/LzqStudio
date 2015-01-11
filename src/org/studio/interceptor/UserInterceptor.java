package org.studio.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

/**
 * Created by lzim on 14-12-25.
 */
/**
 * 权限拦截器
 *
 * @author john
 * @date 2013-03-27
 */
public class UserInterceptor implements Interceptor {

    public void intercept(ActionInvocation ai) {
        Controller controller = ai.getController();
        String isLogin = controller.getSessionAttr("islogin");
        if (isLogin != null) {
            ai.invoke();
        }else {
            controller.renderJson("[{\"5\":\"error\"}]");
        }
    }
}
