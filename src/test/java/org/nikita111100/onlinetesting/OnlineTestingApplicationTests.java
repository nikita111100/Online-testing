package org.nikita111100.onlinetesting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OnlineTestingApplicationTests {

	@Test
	void contextLoads() {
		Assertions.assertEquals(1,1);
	}

}
