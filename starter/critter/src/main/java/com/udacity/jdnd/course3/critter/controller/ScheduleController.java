package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.pet.PetDTO;
import com.udacity.jdnd.course3.critter.data.schedule.Schedule;
import com.udacity.jdnd.course3.critter.data.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.data.user.Employee;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule savedSchedule = scheduleService.save(convertScheduleDTOToSchedule(scheduleDTO));
        scheduleDTO.setId(savedSchedule.getId());
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return convertListScheduleToListScheduleDTO(scheduleService.getAllSchedule());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return convertListScheduleToListScheduleDTO(scheduleService.getScheduleForPet(petId));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return convertListScheduleToListScheduleDTO(scheduleService.getScheduleForEmployee(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return convertListScheduleToListScheduleDTO(scheduleService.getScheduleForCustomer(customerId));
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Pet> pets = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();

        if (scheduleDTO.getPetIds() != null && scheduleDTO.getPetIds().size() > 0) {
            scheduleDTO.getPetIds().forEach(petId ->
                    pets.add(new Pet(petId))
            );
        }

        if (scheduleDTO.getEmployeeIds() != null && scheduleDTO.getEmployeeIds().size() > 0) {
            scheduleDTO.getEmployeeIds().forEach(employeeId ->
                    employees.add(new Employee(employeeId))
            );
        }

        schedule.setPets(pets);
        schedule.setEmployees(employees);

        return schedule;
    }

    private List<ScheduleDTO> convertListScheduleToListScheduleDTO(List<Schedule> listSchedule) {
        List<ScheduleDTO> listScheduleDTO = new ArrayList<>();

        for (Schedule schedule : listSchedule) {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            BeanUtils.copyProperties(schedule, scheduleDTO);

            List<Long> listPetId = new ArrayList<>();
            List<Long> listEmployeeId = new ArrayList<>();

            if (schedule.getPets() != null && schedule.getPets().size() > 0) {
                schedule.getPets().forEach(pet ->
                        listPetId.add(pet.getId())
                );
            }
            scheduleDTO.setPetIds(listPetId);

            if (schedule.getEmployees() != null && schedule.getEmployees().size() > 0) {
                schedule.getEmployees().forEach(employee ->
                        listEmployeeId.add(employee.getId())
                );
            }
            scheduleDTO.setEmployeeIds(listEmployeeId);

            listScheduleDTO.add(scheduleDTO);
        }

        return listScheduleDTO;
    }
}
