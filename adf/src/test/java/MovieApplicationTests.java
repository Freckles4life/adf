import com.movie_list.MovieApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = MovieApplication.class)
@ActiveProfiles ("test")
@ConfigurationProperties(prefix = "test.properties")
class MovieApplicationTests {

	@Test
	void contextLoads() {
	}

}
