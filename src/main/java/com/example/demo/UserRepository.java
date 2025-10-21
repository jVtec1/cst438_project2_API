package com.example.demo;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    User findByUsername(String username);
}
