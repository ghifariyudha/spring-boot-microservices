package com.example.productservice.controller;

import com.example.productservice.domain.entity.Product;
import com.example.productservice.service.ProductService;
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

@Api(tags = "Product")
@RestController
@RequestMapping(value = "product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    @Autowired
    ProductService service;

    @PostMapping("save")
    @ApiOperation(value = "Save")
    public ResponseEntity<Object> save(@Valid @RequestBody Product req) {
        Product product = service.save(req);
        return ResponseEntity.ok(product);
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
        Product product = service.getOne(id);
        return product != null
                ? ResponseEntity.ok(product)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data is not found");
    }

    @GetMapping("list")
    @ApiOperation("List")
    public ResponseEntity<Object> list() {
        List<Product> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("pgb")
    @ApiOperation("Page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "page", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "sort", paramType = "query", dataType = "string", allowMultiple = true)
    })
    public ResponseEntity<Object> pgb(@ApiIgnore @PageableDefault(sort = "idProduct", direction = Sort.Direction.DESC) Pageable pgb) {
        Page<Product> page = service.findAll(pgb);
        return ResponseEntity.ok(page);
    }
}
