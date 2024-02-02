package pl.pjwstk.woloappapi.utils;

import org.springframework.stereotype.Component;
import pl.pjwstk.woloappapi.model.Address;
import pl.pjwstk.woloappapi.model.Event;
import pl.pjwstk.woloappapi.model.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class Translator {
    private static final Map<Language, Function<Event, String>> nameExtractorMap = Map.of(
            Language.PL, Event::getNamePL,
            Language.EN, Event::getNameEN,
            Language.UA, Event::getNameUA,
            Language.RU, Event::getNameRU
    );

    private static final Map<Language, Function<Event, String>> descriptionExtractorMap = Map.of(
            Language.PL, Event::getDescriptionPL,
            Language.EN, Event::getDescriptionEN,
            Language.UA, Event::getDescriptionUA,
            Language.RU, Event::getDescriptionRU
    );

    private static final Map<Language, Function<Address, String>> addressDescriptionExtractorMap = Map.of(
            Language.PL, Address::getAddressDescriptionPL,
            Language.EN, Address::getAddressDescriptionEN,
            Language.UA, Address::getAddressDescriptionUA,
            Language.RU, Address::getAddressDescriptionRU
    );

    public List<String> translate(String language, Event event) {
        List<String> translations = new ArrayList<>();
        Address address = event.getAddressToEvents().get(0).getAddress();
        translations.add(nameExtractorMap.get(languageToEnum(language)).apply(event));
        translations.add(descriptionExtractorMap.get(languageToEnum(language)).apply(event));
        translations.add(addressDescriptionExtractorMap.get(languageToEnum(language)).apply(address));
        return translations;
    }

    private Language languageToEnum(String language) {
        return switch (language) {
            case "pl" -> Language.PL;
            case "en" -> Language.EN;
            case "ua" -> Language.UA;
            case "ru" -> Language.RU;
            default -> null;
        };
    }
}
