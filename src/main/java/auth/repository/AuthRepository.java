package auth.repository;

import auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AuthRepository extends JpaRepository<User,String> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByUserNm(String userNm);
    List<User> findAll();
}