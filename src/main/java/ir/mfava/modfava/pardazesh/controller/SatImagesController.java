package ir.mfava.modfava.pardazesh.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping(value = "/satellite")
public class SatImagesController {


    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String getSatImages(ModelMap map, HttpSession session) {
        List<String> satFileNames = new ArrayList<>();
        File satImageProcessedDirectory = new File("/d/weather_base_directory/processed/sat/");
        if (!satImageProcessedDirectory.exists()) satImageProcessedDirectory.mkdirs();
        if (satImageProcessedDirectory.listFiles() != null) {
            List<File> oldSatImages = Arrays.asList(satImageProcessedDirectory.listFiles());
            for (File oldSatImage : oldSatImages) {
                if (oldSatImage.isFile()) {
                    satFileNames.add(oldSatImage.getName());
                }
            }
        }

        List<String> dustFileNames = new ArrayList<>();
        File dustImageProcessedDirectory = new File("/d/weather_base_directory/processed/dust/");
        if (!dustImageProcessedDirectory.exists()) dustImageProcessedDirectory.mkdirs();
        if (dustImageProcessedDirectory.listFiles() != null) {
            List<File> oldSatImages = Arrays.asList(dustImageProcessedDirectory.listFiles());
            for (File oldSatImage : oldSatImages) {
                if (oldSatImage.isFile()) {
                    dustFileNames.add(oldSatImage.getName());
                }
            }
        }

        Collections.sort(dustFileNames);
        Collections.sort(satFileNames);
        map.put("satFiles", satFileNames);
        map.put("dustFiles", dustFileNames);
        return "satellite-images";
    }

    @RequestMapping(value = "/{imageType:cloud|dust}/image/{fileName:.+}")
    public void getImage(@PathVariable(value = "fileName") String filename,
                         @PathVariable(value = "imageType") String imageType,
                         HttpServletResponse httpResponse) throws IOException {
        File satImage = null;
        if (imageType.equals("cloud")) {
            satImage = new File("/d/weather_base_directory/processed/sat/" + filename);
        } else if (imageType.equals("dust")) {
            satImage = new File("/d/weather_base_directory/processed/dust/" + filename);
        }

        if (satImage != null) {
            IOUtils.copy(new FileInputStream(satImage), httpResponse.getOutputStream());
        }
    }

}
