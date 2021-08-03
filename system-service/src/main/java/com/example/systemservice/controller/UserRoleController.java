package com.example.systemservice.controller;

import com.example.systemservice.domain.entity.UserRole;
import com.example.systemservice.payload.response.UserRoleResp;
import com.example.systemservice.repository.UserRoleRepository;
import com.example.systemservice.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "User Role")
@RestController
@RequestMapping(value = "user-role", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRoleController {

    @Autowired
    UserRoleService service;
    @Autowired
    UserRoleRepository repository;

    @PostMapping("save")
    @ApiOperation(value = "Save")
    public ResponseEntity<Object> save(@Valid @RequestBody UserRole req) {
        if (repository.existsByUser_IdUserAndRole_IdRole(req.getUser().getIdUser(), req.getRole().getIdRole())) {
            throw new RuntimeException("Already exists!");
        }
        UserRole userRole = service.save(req);
        return ResponseEntity.ok(userRole);
    }

    @DeleteMapping("delete")
    @ApiOperation("Delete")
    public ResponseEntity<Object> delete(@RequestParam Long id){
        Boolean success = service.delete(id);
        return success ?
                ResponseEntity.status(HttpStatus.OK).body("Data successfully deleted") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete Data");
    }

    @GetMapping("get")
    @ApiOperation(value = "Get")
    public ResponseEntity<Object> get(@RequestParam Long id) {
        UserRoleResp userRoleResp = service.getOne(id);
        return ResponseEntity.ok(userRoleResp);
    }

    @GetMapping("list")
    @ApiOperation("List")
    public ResponseEntity<Object> list() {
        List<UserRoleResp> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("pgb")
    @ApiOperation("Page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "page", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "sort", paramType = "query", dataType = "string", allowMultiple = true)
    })
    public ResponseEntity<Object> pgb(@ApiIgnore @PageableDefault(sort = "idUserRole", direction = Sort.Direction.DESC) Pageable pgb) {
        Page<UserRoleResp> page = service.findAll(pgb);
        return ResponseEntity.ok(page);
    }
}
