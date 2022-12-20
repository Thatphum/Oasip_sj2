package oasip.backend.Controller;

import oasip.backend.Service.FileStorage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;


@RestController
@RequestMapping("/api/file")
public class FileController {
    private final FileStorageService fileService;

    @Autowired
    public FileController(FileStorageService fileService){
        this.fileService = fileService;
    }

    @GetMapping("/{folderId}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable("folderId") String id,@PathVariable("filename") String filename){
        Resource file = fileService.loadFileAsResource(id,filename);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
    }
    @PostMapping("")
    public String fileUpload(@RequestParam("folderId") String folderId,@RequestParam("file") MultipartFile file) {
        fileService.storeFile(folderId , file);
        return "You successfully uploaded " + file.getOriginalFilename() + "!";
    }
    @DeleteMapping("/{folderId}")
    public String deleteFile(@PathVariable("folderId") String folderId) throws IOException {
        fileService.deleteDirectory(folderId);
        return "You successfully uploaded !";
    }
}
