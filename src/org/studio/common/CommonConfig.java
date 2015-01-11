package org.studio.common;

import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import org.studio.general.*;
import org.studio.utils.ReadPropertity;


public class CommonConfig extends JFinalConfig {
	
	/**
	 * 配置常量
	 */
	@Override
	public void configConstant(Constants me) {
	    boolean devMode = Boolean.parseBoolean(ReadPropertity.getProperty("devMode"));
		me.setDevMode(devMode);
	}
	
	/**
	 * 配置路由
	 */
	@Override
	public void configRoute(Routes me) {
		me.add("/n", IndexController.class);
		me.add("/n/article", articleController.class);
        me.add("/n/comment",commentController.class);
		me.add("/n/user", userController.class);
		me.add("/captcha", IdCodeController.class);
	}
	
	/**
	 * 配置插件
	 */
	@Override
	public void configPlugin(Plugins me) {
        /** 配置C3p0插件 */
        C3p0Plugin cp = new C3p0Plugin(ReadPropertity.getProperty("jdbcUrl"),
                ReadPropertity.getProperty("user"), ReadPropertity.getProperty("password"));
        me.add(cp);
        /** 配置ActiveRecord插件 */
        ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
        me.add(arp);
        /** DB映射*/
        arp.addMapping("userinfo","userId" ,userinfo.class);
        arp.addMapping("article" ,"artId", article.class);
        arp.addMapping("image"   , "imageId",image.class);
        arp.addMapping("comment" , "commentId",comment.class);
    }
	
	/**
	 * 配置全局拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * 配置处理器
	 */
	@Override
	public void configHandler(Handlers me) {
	}

	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("web", 8080, "/", 5);
	}
}
