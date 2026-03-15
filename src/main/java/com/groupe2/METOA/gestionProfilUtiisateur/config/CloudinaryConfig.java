package com.groupe2.METOA.gestionProfilUtiisateur.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "drrebrk4k",
                "api_key", "524364548898211",
                "api_secret", "u5yn3EfYs9KIcH90xUadfOZPCzQ"
        ));
    }
}