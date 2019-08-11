package curso.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableTransactionManagement // gerencia transações no banco de dados
@ComponentScan("curso") // escanear tudo o que estiver dentro do package curso
public class SpringRootConfig {
}
