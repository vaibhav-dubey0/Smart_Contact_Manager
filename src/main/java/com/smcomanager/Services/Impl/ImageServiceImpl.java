package com.smcomanager.Services.Impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smcomanager.Services.ImageService;

@Service
public class ImageServiceImpl implements ImageService{

    @Override
    public String uploadImage(MultipartFile contactImage, String filename) {
        
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
       
    }

    
}
