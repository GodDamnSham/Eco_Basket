package de.hsos.swa;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(
    info = @Info(
        title = "ecobasket API",
        version = "1.0.0",
        contact = @Contact(
                name = "Malik Shameer Riaz & Finn Chritophersen"
        )
    )
)

@ApplicationPath("/ecobasket")
public class AnnotationDoc extends Application {
    
}
