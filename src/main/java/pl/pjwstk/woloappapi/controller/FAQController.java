package pl.pjwstk.woloappapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import pl.pjwstk.woloappapi.model.FAQEditRequestDto;
import pl.pjwstk.woloappapi.model.FAQRequestDto;
import pl.pjwstk.woloappapi.model.FAQResponseDto;
import pl.pjwstk.woloappapi.model.translation.FAQTranslationRequest;
import pl.pjwstk.woloappapi.model.translation.FAQTranslationResponse;
import pl.pjwstk.woloappapi.service.FAQService;
import pl.pjwstk.woloappapi.service.UserService;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/faq")
@Tag(name = "FAQ")
public class FAQController {

    private final FAQService faqService;
    private final UserService userService;
    private final DictionariesMapper dictionariesMapper;

    @GetMapping()
    public ResponseEntity<List<FAQResponseDto>> getFAQs(){
        List<FAQResponseDto> faqDtos = faqService.getAllFAQs()
                .stream()
                .map(dictionariesMapper::toFAQDto)
                .collect(Collectors.toList());
        return new  ResponseEntity<>(faqDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FAQResponseDto> getFAQById(@PathVariable Long id){
        FAQResponseDto faqDto = dictionariesMapper.toFAQDto(faqService.getFaqById(id));
        return new ResponseEntity<>(faqDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addFAQ(@RequestBody FAQRequestDto faqRequestDto,
                                             @RequestParam String language){

        var translate = createTranslationDto(faqRequestDto, language);
        var localClient = WebClient.create("http://host.docker.internal:5000/");
        localClient.post()
                .uri("/faq/translate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(translate)
                .retrieve()
                .bodyToMono(FAQTranslationResponse.class)
                .subscribe(faqService::createFAQ);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<HttpStatus> editFAQ(
            @Valid @RequestBody FAQEditRequestDto faqEditRequestDto) {
        faqService.updateFAQ(dictionariesMapper.toFAQ(faqEditRequestDto).build());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteFAQ(@PathVariable Long id){
        faqService.deleteFAQ(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private FAQTranslationRequest createTranslationDto(FAQRequestDto faqRequestDto, String language){
        var translate = new FAQTranslationRequest();
        translate.setLanguage(language);
        translate.setQuestion(faqRequestDto.getQuestion());
        translate.setAnswer(faqRequestDto.getAnswer());
        return translate;
    }
}
