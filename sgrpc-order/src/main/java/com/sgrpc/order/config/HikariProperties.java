package com.sgrpc.order.config;
import com.zaxxer.hikari.HikariConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 数据库数据源配置
 **/
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class HikariProperties {

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    private String poolName;

    private int minIdle = 10;

    private int maximumPoolSize = 100;

    private int idleTimeout = 60000;

    private int connectionTimeout = 30000;

    public HikariConfig config() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        config.setPoolName(StringUtils.isBlank(poolName) ? UUID.randomUUID().toString() : poolName);
        //从池返回的连接的默认自动提交行为,默认值：true
        config.setMinimumIdle(minIdle);             //最小空闲
        config.setMaximumPoolSize(maximumPoolSize); //连接池最大连接数，默认是10
        // 空闲连接存活最大时间，默认600000（10分钟）
        config.setIdleTimeout(idleTimeout);
        //数据库连接超时时间,默认30秒，即30000
        config.setConnectionTimeout(connectionTimeout);
        config.setConnectionTestQuery("SELECT 1;");
        //https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");
        return config;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public int getIdleTimeout() {
        return idleTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public void setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}