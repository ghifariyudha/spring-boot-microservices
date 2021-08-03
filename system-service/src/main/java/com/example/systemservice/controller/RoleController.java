package com.example.systemservice.controller;

import com.example.systemservice.domain.entity.Role;
import com.example.systemservice.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api(tags = "Role")
@RestController
@RequestMapping(value = "role", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {

    @Autowired
    RoleService service;

    @PostMapping("save")
    @ApiOperation(value = "Save")
    public ResponseEntity<Object> save(@Valid @RequestBody Role req) {
        Role role = service.save(req);
        return ResponseEntity.ok(role);
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
        Optional<Role> role = service.getOne(id);
        return role.isPresent()
                ? ResponseEntity.ok(role)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data is not found");
    }

    @GetMapping("list")
    @ApiOperation("List")
    public ResponseEntity<Object> list() {
        List<Role> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("pgb")
    @ApiOperation("Page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "page", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "sort", paramType = "query", dataType = "string", allowMultiple = true)
    })
    public ResponseEntity<Object> pgb(@ApiIgnore @PageableDefault(sort = "idRole", direction = Sort.Direction.DESC) Pageable pgb) {
        Page<Role> page = service.findAll(pgb);
        return ResponseEntity.ok(page);
    }
}
