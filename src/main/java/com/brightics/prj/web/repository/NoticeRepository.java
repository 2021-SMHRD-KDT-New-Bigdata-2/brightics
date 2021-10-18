package com.brightics.prj.web.repository;

import com.brightics.prj.web.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
