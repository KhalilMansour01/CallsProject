package com.example.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.backend.Entity.CallsEntity;
import com.example.backend.Service.CallsService;
import com.example.backend.Exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/calls")
public class CallsController {
    @Autowired
    CallsService callsService;

    @GetMapping("/all")
    public ResponseEntity<List<CallsEntity>> getAllCalls() {
        List<CallsEntity> list = callsService.getAllCalls();
        return new ResponseEntity<List<CallsEntity>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CallsEntity> getCallsById(@PathVariable("id") BigDecimal id)
            throws ResourceNotFoundException {
        return callsService.getCallsById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<CallsEntity> createCalls(@RequestBody CallsEntity calls) throws ResourceNotFoundException {
        return callsService.createCalls(calls);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CallsEntity> updateCalls(@PathVariable("id") BigDecimal id, @RequestBody CallsEntity calls)
            throws ResourceNotFoundException {
        return callsService.updateCalls(id, calls);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CallsEntity> deleteCallsById(@PathVariable("id") BigDecimal id)
            throws ResourceNotFoundException {
        return callsService.deleteCallsById(id);
    }

    // @GetMapping("/filter")
    // public ResponseEntity<List<CallsEntity>> filterCalls(
    //         @RequestParam(required = false) String fCat,
    //         @RequestParam(required = false) Boolean open) {

    //     List<CallsEntity> calls = callsService.filterCalls(fCat, open);
    //     return ResponseEntity.ok().body(calls);
    // }

    // @GetMapping("/search")
    // public ResponseEntity<List<CallsEntity>> searchCalls(
    //         @RequestParam(required = false) String searchQuery) {

    //     List<CallsEntity> calls = callsService.searchCalls(searchQuery);
    //     return ResponseEntity.ok().body(calls);
    // }

    // @GetMapping("/filterAndSearch")
    // public ResponseEntity<List<CallsEntity>> searchAndFilterCalls(
    //         @RequestParam(required = false) String fCat,
    //         @RequestParam(required = false) Boolean open,
    //         @RequestParam(required = false) String searchQuery) {

    //     List<CallsEntity> calls = callsService.filterAndSearchCalls(fCat, open, searchQuery);
    //     return ResponseEntity.ok().body(calls);
    // }

    /*
     * Advanced GET request to get paginated calls with sorting and filtering
     */
    @GetMapping("/advancedGet")
    public ResponseEntity<Page<CallsEntity>> pagedCalls(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String fCat,
            @RequestParam(required = false) Boolean open,
            @RequestParam(required = false) String searchQuery) {

        Sort sort = Sort.by("asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC,
                sortField);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<CallsEntity> callsPage = callsService.paginatedCalls(pageable, fCat, open, searchQuery);

        if (callsPage.hasContent()) {
            return ResponseEntity.ok(callsPage);
        } else {
            return ResponseEntity.ok(new PageImpl<>(new ArrayList<>(), pageable, 0));
            // return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }
}