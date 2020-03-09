package cn.zhang.com.controller;

import cn.zhang.com.dto.FileDTO;
import org.apache.catalina.core.ApplicationPart;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileController {

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request, HttpSession session) throws IOException {
        MultipartHttpServletRequest multipartHttpServletRequest= (MultipartHttpServletRequest) request;
        MultipartFile files = multipartHttpServletRequest.getFile("editormd-image-file");
        String fineName=files.getOriginalFilename();
        fineName = UUID.randomUUID() + fineName;//给文件新的文件名
        File dest = new File("image/"+ fineName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        System.out.println(dest.getAbsolutePath());
        files.transferTo(dest);
        System.out.println(dest.getAbsolutePath());
        FileDTO fileDTO=new FileDTO();
        fileDTO.setUrl("image/"+fineName);
        fileDTO.setSuccess(1);
        return fileDTO;

    }
}
