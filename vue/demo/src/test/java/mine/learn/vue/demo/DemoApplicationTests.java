package mine.learn.vue.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import mine.learn.vue.demo.entity.Users;
import mine.learn.vue.demo.repository.UserRepo;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private UserRepo repo;

	@Test
	void contextLoads() {
	}

	@Test
	void findAll() {
		List<Users> all = repo.findAll();
		for (Users user : all) {
			System.out.println(user);
		}
	}

}
