package pl.pjwstk.woloappapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.entities.FAQ;
import pl.pjwstk.woloappapi.repository.FAQRepository;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FAQService {
    private final FAQRepository faqRepository;

    public List<FAQ> getAllFAQs() { return faqRepository.findAll(); }

    public FAQ getFaqById(Long id){
        return faqRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("FAQ id not found!"));
    }

    @Transactional
    public void createFAQ(FAQ faq) { faqRepository.save(faq); }

    @Transactional
    public void updateFAQ(FAQ faq){
        faqRepository
                .findById(faq.getId())
                .orElseThrow(() -> new IllegalArgumentException("FAQ with ID" + faq.getId() + " does not exist"));
        faqRepository.save(faq);
    }

    @Transactional
    public void deleteFAQ(Long id){
        faqRepository.deleteById(id);
    }
}
