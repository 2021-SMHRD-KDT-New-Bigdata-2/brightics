package com.brightics.prj.member.repository;

import com.brightics.prj.member.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
