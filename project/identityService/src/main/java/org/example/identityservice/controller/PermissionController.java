package org.example.identityservice.controller;

import jakarta.validation.Valid;
import org.example.identityservice.dto.PermissionCreationRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @PostMapping("creation")
    Object creation(@Valid @RequestBody List<PermissionCreationRequest> request) {
        return request;
    }
}
