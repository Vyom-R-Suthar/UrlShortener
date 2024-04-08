package com.internship.UrlShortner.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UrlRequestModel {

    private String originalUrl;

    private String expiresAt;
}
