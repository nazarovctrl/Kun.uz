package com.example.kunuz.service;

import com.example.kunuz.dto.attach.AttachResponseDTO;
import com.example.kunuz.entity.AttachEntity;
import com.example.kunuz.enums.Language;
import com.example.kunuz.exp.AttachNotFoundException;
import com.example.kunuz.exp.CouldNotRead;
import com.example.kunuz.exp.OriginalFileNameNullException;
import com.example.kunuz.exp.SomethingWentWrong;
import com.example.kunuz.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AttachService {


    private final AttachRepository repository;

    @Value("${attach.upload.folder}")
    private String attachUploadFolder;

    @Value("${attach.download.url}")
    private String attachDownloadUrl;

    private final ResourceBundleService resourceBundleService;

    public AttachService(AttachRepository repository, ResourceBundleService resourceBundleService) {
        this.repository = repository;
        this.resourceBundleService = resourceBundleService;
    }

    public AttachResponseDTO saveToSystem(MultipartFile file,Language language) {
        try {

            String pathFolder = getYmDString(); // 2022/04/23
            File folder = new File(attachUploadFolder + pathFolder); // attaches/2022/04/23

            if (!folder.exists()) folder.mkdirs();


            String fileName = UUID.randomUUID().toString(); // dasdasd-dasdasda-asdasda-asdasd
            String extension = getExtension(file.getOriginalFilename(),language); //zari.jpg

            // attaches/2022/04/23/dasdasd-dasdasda-asdasda-asdasd.jpg
            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachUploadFolder + pathFolder + "/" + fileName + "." + extension);
            Files.write(path, bytes);

            AttachEntity entity = new AttachEntity();
            entity.setId(fileName);
            entity.setOriginalName(file.getOriginalFilename());
            entity.setExtension(extension);
            entity.setCreatedDate(LocalDateTime.now());
            entity.setPath(pathFolder);
            entity.setSize(file.getSize());
            repository.save(entity);


            AttachResponseDTO dto = new AttachResponseDTO();
            dto.setId(entity.getId());
            dto.setUrl(attachDownloadUrl + fileName + "." + extension);
            return dto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] open(String fileName) {
        // c83b82c7-0ed5-4d19-9f85-cf040c480e00 PNG
        BufferedImage originalImage;
        try {
            AttachEntity entity = getAttach(fileName);

            // attaches/2022/04/23/dasdasd-dasdasda-asdasda-asdasd.jpg
            originalImage = ImageIO.read(new File(attachUploadFolder + entity.getPath() + "/" + fileName));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "png", baos);

            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public byte[] open_general(String fileName) {

        try {
            AttachEntity entity = getAttach(fileName);


            Path file = Paths.get(attachUploadFolder + entity.getPath() + "/" + fileName);
            return Files.readAllBytes(file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Resource download(String fileName, Language language) {
        try {
            AttachEntity entity = getAttach(fileName);


            File file = new File(attachUploadFolder + entity.getPath() + "/" + fileName);

            File dir = file.getParentFile();
            File rFile = new File(dir, entity.getOriginalName());

            Resource resource = new UrlResource(rFile.toURI());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new CouldNotRead(resourceBundleService.getMessage("could.not.read",language));
            }
        } catch (MalformedURLException e) {
            throw new SomethingWentWrong(resourceBundleService.getMessage("wrong",language));
        }
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public String getExtension(String fileName,Language language) {
        // mp3/jpg/npg/mp4.....
        if (fileName == null) {
            throw new OriginalFileNameNullException(resourceBundleService.getMessage("file.name.null",language));
        }
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }


    public String deleteById(String fileName) {
        try {
            AttachEntity entity = getAttach(fileName);
            Path file = Paths.get(attachUploadFolder + entity.getPath() + "/" + fileName);

            Files.delete(file);
            repository.deleteById(entity.getId());

            return "deleted";
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

    }

    public AttachEntity getAttach(String fileName) {
        String id = fileName.split("\\.")[0];
        Optional<AttachEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new AttachNotFoundException("Attach Not Found");
        }
        return optional.get();
    }

    public Page<AttachResponseDTO> getWithPage(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AttachEntity> pageObj = repository.findAll(pageable);

        List<AttachEntity> entityList = pageObj.getContent();
        List<AttachResponseDTO> dtoList = new ArrayList<>();


        for (AttachEntity entity : entityList) {

            AttachResponseDTO dto = new AttachResponseDTO();
            dto.setId(entity.getId());
            dto.setPath(entity.getPath());
            dto.setExtension(entity.getExtension());
            dto.setUrl(attachDownloadUrl + "/" + entity.getId() + "." + entity.getExtension());
            dto.setOriginalName(entity.getOriginalName());
            dto.setSize(entity.getSize());
            dto.setCreatedData(entity.getCreatedDate());
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }


    public String getById(String imageId,Language language) {
        Optional<AttachEntity> optional = repository.findById(imageId);
        if (optional.isEmpty()) {
            throw new AttachNotFoundException(resourceBundleService.getMessage("not.found",language,"Attach"));
        }
        return attachDownloadUrl + optional.get().getId() + "." + optional.get().getExtension();

    }
}