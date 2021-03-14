package com.sgrpc.order.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.util.Utils;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Configuration
public class DruidConfig {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        //初始连接数
        druidDataSource.setInitialSize(5);
        //最小连接池数量
        druidDataSource.setMinIdle(10);
        // 最大连接池数量
        druidDataSource.setMaxActive(20);
        //配置获取连接等待超时的时间
        druidDataSource.setMaxWait(60000);
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        //配置一个连接在池中最小生存的时间，单位是毫秒
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        //配置一个连接在池中最大生存的时间，单位是毫秒
        druidDataSource.setMaxEvictableIdleTimeMillis(900000);
        //配置检测连接是否有效
        druidDataSource.setValidationQuery("SELECT 'x'");
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setTestWhileIdle(true);
        List<Filter> filters = new ArrayList<>();
        filters.add(wallFilter());
        filters.add(statFilter());
        druidDataSource.setProxyFilters(filters);
        return druidDataSource;
    }

    @Bean
    public WallFilter wallFilter() {
        WallFilter filter = new WallFilter();
        WallConfig wallConfig = new WallConfig();
        wallConfig.setMultiStatementAllow(true);//是否允许一次执行多条语句
        filter.setConfig(wallConfig);
        return filter;
    }

    @Bean
    public StatFilter statFilter() {
        StatFilter filter = new StatFilter();
        filter.setSlowSqlMillis(1000); // 慢查询sql的阈值1秒
        filter.setLogSlowSql(true); //慢查询sql
        filter.setMergeSql(true);  //合并sql
        return filter;
    }

    /**
     * 配置druid管理页面的访问控制
     * 访问网址: http://127.0.0.1:8080/druid
     */
    @Bean
    public ServletRegistrationBean<Servlet> druidServlet() {
        ServletRegistrationBean<Servlet> servletRegistrationBean = new ServletRegistrationBean<>();
        servletRegistrationBean.setServlet(new StatViewServlet());  //配置一个拦截器
        servletRegistrationBean.addUrlMappings("/druid/*");    //指定拦截器只拦截druid管理页面的请求
        HashMap<String, String> initParam = new HashMap<>();
        initParam.put("loginUsername", "admin");    //登录druid管理页面的用户名
        initParam.put("loginPassword", "admin");    //登录druid管理页面的密码
        initParam.put("resetEnable", "true");       //是否允许重置druid的统计信息
        initParam.put("allow", "");         //ip白名单，如果没有设置或为空，则表示允许所有访问
        servletRegistrationBean.setInitParameters(initParam);
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<javax.servlet.Filter> removeDruidAdFilterRegistrationBean() {
        String commonJsPattern = "/druid/*".replaceAll("\\*", "js/common.js");
        final String filePath = "support/http/resources/js/common.js";
        //创建filter进行过滤
        javax.servlet.Filter filter = new javax.servlet.Filter() {
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {
                // Do nothing
            }

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                chain.doFilter(request, response);
                // 重置缓冲区，响应头不会被重置
                response.resetBuffer();
                // 获取common.js
                String text = Utils.readFromResource(filePath);
                // 正则替换banner, 除去底部的广告信息
                text = text.replaceAll("<a.*?banner\"></a><br/>", "");
                text = text.replaceAll("powered.*?shrek.wang</a>", "");
                response.getWriter().write(text);
            }

            @Override
            public void destroy() {
                // Do nothing
            }
        };
        FilterRegistrationBean<javax.servlet.Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns(commonJsPattern);
        return registrationBean;
    }
}
