package pl.pjwstk.woloappapi.utils;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.EventEditRequestDto;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.repository.EventRepository;
import pl.pjwstk.woloappapi.repository.ShiftToUserRepository;
import pl.pjwstk.woloappapi.service.CategoryService;
import pl.pjwstk.woloappapi.service.DistrictService;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class EventUpdater {
    private final EventRepository eventRepository;
    private final DistrictService districtService;
    private final EmailUtil emailUtil;
    private final CategoryService categoryService;
    private final EventMapper eventMapper;
    private final ShiftToUserRepository shiftToUserRepository;

    @Transactional
    public void update(EventEditRequestDto eventDto, Long id, Boolean sendMail) {
        var city = districtService.getDistrictById(eventDto.getShifts().get(0).getDistrictId()).getCity();
        Event event = eventRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Event id not found!"));
        event.setNamePL(eventDto.getNamePL());
        event.setNameEN(eventDto.getNameEN());
        event.setNameUA(eventDto.getNameUA());
        event.setNameRU(eventDto.getNameRU());
        event.setDescriptionPL(eventDto.getDescriptionPL());
        event.setDescriptionEN(eventDto.getDescriptionEN());
        event.setDescriptionUA(eventDto.getDescriptionUA());
        event.setDescriptionRU(eventDto.getDescriptionRU());
        event.setDate(eventDto.getDate());
        event.setPeselVerificationRequired(eventDto.isPeselVerificationRequired());
        event.setAgreementNeeded(eventDto.isAgreementNeeded());
        event.setImageUrl(eventDto.getImageUrl());
        event.setCity(city);

        if (event.isPeselVerificationRequired() != eventDto.isPeselVerificationRequired() &&
                eventDto.isPeselVerificationRequired()) {
            sendEmailsForPeselVerificationRequired(event);
            deleteShiftToUsersForPeselVerification(event);
        }

        if (event.isAgreementNeeded() != eventDto.isAgreementNeeded() &&
                eventDto.isAgreementNeeded()) {
            sendEmailsForAgreementNeeded(event);
            deleteShiftToUsersForAgreementNeeded(event);
        }

        if (areEventFieldsChanged(event, eventDto) && sendMail) {
            event.getShifts().stream()
                    .flatMap(shift -> shift.getShiftToUsers().stream())
                    .map(ShiftToUser::getUser)
                    .map(User::getEmail)
                    .distinct()
                    .forEach(email -> {
                        try {
                            emailUtil.sendEditEventMail(email, event.getId());
                        } catch (MessagingException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
        }

        updateEventCategories(event, eventDto);
        updateEventShifts(event, eventDto, sendMail);

        eventRepository.save(event);
    }


    private void sendEmailsForPeselVerificationRequired(Event event) {
        event.getShifts().stream()
                .flatMap(shift -> shift.getShiftToUsers().stream())
                .map(ShiftToUser::getUser)
                .filter(user -> !user.isPeselVerified())
                .map(User::getEmail)
                .distinct()
                .forEach(email -> {
                    try {
                        emailUtil.sendPeselVerificationEmail(email, event.getId());
                    } catch (MessagingException ex) {
                        throw new RuntimeException(ex);
                    }
                });
    }

    private void sendEmailsForAgreementNeeded(Event event) {
        event.getShifts().stream()
                .flatMap(shift -> shift.getShiftToUsers().stream())
                .map(ShiftToUser::getUser)
                .filter(user -> !user.isPeselVerified())
                .map(User::getEmail)
                .distinct()
                .forEach(email -> {
                    try {
                        emailUtil.sendAgreementNeededEmail(email, event.getId());
                    } catch (MessagingException ex) {
                        throw new RuntimeException(ex);
                    }
                });
    }

    private void deleteShiftToUsersForPeselVerification(Event event) {
        List<Shift> shifts = event.getShifts();
        shifts.forEach(s -> {
            var shiftToUsersToRemove = s.getShiftToUsers().stream()
                    .filter(uts -> !uts.getUser().isPeselVerified())
                    .toList();
            shiftToUsersToRemove.forEach(stu -> {
                s.getShiftToUsers().remove(stu);
                s.setRegisteredUsers(s.getRegisteredUsers() - 1);
            });
        });
    }

    private void deleteShiftToUsersForAgreementNeeded(Event event) {
        List<Shift> shifts = event.getShifts();
        shifts.forEach(s -> {
            var shiftToUsersToRemove = s.getShiftToUsers().stream()
                    .filter(uts -> !uts.getUser().isAgreementSigned())
                    .toList();
            shiftToUsersToRemove.forEach(stu -> {
                s.getShiftToUsers().remove(stu);
                s.setRegisteredUsers(s.getRegisteredUsers() - 1);
            });

        });
    }

    private boolean areEventFieldsChanged(Event persistedEvent, EventEditRequestDto eventDto) {
        return !Objects.equals(persistedEvent.getNamePL(), eventDto.getNamePL()) ||
                !Objects.equals(persistedEvent.getNameEN(), eventDto.getNameEN()) ||
                !Objects.equals(persistedEvent.getNameUA(), eventDto.getNameUA()) ||
                !Objects.equals(persistedEvent.getNameRU(), eventDto.getNameRU()) ||
                !Objects.equals(persistedEvent.getDescriptionPL(), eventDto.getDescriptionPL()) ||
                !Objects.equals(persistedEvent.getDescriptionEN(), eventDto.getDescriptionEN()) ||
                !Objects.equals(persistedEvent.getDescriptionUA(), eventDto.getDescriptionUA()) ||
                !Objects.equals(persistedEvent.getDescriptionRU(), eventDto.getDescriptionRU()) ||
                !Objects.equals(persistedEvent.getDate(), eventDto.getDate());
    }

    private void updateEventCategories(Event event, EventEditRequestDto eventDto) {
        event.getCategories().removeIf(cte ->!eventDto.getCategories()
                .contains(cte.getCategory().getId()));

        eventDto.getCategories()
                .stream()
                .filter(categoryId ->event.getCategories().stream()
                        .noneMatch(categoryToEvent ->
                                categoryId.equals(categoryToEvent
                                        .getCategory()
                                        .getId())))
                .map(categoryService::getCategoryById)
                .map(category -> {
                    CategoryToEvent newCategoryToEvent = new CategoryToEvent();
                    newCategoryToEvent.setCategory(category);
                    newCategoryToEvent.setEvent(event);
                    return newCategoryToEvent;
                })
                .forEach(event.getCategories()::add);
    }

    private void updateEventShifts(Event event, EventEditRequestDto eventDto, Boolean sendMail) {
        List<Shift> newShifts = eventDto.getShifts().stream()
                .map(s -> eventMapper.toShift(s)
                        .event(event)
                        .build())
                .toList();

        List<Long> newShiftIds = newShifts.stream().map(Shift::getId).toList();

        removeObsoleteShifts(event, newShiftIds);
        updateExistingShifts(event, newShifts, sendMail);
        addNewShifts(event, newShifts);
    }

    private void removeObsoleteShifts(Event event, List<Long> newShiftIds) {
        event.getShifts().stream()
                .filter(existingShift -> !newShiftIds.contains(existingShift.getId()))
                .flatMap(existingShift -> existingShift.getShiftToUsers().stream())
                .forEach(shiftToUser -> {
                    try {
                        emailUtil.sendDeleteEventMessage(shiftToUser.getUser().getEmail(), event.getId());
                    } catch (MessagingException ex) {
                        throw new RuntimeException(ex);
                    }
                    shiftToUserRepository.delete(shiftToUser);
                });

        event.getShifts().removeIf(existingShift -> !newShiftIds.contains(existingShift.getId()));
    }

    private void updateExistingShifts(Event event, List<Shift> newShifts, Boolean sendMail) {
        newShifts.forEach(ns -> {
            if (ns.getId() == null) return;

            event.getShifts().stream()
                    .filter(s -> s.getId().equals(ns.getId()))
                    .findFirst()
                    .ifPresent(existingShift -> updateShiftFields(existingShift, ns, sendMail));
        });
    }

    private void updateShiftFields(Shift shift, Shift newShift, Boolean sendMail) {

        shift.setStartTime(newShift.getStartTime());
        shift.setEndTime(newShift.getEndTime());
        shift.setLeaderRequired(newShift.isLeaderRequired());
        updateCapacity(shift, newShift.getCapacity());
        updateMinAge(shift, newShift.getRequiredMinAge());
        shift.setShiftDirectionsPL(newShift.getShiftDirectionsPL());
        shift.setShiftDirectionsEN(newShift.getShiftDirectionsEN());
        shift.setShiftDirectionsUA(newShift.getShiftDirectionsUA());
        shift.setShiftDirectionsRU(newShift.getShiftDirectionsRU());
        updateShiftAddress(shift, newShift);

        if (areShiftFieldsChanged(shift, newShift) && sendMail) {
            shift.getShiftToUsers().forEach(stu -> {
                try {
                    emailUtil.sendEditEventMail(stu.getUser().getEmail(), shift.getEvent().getId());
                } catch (MessagingException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    private void updateMinAge(Shift shift, int requiredMinAge) {
        if(shift.getRequiredMinAge() < requiredMinAge && requiredMinAge >= 18) {
            var usersToRemove = shift.getShiftToUsers().stream()
                    .filter(stu -> !stu.getUser().isAdult())
                    .toList();

            usersToRemove.forEach(stu -> {
                try {
                    emailUtil.sendMinAgeMail(stu.getUser().getEmail(), shift.getEvent().getId());
                } catch (MessagingException ex) {
                    throw new RuntimeException(ex);
                }
                shift.setRegisteredUsers(shift.getRegisteredUsers() - 1);
            });

            shift.getShiftToUsers().removeAll(usersToRemove);
        }
        shift.setRequiredMinAge(requiredMinAge);
    }

    private void updateCapacity(Shift shift, int capacity) {
        if(shift.getCapacity() > capacity) {
            var quantity = shift.getCapacity() - capacity;
            var shiftToUsersToRemove = shift.getShiftToUsers()
                    .subList(shift.getShiftToUsers().size() - quantity, shift.getShiftToUsers().size());
            shiftToUsersToRemove.forEach(stu -> {
                try {
                    emailUtil.sendUpdateCapacityEmail(stu.getUser().getEmail(), stu.getShift().getEvent().getId());
                } catch (MessagingException ex) {
                    throw new RuntimeException(ex);
                }
                shift.setRegisteredUsers(shift.getRegisteredUsers() - 1);
            });

            shift.getShiftToUsers().removeAll(shiftToUsersToRemove);
        }
        shift.setCapacity(capacity);
    }

    private void updateShiftAddress(Shift shift, Shift newShift) {
        var address = shift.getAddress();
        var newAddress =newShift.getAddress();
        address.setDistrict(newAddress.getDistrict());
        address.setStreet(newAddress.getStreet());
        address.setHomeNum(newAddress.getHomeNum());
    }

    private boolean areShiftFieldsChanged(Shift shift, Shift newShift) {
        return !Objects.equals(shift.getStartTime(), newShift.getStartTime()) ||
                !Objects.equals(shift.getEndTime(), newShift.getEndTime()) ||
                !Objects.equals(shift.getAddress(), newShift.getAddress()) ||
                !Objects.equals(shift.getShiftDirectionsPL(), newShift.getShiftDirectionsPL()) ||
                !Objects.equals(shift.getShiftDirectionsEN(), newShift.getShiftDirectionsEN()) ||
                !Objects.equals(shift.getShiftDirectionsUA(), newShift.getShiftDirectionsUA()) ||
                !Objects.equals(shift.getShiftDirectionsRU(), newShift.getShiftDirectionsRU());
    }

    private void addNewShifts(Event event, List<Shift> newShifts) {
        newShifts.stream()
                .filter(ns -> ns.getId() == null)
                .forEach(event.getShifts()::add);
    }
}
