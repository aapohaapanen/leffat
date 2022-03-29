package fi.aapohaapanen.leffat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LeffatApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private LeffatRestController controller;

	@Autowired
	private TestRestTemplate rest;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	public void pingReturnsPong() {
		assertThat(rest.getForObject(url("/ping"), String.class))
				.contains("pong");
	}

	private String url(String path) {
		return "http://localhost:" + port + path;
	}
}
