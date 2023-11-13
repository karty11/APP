package com.keystoneconstructs.credentia.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;

public class SwaggerConfiguration {

    @Bean
    public OpenAPI myOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail( "rishabh@keystoneconstructs.com" );
        contact.setName( "R Rishabh Keeshan" );

        License license = new License().name( "Apache 2.0" ).url( "https://www.apache.org/licenses/LICENSE-2.0.html" );

        Info info = new Info().title( "Credentia Backend API" ).version( "0.0" ).contact( contact )
                .description( "This API exposes endpoints to manage credentia services." ).license( license );

        return new OpenAPI().info( info )
                .addSecurityItem( new SecurityRequirement().addList( "Bearer Authentication" ) )
                .components( new Components().addSecuritySchemes( "Bearer Authentication", createAPIKeyScheme() ) );

    }


    private SecurityScheme createAPIKeyScheme() {

        return new SecurityScheme().type( SecurityScheme.Type.HTTP ).in( SecurityScheme.In.HEADER )
                .name( "credentia_auth" ).bearerFormat( "JWT" ).scheme( "bearer" );
    }

}
