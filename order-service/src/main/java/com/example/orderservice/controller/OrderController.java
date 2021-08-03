package com.example.orderservice.controller;

import com.example.orderservice.domain.entity.Order;
import com.example.orderservice.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api(tags = "Order")
@RestController
@RequestMapping(value = "order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    
    @Autowired
    OrderService service;

    @PostMapping("save")
    @ApiOperation(value = "Save")
    public ResponseEntity<Object> save(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody Order req) {
        Order order = service.save(token, req);
        return ResponseEntity.ok(order);
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
        Optional<Order> order = service.getOne(id);
        return order.isPresent()
                ? ResponseEntity.ok(order)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data is not found");
    }

    @GetMapping("list")
    @ApiOperation("List")
    public ResponseEntity<Object> list() {
        List<Order> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("pgb")
    @ApiOperation("Page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "page", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "sort", paramType = "query", dataType = "string", allowMultiple = true)
    })
    public ResponseEntity<Object> pgb(@ApiIgnore @PageableDefault(sort = "idOrder", direction = Sort.Direction.DESC) Pageable pgb) {
        Page<Order> page = service.findAll(pgb);
        return ResponseEntity.ok(page);
    }
}
