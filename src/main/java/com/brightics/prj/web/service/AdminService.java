package com.brightics.prj.web.service;


import com.brightics.prj.web.entity.Notice;
import com.brightics.prj.web.entity.Stock;
import com.brightics.prj.web.form.NoticeForm;
import com.brightics.prj.web.form.StockForm;
import com.brightics.prj.web.repository.NoticeRepository;
import com.brightics.prj.web.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final StockRepository stockRepository;
    private final NoticeRepository noticeRepository;


    public Stock addStock(@ModelAttribute StockForm stockForm) {
        Stock stock = new Stock();
        stock.setCandidate(stockForm.getCandidate());
        stock.setCode(stockForm.getStock_code());
        stock.setCategory(stockForm.getCategory());
        stock.setDescription(stockForm.getDescription());
        stock.setName(stockForm.getName());
        return stockRepository.save(stock);
    }


    public Notice addNotice(@ModelAttribute NoticeForm noticeForm) {

        Notice notice = new Notice();
        notice.setTitle(noticeForm.getTitle());
        notice.setContent(noticeForm.getContent());
        notice.setNoticedAt(LocalDateTime.now());

        return noticeRepository.save(notice);
    }


}

