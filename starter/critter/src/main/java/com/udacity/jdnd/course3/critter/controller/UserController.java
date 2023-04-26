package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.user.*;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer savedCustomer = customerService.save(convertCustomerDTOToCustomer(customerDTO));
        customerDTO.setId(savedCustomer.getId());
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return convertListCustomerToListCustomerDTO(customerService.getAllCustomers());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return convertCustomerToCustomerDTO(customerService.getOwnerByPet(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee savedEmployee = employeeService.save(convertEmployeeDTOToEmployee(employeeDTO));
        employeeDTO.setId(savedEmployee.getId());
        return employeeDTO;
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEmployeeToEmployeeDTO(employeeService.getEmployeeById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return convertListEmployeeToListEmployeeDTO(employeeService.findEmployeesForService(employeeDTO));
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    private List<EmployeeDTO> convertListEmployeeToListEmployeeDTO(List<Employee> listEmployee) {
        List<EmployeeDTO> listEmployeeDTO = new ArrayList<>();

        for (Employee employee : listEmployee) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            BeanUtils.copyProperties(employee, employeeDTO);
            listEmployeeDTO.add(employeeDTO);
        }

        return listEmployeeDTO;
    }

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        if (customer.getPets().size() > 0) {
            List<Long> listPetId = new ArrayList<>();
            for (Pet pet : customer.getPets()) {
                listPetId.add(pet.getId());
            }
            customerDTO.setPetIds(listPetId);
        }

        return customerDTO;
    }

    private List<CustomerDTO> convertListCustomerToListCustomerDTO(List<Customer> listCustomer) {
        List<CustomerDTO> listCustomerDTO = new ArrayList<>();

        for (Customer customer : listCustomer) {
            CustomerDTO customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(customer, customerDTO);

            if (customer.getPets().size() > 0) {
                List<Long> listPetId = new ArrayList<>();
                for (Pet pet : customer.getPets()) {
                    listPetId.add(pet.getId());
                }
                customerDTO.setPetIds(listPetId);
            }

            listCustomerDTO.add(customerDTO);
        }

        return listCustomerDTO;
    }
}
