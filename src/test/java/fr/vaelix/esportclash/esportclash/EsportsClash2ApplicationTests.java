package fr.vaelix.esportclash.esportclash;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(PostgresSQLTestConfiguration.class)
@SpringBootTest
class EsportsClash2ApplicationTests {

    @Test
    void contextLoads() {
    }

}
