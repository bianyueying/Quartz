package bml.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author 边月白
 * Date 2020/11/3 20:29
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "db1")
    @Primary
    @ConfigurationProperties("spring.datasource.db1")
    public DruidDataSource dataSource1 () {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "db2")
    @ConfigurationProperties("spring.datasource.db2")
    @QuartzDataSource
    public DruidDataSource dataSource2 () {
        return DruidDataSourceBuilder.create().build();
    }
}
