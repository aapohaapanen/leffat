package fi.aapohaapanen.leffat;

import fi.aapohaapanen.leffat.jpa.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
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

	private final String movieJson = """
			{"name":"Avengers: Endgame",
			"year":2018,
			"genres":["Adventure","Sci-fi"],
			"ageLimit":12,
			"rating":4,
			"actors":[
			  {"firstName":"Robert","lastName":" Downey Jr."},
			  {"firstName":"Chris","lastName":"Evans"},
			  {"firstName":"Scarlett","lastName":"Johansson"}
			],
			"director":{"firstName":"Anthony","lastName":"Russo"},
			"synopsis":"Restore balance to the universe."}""";

	@Test
	public void saveMovie() {
		var request = RequestEntity.post(url("/addMovie"))
				.contentType(MediaType.APPLICATION_JSON)
				.body(movieJson);
		var result = rest.exchange(request, Movie.class);

		assertEquals(200, result.getStatusCodeValue());
		var movie = result.getBody();
		assertNotNull(movie);
		assertEquals("Avengers: Endgame", result.getBody().getName());
	}

	@Test void addAndDeleteMovie() {
		var request = RequestEntity.post(url("/addMovie"))
				.contentType(MediaType.APPLICATION_JSON)
				.body(movieJson);
		var result = rest.exchange(request, Movie.class);
		var movie = result.getBody();
		assertNotNull(movie);
		var id = result.getBody().getId();

		assertTrue(moviesContains(id));

		var deleteUrl = url("/delete/" + id.toString());
		rest.delete(deleteUrl);
		assertFalse(moviesContains(id));
	}

	private boolean moviesContains(UUID id) {
		var allMovies = rest.getForObject(url("/allMovies"), Movie[].class);
		return Stream.of(allMovies).anyMatch(m -> m.getId().equals(id));
	}

	private String url(String path) {
		return "http://localhost:" + port + path;
	}
}
