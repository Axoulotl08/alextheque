package com.axoulotl.alextheque.service;

import com.axoulotl.alextheque.repository.ConsoleRepository;
import com.axoulotl.alextheque.service.validation.ConsoleValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConsoleService {

    ConsoleRepository consoleRepository;
    ConsoleValidationService consoleValidationService;

    @Autowired
    public ConsoleService(ConsoleRepository consoleRepository,
                          ConsoleValidationService consoleValidationService){
        this.consoleRepository = consoleRepository;
        this.consoleValidationService = consoleValidationService;
    }

    public ResponseEntity<Object> addConsole(){
        return ResponseEntity.accepted().build();
    }
}
