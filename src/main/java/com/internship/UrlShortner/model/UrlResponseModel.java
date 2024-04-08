package com.internship.UrlShortner.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UrlResponseModel {

    private String originalUrl;

    private String shortUrl;
}
