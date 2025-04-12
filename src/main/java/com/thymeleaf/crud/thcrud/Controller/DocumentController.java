package com.thymeleaf.crud.thcrud.Controller;

import com.thymeleaf.crud.thcrud.Model.Document;
import com.thymeleaf.crud.thcrud.Repository.DocumentRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class DocumentController {

    private final DocumentRepository repository;

    public DocumentController(DocumentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        model.addAttribute("document", new Document());
        model.addAttribute("documents", repository.findAll());
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("title") String title,
                                   @RequestParam("file") MultipartFile file) throws IOException {

        Document document = new Document();
        document.setTitle(title);
        document.setFileData(file.getBytes());

        repository.save(document);
        return "redirect:/upload?success";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        Optional<Document> optionalDoc = repository.findById(id);

        if (optionalDoc.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Document document = optionalDoc.get();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getTitle() + ".pdf\"")
                .body(document.getFileData());
    }

}

