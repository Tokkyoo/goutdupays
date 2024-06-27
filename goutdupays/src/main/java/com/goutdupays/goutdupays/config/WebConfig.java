import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        Dotenv dotenv = Dotenv.load();
        String corsUrl;

        String env = System.getenv("ENV");
        if ("PROD".equalsIgnoreCase(env)) {
            corsUrl = dotenv.get("CORS_URL_PROD");
        } else {
            corsUrl = dotenv.get("CORS_URL_LOCAL");
        }

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(corsUrl);
            }
        };
    }
}
