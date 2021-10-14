package com.brightics.prj.web.repository;

import com.brightics.prj.web.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
