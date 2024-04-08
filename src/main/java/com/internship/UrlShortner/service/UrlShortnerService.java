package com.internship.UrlShortner.service;

import com.google.common.hash.Hashing;
import com.internship.UrlShortner.repository.UrlShortnerRepository;
import com.internship.UrlShortner.model.Url;
import com.internship.UrlShortner.model.UrlRequestModel;
import com.internship.UrlShortner.model.UrlResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UrlShortnerService {

    @Autowired
    private UrlShortnerRepository repository;

    public UrlResponseModel createShortUrl(UrlRequestModel model){
        String originalUrl = model.getOriginalUrl();
        String shortUrl = encodeUrl(originalUrl);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        if(!StringUtils.isEmpty(model.getExpiresAt())){
            System.out.println(model.getExpiresAt());
            model.setExpiresAt(model.getExpiresAt().concat(":00"));
        }
        validateExpiryDate(model.getExpiresAt(),dateTimeFormatter);

        Url url = Url.builder()
                .originalUrl(originalUrl)
                .shortUrl(shortUrl)
                .createdAt(LocalDateTime.now())
                .expiresAt(StringUtils.isBlank(model.getExpiresAt())?LocalDateTime.now().plusSeconds(120):LocalDateTime.parse(model.getExpiresAt(),dateTimeFormatter))
                .build();

        Url savedUrl = saveUrl(url);

        UrlResponseModel responseModel = UrlResponseModel.builder()
                .originalUrl(savedUrl.getOriginalUrl())
                .shortUrl(savedUrl.getShortUrl())
                .build();
        return responseModel;
    }

    private void validateExpiryDate(String expiresAt,DateTimeFormatter formatter) {
        if(!StringUtils.isEmpty(expiresAt)) {
            System.out.println("\n\nvalidate method's expire date: " + expiresAt);
            LocalDateTime time = LocalDateTime.parse(expiresAt, formatter);
            boolean isAfterCurrentTime = time.isAfter(LocalDateTime.now());
            if (!isAfterCurrentTime) {
                throw new IllegalArgumentException("ERR_MSG_EXPIRY_TIME_SHOULD_BE_AFTER_CURRENT_TIME");
            }
        }
    }

    private Url saveUrl(Url url) {
        return repository.save(url);
    }

    private String encodeUrl(String originalUrl) {
        if(!StringUtils.isBlank(originalUrl)){
            String encodedUrl = "";
            LocalDateTime time = LocalDateTime.now();
            encodedUrl = Hashing.murmur3_32_fixed().hashString(originalUrl.concat(time.toString()), StandardCharsets.UTF_8).toString();
            return encodedUrl;
        }else{
            throw new IllegalArgumentException("ERR_MSG_ORIGINAL_URL_REQUIRED");
        }

    }

    public Url isShortUrlExist(String shortUrl) {
        return repository.findByShortUrl(shortUrl);
    }

    public void deleteUrl(Url url) {
        repository.delete(url);
    }
}
