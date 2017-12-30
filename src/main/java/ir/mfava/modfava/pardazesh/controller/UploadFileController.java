package ir.mfava.modfava.pardazesh.controller;

import ir.mfava.modfava.pardazesh.model.UploadFile;
import ir.mfava.modfava.pardazesh.model.UploadFileType;
import ir.mfava.modfava.pardazesh.service.UploadFileService;
import ir.mfava.modfava.pardazesh.service.UploadFileTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Controller
public class UploadFileController {

    @Autowired
    private UploadFileTypeService uploadFileTypeService;
    @Autowired
    private UploadFileService uploadFileService;

    @RequestMapping(value = {"/base-info/files/view", "/base-info/files/", "/base-info/files"}, method = RequestMethod.GET)
    public String showFilesManager(ModelMap map, HttpSession session) {
        map.put("uploadFiles", uploadFileService.getAllAvailable());
        map.put("activeUploadFileTypes", uploadFileTypeService.getAllActive());
        map.put("allUploadFileTypes", uploadFileTypeService.getAll());


        map.put("successMessage", session.getAttribute("successMessage"));
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "files-upload";
    }

    @RequestMapping(value = {"/files/view", "/files/", "/files"}, method = RequestMethod.GET)
    public String showFiles(ModelMap map) {
        map.put("uploadFiles", uploadFileService.getAllAvailable());
        map.put("allUploadFileTypes", uploadFileTypeService.getAll());
        return "files-view";
    }

    @RequestMapping(value = "/files/get/{uploadFileId}", method = RequestMethod.GET)
    public void downloadFile(@PathVariable(name = "uploadFileId") Long uploadFileId,
                             HttpServletResponse response) throws IOException {
        UploadFile uploadFile = uploadFileService.getById(uploadFileId);
        File file = new File("/d/file_upload_base/" + uploadFile.getFileType().getId() + "/" + uploadFile.getId() + "/" + uploadFile.getFileName());
        if (file.exists()) {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename='"+ uploadFile.getFileName() +"'");
            FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
            response.getOutputStream().flush();
        }
    }

    @RequestMapping(value = "/base-info/files/type/save")
    public String saveFileType(@RequestParam(name = "id", required = false) Long typeId,
                               @RequestParam(name = "name") String name,
                               HttpSession session) {
        UploadFileType uploadFileType;
        if (typeId != null) {
            uploadFileType = uploadFileTypeService.getById(typeId);
        } else {
            uploadFileType = new UploadFileType();
        }
        uploadFileType.setActive(true);
        uploadFileType.setName(name);
        try {
            uploadFileTypeService.save(uploadFileType);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
        }
        return "redirect:/base-info/files/view";
    }

    @RequestMapping(value = "/base-info/files/save", method = RequestMethod.POST)
    public String saveFile(@RequestParam(name = "typeId") Long typeId,
                           @RequestParam(name = "file") MultipartFile file,
                           HttpSession session) {
        UploadFile uploadFile = new UploadFile();

        UploadFileType uploadFileType = uploadFileTypeService.getById(typeId);
        if (uploadFileType == null) {
            session.setAttribute("errorMessage", "نوع فایل تعریف نشده است.");
            return "redirect:/base-info/files/view";
        }
        uploadFile.setFileType(uploadFileType);
        if (file != null && file.getSize() > 0) {
            try {
                uploadFile.setFileName(file.getOriginalFilename());
                uploadFile.setCreateDate(new Date());
                uploadFile.setDeleted(false);
                uploadFile = uploadFileService.save(uploadFile);
                session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");

                File fileDirectory = new File("/d/file_upload_base/" + uploadFileType.getId() + "/" + uploadFile.getId());
                if (!fileDirectory.exists()) {
                    fileDirectory.mkdirs();
                }
                Path path = Paths.get(fileDirectory + "/" + file.getOriginalFilename());
                try {
                    Files.write(path, file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
            }
        } else {
            System.out.println("Bad file");
            session.setAttribute("errorMessage", "خطا در بارگذاری فایل.");
            return "redirect:/base-info/files/view";
        }
        return "redirect:/base-info/files/view";
    }

    @RequestMapping(value = "/base-info/files/type/update")
    public String updateFileType(@RequestParam(name = "id") Long id,
                                 @RequestParam(name = "active") Boolean active,
                                 HttpSession session) {
        UploadFileType uploadFileType = uploadFileTypeService.getById(id);
        if (uploadFileType == null) {
            session.setAttribute("errorMessage", "نوع فایل تعریف نشده است.");
            return "redirect:/files/view";
        }
        uploadFileType.setActive(active);
        try {
            uploadFileTypeService.save(uploadFileType);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
        }
        return "redirect:/base-info/files/view";
    }


    @RequestMapping(value = "/base-info/files/remove", method = RequestMethod.POST)
    public String saveBulletin(@RequestParam(name = "id") Long id,
                               HttpSession session) {
        UploadFile uploadFile = uploadFileService.getById(id);
        if (uploadFile == null) {
            session.setAttribute("errorMessage", "خطا در بازیابی اطلاعات.");
            return "redirect:/base-info/files/view";
        }
        try {
            uploadFile.setDeleted(true);
            uploadFileService.save(uploadFile);
            session.setAttribute("successMessage", "حذف اطلاعات با موفقیت انجام شد.");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
        }
        return "redirect:/base-info/files/view";
    }
}
