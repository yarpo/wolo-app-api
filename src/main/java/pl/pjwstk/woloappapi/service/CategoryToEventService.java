package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.CategoryToEvent;
import pl.pjwstk.woloappapi.repository.CategoryToEventRepository;

@Service
@AllArgsConstructor
public class CategoryToEventService {
    private final CategoryToEventRepository categoryToEventRepository;

    public void createCategoryToEvent(CategoryToEvent categoryToEvent) {
        categoryToEventRepository.save(categoryToEvent);
    }
}
