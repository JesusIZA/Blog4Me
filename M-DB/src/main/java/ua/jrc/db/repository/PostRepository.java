package ua.jrc.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.jrc.db.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
}
