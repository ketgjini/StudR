package mshsie.masterproject.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import mshsie.masterproject.model.File;
import mshsie.masterproject.service.FileService;

@Controller
@RequestMapping("/download")
public class DownloadController {

	@Autowired
	FileService fileService;
	
    @GetMapping("/file/{fid}")
    public void downloadFile(@PathVariable("fid") Long fid, HttpServletResponse resp) throws IOException {

        File dbFile = fileService.findFileById(fid);

        byte[] byteArray =  dbFile.getFile(); // read the byteArray

        resp.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM.getType()); 
        resp.setHeader("Content-Disposition", "attachment; filename=" + dbFile.getFileName());
        resp.setContentLength(byteArray.length);

        OutputStream os = resp.getOutputStream();
        try {
            os.write(byteArray, 0, byteArray.length);
        } finally {
            os.close();
        }

    }
}