package ir.mfava.modfava.pardazesh.controller;

import com.sun.deploy.net.HttpResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping(value = "/satellite")
public class SatImagesController {


    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String getSatImages(ModelMap map, HttpSession session) {
        List<String> fileNames = new ArrayList<>();
        File satImageProcessedDirectory = new File("/d/weather_base_directory/processed/sat/");
        if (!satImageProcessedDirectory.exists()) satImageProcessedDirectory.mkdirs();
        if (satImageProcessedDirectory.listFiles() != null) {
            List<File> oldSatImages = Arrays.asList(satImageProcessedDirectory.listFiles());
            for (File oldSatImage : oldSatImages) {
                fileNames.add(oldSatImage.getName());
            }
        }
        Collections.sort(fileNames);
        map.put("files",fileNames);

        return "satellite-images";
    }

    @RequestMapping(value = "/image/{fileName:.+}")
    public void getImage(@PathVariable(value = "fileName") String filename, HttpServletResponse httpResponse) throws IOException {
        File satImage = new File("/d/weather_base_directory/processed/sat/"+filename);
        IOUtils.copy(new FileInputStream(satImage),  httpResponse.getOutputStream());
    }

}
