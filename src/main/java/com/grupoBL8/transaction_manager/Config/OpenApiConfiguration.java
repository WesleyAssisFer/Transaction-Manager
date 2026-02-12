package com.grupoBL8.transaction_manager.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI apiInfo(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Desafio tecnico")
                                .description("API feita para um desafio tecnico do ITAU")
                );
    }
}