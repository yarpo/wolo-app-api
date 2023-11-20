package pl.pjwstk.woloappapi.utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import pl.pjwstk.woloappapi.model.*;

import java.util.List;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);
    @Mappings({
            @Mapping(source = "startTime", target = "startTime"),
            @Mapping(source = "endTime", target = "endTime"),
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "capacity", target = "capacity"),
            @Mapping(source = "isLeaderRequired", target = "isLeaderRequired"),
            @Mapping(source = "requiredMinAge", target = "requiredMinAge")
    })
    Shift toShift(ShiftDto shiftDto);

    List<Shift> toShifts(List<ShiftDto> shiftDtos);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "isPeselVerificationRequired", target = "isPeselVerificationRequired")
    @Mapping(source = "isAgreementNeeded", target = "isAgreementNeeded")
    Event toEvent(DtoRequestEvent dtoRequestEvent);

    @Mapping(source = "street", target = "street")
    @Mapping(source = "homeNum", target = "homeNum")
    @Mapping(source = "addressDescription", target = "description")
    Address toAddress(DtoRequestEvent dtoRequestEvent);
}
