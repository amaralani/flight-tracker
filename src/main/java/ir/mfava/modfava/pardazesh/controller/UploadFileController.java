package ir.mfava.modfava.pardazesh.controller;

import ir.mfava.modfava.pardazesh.model.UploadFile;
import ir.mfava.modfava.pardazesh.model.UploadFileType;
import ir.mfava.modfava.pardazesh.model.report.event.Event;
import ir.mfava.modfava.pardazesh.service.UploadFileService;
import ir.mfava.modfava.pardazesh.service.UploadFileTypeService;
import ir.mfava.modfava.pardazesh.service.report.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UploadFileController extends BaseController {

    @Autowired
    private UploadFileTypeService uploadFileTypeService;
    @Autowired
    private UploadFileService uploadFileService;
    @Autowired
    private EventService eventService;

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
        File file = new File(getFileUploadBaseDirectory().getAbsolutePath() + uploadFile.getFileType().getId() + "/" + uploadFile.getId() + "/" + uploadFile.getFileName());
        if (file.exists()) {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + uploadFile.getFileName());
            FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
            response.getOutputStream().flush();
        }
    }

    @RequestMapping(value = "/base-info/files/type/save")
    public String saveFileType(@RequestParam(name = "id", required = false) Long typeId,
                               @RequestParam(name = "name") String name,
                               HttpSession session,
                               HttpServletRequest request,
                               Authentication authentication) {
        Map<String, String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity", "File Type");
        descriptionMap.put("id", String.valueOf(typeId));
        descriptionMap.put("name", String.valueOf(name));

        UploadFileType uploadFileType;
        Event.SubType subType;
        Event.Flag flag;
        if (typeId != null) {
            uploadFileType = uploadFileTypeService.getById(typeId);
            subType = Event.SubType.EDIT_DATA;
        } else {
            uploadFileType = new UploadFileType();
            subType = Event.SubType.NEW_DATA;
        }
        uploadFileType.setActive(true);
        uploadFileType.setName(name);
        try {
            uploadFileTypeService.save(uploadFileType);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception e) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, subType, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/files/view";
    }

    @RequestMapping(value = "/base-info/files/save", method = RequestMethod.POST)
    public String saveFile(@RequestParam(name = "typeId") Long typeId,
                           @RequestParam(name = "file") MultipartFile file,
                           @RequestParam(name = "title") String title,
                           HttpSession session,
                           HttpServletRequest request,
                           Authentication authentication) {
        Map<String, String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity", "File");
        descriptionMap.put("typeId", String.valueOf(typeId));
        descriptionMap.put("file", file.getOriginalFilename());
        descriptionMap.put("title", String.valueOf(title));

        UploadFile uploadFile = new UploadFile();
        Event.Flag flag;
        UploadFileType uploadFileType = uploadFileTypeService.getById(typeId);
        if (uploadFileType == null) {
            session.setAttribute("errorMessage", "نوع فایل تعریف نشده است.");
            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, Event.SubType.NEW_DATA, Event.Flag.FAILURE, descriptionMap, Event.Sensitivity.NOTIFICATION);
            return "redirect:/base-info/files/view";
        }
        uploadFile.setFileType(uploadFileType);
        if (file != null && file.getSize() > 0) {
            try {
                uploadFile.setFileName(file.getOriginalFilename());
                uploadFile.setCreateDate(new Date());
                uploadFile.setTitle(title);
                uploadFile.setDeleted(false);
                uploadFile = uploadFileService.save(uploadFile);
                session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
                flag = Event.Flag.SUCCESS;
                File fileDirectory = new File(getFileUploadBaseDirectory().getAbsolutePath() + uploadFileType.getId() + "/" + uploadFile.getId());
                if (!fileDirectory.exists()) {
                    //noinspection ResultOfMethodCallIgnored
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
                flag = Event.Flag.FAILURE;
            }
        } else {
            System.out.println("Bad file");
            session.setAttribute("errorMessage", "خطا در بارگذاری فایل.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, Event.SubType.NEW_DATA, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/files/view";
    }

    @RequestMapping(value = "/base-info/files/type/update")
    public String updateFileType(@RequestParam(name = "id") Long id,
                                 @RequestParam(name = "active") Boolean active,
                                 HttpSession session,
                                 HttpServletRequest request,
                                 Authentication authentication) {
        Map<String, String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity", "File Type");
        descriptionMap.put("id", String.valueOf(id));
        descriptionMap.put("active", String.valueOf(active));

        UploadFileType uploadFileType = uploadFileTypeService.getById(id);
        if (uploadFileType == null) {
            session.setAttribute("errorMessage", "نوع فایل تعریف نشده است.");
            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, Event.SubType.EDIT_DATA, Event.Flag.FAILURE, descriptionMap, Event.Sensitivity.NOTIFICATION);
            return "redirect:/base-info/files/view";
        }
        Event.Flag flag;
        uploadFileType.setActive(active);
        try {
            uploadFileTypeService.save(uploadFileType);
            session.setAttribute("successMessage", "ثبت اطلاعات با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception e) {
            session.setAttribute("errorMessage", "خطا در ثبت اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, Event.SubType.EDIT_DATA, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/files/view";
    }


    @RequestMapping(value = "/base-info/files/remove", method = RequestMethod.POST)
    public String removeFile(@RequestParam(name = "id") Long id,
                             HttpSession session,
                             HttpServletRequest request,
                             Authentication authentication) {
        Map<String, String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity", "File");
        descriptionMap.put("id", String.valueOf(id));

        UploadFile uploadFile = uploadFileService.getById(id);
        if (uploadFile == null) {
            session.setAttribute("errorMessage", "خطا در بازیابی اطلاعات.");
            eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.DELETE, Event.SubType.DELETE_FROM_DB, Event.Flag.FAILURE, descriptionMap, Event.Sensitivity.NOTIFICATION);
            return "redirect:/base-info/files/view";
        }
        Event.Flag flag;
        try {
            uploadFile.setDeleted(true);
            uploadFileService.save(uploadFile);
            session.setAttribute("successMessage", "حذف اطلاعات با موفقیت انجام شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "خطا در حذف اطلاعات.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.DELETE, Event.SubType.DELETE_FROM_DB, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/base-info/files/view";
    }
}
