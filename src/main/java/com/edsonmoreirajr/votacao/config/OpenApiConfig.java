package com.edsonmoreirajr.votacao.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI sicrediVotacaoOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Sicredi Votacao API")
                        .description("Aplicação para votação de pautas por associados.")
                        .version("v1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Sicredi Votacao Documentação")
                        .url("https://github.com/edsonmoreirajr/desafio_sicredi_backend/wiki"));
    }
}
