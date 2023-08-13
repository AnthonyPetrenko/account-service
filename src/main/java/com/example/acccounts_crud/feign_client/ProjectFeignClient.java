package com.example.acccounts_crud.feign_client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "project", url = "http://localhost:8081")
public interface ProjectFeignClient {

    @PostMapping("/change-status")
    void changeProjectStatus(@RequestParam ("project-id") long projectId);

    @GetMapping("/check-project")
    boolean checkIfProjectExist(@RequestParam ("project-id") long projectId);

}