package com.veli.vshop.seckill.config;

import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangwei
 * @date 2021-02-15 21:58
 */
@Configuration
public class WebServerConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        // 获取tomcat连接器
        ((TomcatServletWebServerFactory) factory).addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            // 获取protocol
            Http11NioProtocol protocolHandler = (Http11NioProtocol) connector.getProtocolHandler();
            // 如果keepalive连接30s，还没有人使用，释放此链接
            protocolHandler.setKeepAliveTimeout(30000);
            // 允许开启最大长连接数量
            protocolHandler.setMaxKeepAliveRequests(10000);
        });
    }
}
