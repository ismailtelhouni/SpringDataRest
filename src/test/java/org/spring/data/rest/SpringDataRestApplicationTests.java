package org.spring.data.rest;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.spring.data.rest.config.TestConfig;
import org.spring.data.rest.web.VoitureController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {SpringDataRestApplication.class, TestConfig.class})
@ActiveProfiles("test")
class SpringDataRestApplicationTests {
    @Autowired
    VoitureController voitureController;
    @Test
    void contextLoads() {
        assertThat(voitureController).isNotNull();
    }

}
