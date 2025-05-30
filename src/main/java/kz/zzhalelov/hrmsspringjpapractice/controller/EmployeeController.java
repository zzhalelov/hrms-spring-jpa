package kz.zzhalelov.hrmsspringjpapractice.controller;

import kz.zzhalelov.hrmsspringjpapractice.dto.employeeDto.EmployeeCreateDto;
import kz.zzhalelov.hrmsspringjpapractice.dto.employeeDto.EmployeeMapper;
import kz.zzhalelov.hrmsspringjpapractice.dto.employeeDto.EmployeeResponseDto;
import kz.zzhalelov.hrmsspringjpapractice.dto.employeeDto.EmployeeUpdateDto;
import kz.zzhalelov.hrmsspringjpapractice.model.*;
import kz.zzhalelov.hrmsspringjpapractice.repository.EmployeeRepository;
import kz.zzhalelov.hrmsspringjpapractice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    //find all employees
    @GetMapping
    public List<EmployeeResponseDto> findAll() {
        return employeeService.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    //find by id
    @GetMapping("/{id}")
    public EmployeeResponseDto findById(@PathVariable int id) {
        return employeeMapper.toResponse(employeeService.findById(id));
    }

    //create
    @PostMapping
    public EmployeeResponseDto create(@RequestBody EmployeeCreateDto employeeCreateDto) {
        Employee employee = employeeMapper.fromCreate(employeeCreateDto);
        return employeeMapper.toResponse(employeeService.create(employee));
    }

    //update
    @PutMapping("/{id}")
    public EmployeeResponseDto update(@PathVariable int id,
                                      @RequestBody EmployeeUpdateDto employeeUpdateDto) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow();
        existingEmployee.setFirstName(employeeUpdateDto.getFirstName());
        existingEmployee.setLastName(employeeUpdateDto.getLastName());
        existingEmployee.setEmail(employeeUpdateDto.getEmail());
        existingEmployee.setPhone(employeeUpdateDto.getPhone());
        return employeeMapper.toResponse(employeeService.update(existingEmployee));
    }

    //delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        employeeService.delete(id);
    }

    //find by first name
    @GetMapping("/find-by-firstname")
    public List<EmployeeResponseDto> findByFirstName(String name) {
        return employeeService.findByFirstName(name)
                .stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }
}