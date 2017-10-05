package ua.jrc.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.jrc.db.entity.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, String> {
}
