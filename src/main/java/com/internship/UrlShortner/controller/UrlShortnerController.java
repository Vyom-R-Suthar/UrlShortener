package com.internship.UrlShortner.controller;

import com.internship.UrlShortner.model.Url;
import com.internship.UrlShortner.model.UrlRequestModel;
import com.internship.UrlShortner.model.UrlResponseModel;
import com.internship.UrlShortner.service.UrlShortnerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/url")
public class UrlShortnerController {

    @Autowired
    private UrlShortnerService service;
    
    @PostMapping("/short")
    public ResponseEntity<UrlResponseModel> createShortUrl(@RequestBody UrlRequestModel model){
        UrlResponseModel responseModel = service.createShortUrl(model);
        return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> redirectToOriginalUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        Url url = service.isShortUrlExist(shortUrl);
        if(url != null && url.getExpiresAt().isAfter(LocalDateTime.now())){
            response.sendRedirect(url.getOriginalUrl());
        }
        return new ResponseEntity<>("Url is either expired or does not exist",HttpStatus.OK);
    }

    @DeleteMapping("/{shortUrl}")
    public ResponseEntity<String> deleteShortUrl(@PathVariable String shortUrl){
        Url url = service.isShortUrlExist(shortUrl);
        if(url != null) {
            service.deleteUrl(url);
            return new ResponseEntity<>("Url Deleted successfully",HttpStatus.OK);
        }
            return new ResponseEntity<>("Url does not exist",HttpStatus.BAD_REQUEST);
    }
}
