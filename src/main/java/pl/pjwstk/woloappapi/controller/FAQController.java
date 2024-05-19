package pl.pjwstk.woloappapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import pl.pjwstk.woloappapi.model.FAQDto;
import pl.pjwstk.woloappapi.model.FAQResponseDto;
import pl.pjwstk.woloappapi.model.translation.FAQTranslationRequest;
import pl.pjwstk.woloappapi.model.translation.FAQTranslationResponse;
import pl.pjwstk.woloappapi.service.FAQService;
import pl.pjwstk.woloappapi.utils.DictionariesMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/faq")
@Tag(name = "FAQ")
public class FAQController {

    private final FAQService faqService;
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
    public ResponseEntity<HttpStatus> addFAQ(@RequestBody FAQDto faqDto,
                                             @RequestParam String language){

        var translate = createTranslationDto(faqDto, language);
        var localClient = WebClient.create("http://host.docker.internal:5000/");
        localClient.post()
                .uri("/faq/translate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(translate)
                .retrieve()
                .bodyToMono(FAQTranslationResponse.class)
                .subscribe(translated -> faqService.createFAQ(faqDto, translated));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<HttpStatus> editFAQ(
            @Valid @RequestBody FAQDto faqDto) {
        //faqService.updateFAQ(dictionariesMapper.toFAQ(faqDto).build());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteFAQ(@PathVariable Long id){
        faqService.deleteFAQ(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private FAQTranslationRequest createTranslationDto(FAQDto faqDto, String language){
        var translate = new FAQTranslationRequest();
        translate.setLanguage(language);
        translate.setQuestion(faqDto.getQuestion());
        translate.setAnswer(faqDto.getAnswer());
        return translate;
    }
}
