package com.project.visit;

import com.project.visit.BaseTestIT.TestConfig;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@Import(TestConfig.class)
public abstract class BaseTestIT {

	@Configuration
	public static class TestConfig {

		@Bean
		@Primary
		public CacheManager inMemoryCacheManager() {
			return new NoOpCacheManager();
		}

	}

}
