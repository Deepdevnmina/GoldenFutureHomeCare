/*package com.homecare.GoldenFutureHomeCare.shared;
/*
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/*@ExtendWith(SpringExtension.class)
class consumerUtilsTest {

	@Autowired
	ConsumerUtils consumerUtils;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	final void testGenerateConsumerId() {
		String consumerId = consumerUtils.generateConsumerId(30);
		String consumerId2 = consumerUtils.generateConsumerId(30);

		assertNotNull(consumerId);
		assertNotNull(consumerId2);

		assertTrue(consumerId.length() == 30);
		assertTrue(!consumerId.equalsIgnoreCase(consumerId2));
	}

	@Test
	final void testHasTokenNotExpired() {// change to not in future.
		String token = consumerUtils.generateEmailVerificationToken("fkfuruk56783nvjfb");
		assertNotNull(token);

		boolean hasTokenExpired = ConsumerUtils.hasTokenExpired(token);
		assertFalse(hasTokenExpired);
	}

	@Test
	final void testHasTokenExpired() {
		String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MUB0ZXN0LmNvbSIsImV4cCI6MTUzMjc3Nzc3NX0.cdudUo3pwZLN9UiTuXiT7itpaQs6BgUPU0yWbNcz56-l1Z0476N3H_qSEHXQI5lUfaK2ePtTWJfROmf0213UJA";
		boolean hasTokenExpired = ConsumerUtils.hasTokenExpired(expiredToken);

		assertTrue(hasTokenExpired);
	}
}*/
