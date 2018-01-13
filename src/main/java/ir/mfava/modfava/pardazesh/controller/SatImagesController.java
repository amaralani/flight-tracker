package ir.mfava.modfava.pardazesh.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping(value = "/satellite")
public class SatImagesController extends BaseController {


    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String getSatImages(ModelMap map) {
        List<String> satFileNames = new ArrayList<>();
        File satImageProcessedDirectory = getSatImageProcessedDirectory();
        putImagesInList(satFileNames, satImageProcessedDirectory);

        List<String> dustFileNames = new ArrayList<>();
        File dustImageProcessedDirectory = getDustImageProcessedDirectory();
        putImagesInList(dustFileNames, dustImageProcessedDirectory);

        Collections.sort(dustFileNames);
        Collections.sort(satFileNames);
        map.put("satFiles", satFileNames);
        map.put("dustFiles", dustFileNames);
        return "satellite-images";
    }

    private void putImagesInList(List<String> fileNames, File ImagesDirectory) {
        if (ImagesDirectory.listFiles() != null) {
            List<File> oldSatImages = Arrays.asList(ImagesDirectory.listFiles());
            for (File oldSatImage : oldSatImages) {
                if (oldSatImage.isFile()) {
                    fileNames.add(oldSatImage.getName());
                }
            }
        }
    }

    @RequestMapping(value = "/{imageType:cloud|dust}/image/{fileName:.+}")
    public void getImage(@PathVariable(value = "fileName") String filename,
                         @PathVariable(value = "imageType") String imageType,
                         HttpServletResponse httpResponse) throws IOException {
        File satImage = null;
        if (imageType.equals("cloud")) {
            satImage = new File(getSatImageProcessedDirectory() + filename);
        } else if (imageType.equals("dust")) {
            satImage = new File(getDustImageProcessedDirectory() + filename);
        }
        if (satImage != null) {
            IOUtils.copy(new FileInputStream(satImage), httpResponse.getOutputStream());
        }
    }

}
