package ru.homework.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @GetMapping("/public-data")
    public ResponseEntity<String> PublicData(){
        return new ResponseEntity<>("This information is available for all authorized users", HttpStatus.OK);
    }

    @GetMapping("/private-data")
    public ResponseEntity<String> PrivateData(){
        return new ResponseEntity<>("Only ADMIN has access to this information", HttpStatus.OK);
    }

//    @GetMapping("/index")
//    public ResponseEntity<String> StartPage(){
//        return new ResponseEntity<>("Welcome, users!", HttpStatus.OK);
//    }
}
