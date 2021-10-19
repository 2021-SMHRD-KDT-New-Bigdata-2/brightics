package com.brightics.prj.web.repository;

import com.brightics.prj.web.entity.Comment;
import com.brightics.prj.web.entity.Member;
import com.brightics.prj.web.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    @EntityGraph(attributePaths = {"member", "stock"})
    Page<Comment> findCommentByStockIs(Stock stock, Pageable pageable);

    @EntityGraph(attributePaths = {"member", "stock"})
    Page<Comment> findCommentByMemberIs(Member member, Pageable pageable);


}
