package osiride.vitt_be.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;



@Configuration
public class SwaggerConfig implements WebMvcConfigurer{

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")	
                .bearerFormat("JWT");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("jwtToken");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("jwtToken", securityScheme))
                .addSecurityItem(securityRequirement);
    }
}
