package ir.mfava.modfava.pardazesh.controller;


import ir.mfava.modfava.pardazesh.model.User;
import ir.mfava.modfava.pardazesh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import java.io.File;

/**
 * @author Drago
 */
@SuppressWarnings({"ResultOfMethodCallIgnored", "WeakerAccess"})
public class BaseController {
    @Autowired
    private UserService userService;

    public User getUser(Authentication authentication) {
        return userService.findByUsername(((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername());
    }

    public Long getUserId(Authentication authentication) {
        return getUser(authentication).getId();
    }

    public File getIconsDirectory(){
        File file = new File("d:/gis_backend_repository/icons_directory");
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }

    public File getSatImageProcessedDirectory(){
        File file = new File("d:/gis_backend_repository/weather_base_directory/processed/sat/");
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }

    public File getDustImageProcessedDirectory(){
        File file = new File("d:/gis_backend_repository/weather_base_directory/processed/dust/");
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }

    public File getFileUploadBaseDirectory(){
        File file = new File("d:/gis_backend_repository/file_upload_base/");
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }
}
