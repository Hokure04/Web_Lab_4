package web.org.webb_project_4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.org.webb_project_4.model.Points;

import java.util.List;

public interface PointRepository extends JpaRepository<Points, Long> {
    List<Points> findAllByUserId(Long userId);
    void deletePointByUserId(Long userId);
}
