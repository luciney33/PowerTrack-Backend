package com.powertrack.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import com.powertrack.backend.common.Constantes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(Constantes.SWAGGER_API_TITLE)
                        .version(Constantes.SWAGGER_API_VERSION)
                        .description(Constantes.SWAGGER_API_DESCRIPTION)
                        .contact(new Contact()
                                .name(Constantes.SWAGGER_CONTACT_NAME)));
    }
}
