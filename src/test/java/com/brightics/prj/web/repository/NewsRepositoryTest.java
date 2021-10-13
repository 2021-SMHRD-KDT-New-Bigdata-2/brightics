package com.brightics.prj.web.repository;

import com.brightics.prj.web.entity.Candidate;
import com.brightics.prj.web.entity.News;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
class NewsRepositoryTest {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    @Test
    void findCountNumberOfNewsPerPeriod() {

        Candidate candidate = candidateRepository.getById(1L);
        System.out.println("candidate.getName() = " + candidate.getName());
        for (int i = 0; i < 100; i++) {
            News news = new News();
            news.setCandidate(candidate);
            news.setDate(LocalDateTime.now().minusDays(i));
            newsRepository.save(news);
        }

        List<Object[]> result= newsRepository.findCountNumberOfNewsPerPeriod(LocalDateTime.now(),7L);

        for (Object[] objects : result) {
            System.out.println("objects[0] = " + objects[0]);
            System.out.println("objects[1] = " + objects[1]);
        }

        BigInteger candidate_id=new BigInteger("1");
        List<Object[]> result2= newsRepository.findCountNumberOfNewsPerPeriodAndCandidateIs(LocalDate.now(),7L, candidate.getId());

        for (Object[] objects : result2) {
            System.out.println("objects[0]2 = " + objects[0]);
            System.out.println("objects[1]2 = " + objects[1]);
        }


//        날짜별 count 쿼리 잘 날아오나 테스트




    }

    @Test
    void time(){
        System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
    }


}