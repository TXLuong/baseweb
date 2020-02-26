package com.hust.baseweb.applicationsandbox.controller;

import java.security.Principal;

import lombok.extern.log4j.Log4j2;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hust.baseweb.applications.order.model.ModelCreateOrderInput;
import com.hust.baseweb.applicationsandbox.entity.SOrderHeader;

import java.util.List;
@RestController
@Log4j2
public class OrderAPIController {
	
	@PostMapping("/s-create-order")
	public ResponseEntity<?> createOrder(Principal principal, @RequestBody ModelCreateOrderInput input){
		SOrderHeader o = null;// TODO
		return ResponseEntity.ok().body(o);
	}
	@GetMapping(path = "/s-orders")
    public ResponseEntity<?> getOrders(Pageable page,
            @RequestParam(name = "search", required = false) String searchString,
            @RequestParam(name = "filter", required = false) String filterString) {
        log.info("::getUsers, searchString = " + searchString);
        List<SOrderHeader> lst = null;// TODO
        return ResponseEntity.ok().body(lst);
    }
}
