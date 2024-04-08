package com.internship.UrlShortner.repository;

import com.internship.UrlShortner.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShortnerRepository extends JpaRepository<Url,Integer> {

    public Url findByShortUrl(String shortUrl);
}
