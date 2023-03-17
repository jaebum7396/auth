package auth.repository;

import auth.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AuthenticationRepository extends JpaRepository<UserEntity,String> {
    Optional<UserEntity> findByUserId(String userId);
    Optional<UserEntity> findByUserNm(String userNm);
    List<UserEntity> findAll();
}