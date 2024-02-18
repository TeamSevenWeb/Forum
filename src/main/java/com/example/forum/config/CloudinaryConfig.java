package com.example.forum.config;
import com.cloudinary.*;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
@Configuration

public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dykfvzq8y",
                "api_key", "624787161519989",
                "api_secret", "2ZulLW2kMjUVFRLN9e0fmDX_ALk"));
        return cloudinary;
}}
