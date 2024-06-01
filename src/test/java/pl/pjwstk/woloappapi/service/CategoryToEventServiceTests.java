package pl.pjwstk.woloappapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.entities.Category;
import pl.pjwstk.woloappapi.model.entities.CategoryToEvent;
import pl.pjwstk.woloappapi.model.entities.Event;
import pl.pjwstk.woloappapi.repository.CategoryToEventRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CategoryToEventServiceTests {

    @Mock
    private CategoryToEventRepository categoryToEventRepository;

    @InjectMocks
    private CategoryToEventService categoryToEventService;

    @Test
    public void testCreateCategoryToEvent(){
        CategoryToEvent categoryToEvent = new CategoryToEvent();
        categoryToEvent.setId(1L);

        Category category = new Category();
        category.setId(1L);
        categoryToEvent.setCategory(category);

        Event event = new Event();
        event.setId(1L);
        categoryToEvent.setEvent(event);

        categoryToEventService.createCategoryToEvent(categoryToEvent);

        ArgumentCaptor<CategoryToEvent> categoryToEventCaptor = ArgumentCaptor.forClass(CategoryToEvent.class);
        verify(categoryToEventRepository).save(categoryToEventCaptor.capture());
        CategoryToEvent capturedCategoryToEvent = categoryToEventCaptor.getValue();
        assertEquals(1L, capturedCategoryToEvent.getId());
        assertEquals(1L, capturedCategoryToEvent.getCategory().getId());
        assertEquals(1L, capturedCategoryToEvent.getEvent().getId());

    }

    @Test
    public void testDeleteCategoryToEvent() {
        Long categoryToEventId = 1L;

        categoryToEventService.deleteCategoryToEvent(categoryToEventId);

        verify(categoryToEventRepository, times(1)).deleteById(categoryToEventId);
    }
}
