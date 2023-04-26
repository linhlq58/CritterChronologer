package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.data.schedule.Schedule;
import com.udacity.jdnd.course3.critter.data.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s JOIN s.employees e WHERE :employeeId = e.id")
    List<Schedule> getScheduleForEmployee(Long employeeId);

    @Query("SELECT s FROM Schedule s JOIN s.pets p WHERE :petId = p.id")
    List<Schedule> getScheduleForPet(Long petId);

    @Query("SELECT s FROM Schedule s JOIN s.pets p WHERE :customerId = p.ownerId")
    List<Schedule> getScheduleForCustomer(Long customerId);
}
