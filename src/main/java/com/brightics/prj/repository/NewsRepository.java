package com.brightics.prj.repository;

import com.brightics.prj.entity.Member;
import com.brightics.prj.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Long> {
}
