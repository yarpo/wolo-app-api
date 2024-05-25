package pl.pjwstk.woloappapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.entities.FAQ;
import pl.pjwstk.woloappapi.model.translation.FAQTranslationResponse;
import pl.pjwstk.woloappapi.repository.FAQRepository;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FAQServiceTest {
    @Mock
    private FAQRepository faqRepository;

    @Mock
    private DictionariesMapper dictionariesMapper;

    @InjectMocks
    private FAQService faqService;

    @Test
    public void testGetAllFAQs() {
        var faq = new FAQ();
        when(faqRepository.findAll()).thenReturn(Collections.singletonList(faq));

        var faqs = faqService.getAllFAQs();

        assertEquals(1, faqs.size());
        assertEquals(faq, faqs.get(0));
    }

    @Test
    public void testGetFaqById() {
        FAQ faq = new FAQ();
        when(faqRepository.findById(1L)).thenReturn(Optional.of(faq));

        FAQ retrievedFaq = faqService.getFaqById(1L);

        assertEquals(faq, retrievedFaq);
    }

    @Test
    public void testGetFaqById_NotFound() {
        when(faqRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> faqService.getFaqById(1L));
    }

    @Test
    public void testCreateFAQ() {
        var translationResponse = new FAQTranslationResponse();
        when(dictionariesMapper.toFAQTranslation(translationResponse)).thenReturn(FAQ.builder());

        assertDoesNotThrow(() -> faqService.createFAQ(translationResponse));
        verify(faqRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateFAQ() {
        FAQ faq = new FAQ();
        faq.setId(1L);
        when(faqRepository.findById(1L)).thenReturn(Optional.of(faq));

        assertDoesNotThrow(() -> faqService.updateFAQ(faq));
        verify(faqRepository, times(1)).save(faq);
    }

    @Test
    public void testUpdateFAQ_NotFound() {
        FAQ faq = new FAQ();
        faq.setId(1L);
        when(faqRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> faqService.updateFAQ(faq));
        assertEquals("FAQ with ID1 does not exist", exception.getMessage());
        verify(faqRepository, never()).save(any());
    }

    @Test
    public void testDeleteFAQ() {
        assertDoesNotThrow(() -> faqService.deleteFAQ(1L));
        verify(faqRepository, times(1)).deleteById(1L);
    }
}
