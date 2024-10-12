package org.spring.data.rest;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.spring.data.rest.web.VoitureController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringDataRestApplicationTests {
    @Autowired
    VoitureController voitureController;
    @Test
    void contextLoads() {
        assertThat(voitureController).isNotNull();
    }

}
