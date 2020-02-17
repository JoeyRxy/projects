package mine.learn.vue.demo.repository;

import mine.learn.vue.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users, Integer> {
}
