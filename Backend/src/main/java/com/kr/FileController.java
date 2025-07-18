package com.kr;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class FileController {
    @Value("${upload.file.clothpath}")
    private String uploadclothPathStr;

    @Value("${upload.file.imagepath}")
    private String uploadimagePathStr;
    public FileController(){
    }

    @PostMapping("/upload/cloth")
    public @ResponseBody boolean upload_cloth(@RequestParam MultipartFile file,@RequestParam String filename)
    {
        if(file == null || file.isEmpty() || filename == null || filename.isEmpty())
            return false;
        try(InputStream inputStream = file.getInputStream())
        {
            Path uploadPath = Paths.get(uploadclothPathStr);
            if(!uploadPath.toFile().exists())
                uploadPath.toFile().mkdirs();
            Files.copy(inputStream, Paths.get(uploadclothPathStr).resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("upload file , filename is "+filename);
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/upload/image")
    public @ResponseBody boolean upload_image(@RequestParam MultipartFile file, @RequestParam String filename) {
        if (file == null || file.isEmpty() || filename == null || filename.isEmpty())
            return false;

        try (InputStream inputStream = file.getInputStream()) {
            Path uploadPath = Paths.get(uploadimagePathStr);
            if (!uploadPath.toFile().exists())
                uploadPath.toFile().mkdirs();
            Files.copy(inputStream, uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            String environmentPath = "F:\\Anaconda\\envs\\ladi-viton";
            String output_path = "F:\\Android-SpringBoot-Upload-Download-file-master\\backend\\upload\\image";
            String python_script = "F:\\FinalBuild\\removebackground.py";
            String command = String.format("conda run -p %s python %s " +
                    "--img_name %s --img_path %s --output_path %s " +
                    "--output_name %s", environmentPath, python_script, filename, output_path, output_path, filename);

            // Execute the command using ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s+"));
            processBuilder.directory(new File("F:\\FinalBuild")); // Set the working directory
            processBuilder.inheritIO(); // Redirect standard I/O to the current process

            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            System.out.println("Upload file, filename is " + filename);

            return exitCode == 0; // Return true if the process exits normally
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }


    @PostMapping("/download/cloth")
    public ResponseEntity<FileSystemResource> downloadCloth(@RequestParam String filename)
    {
        if(filename == null || filename.isEmpty())
            return null;
        File file = Paths.get(uploadclothPathStr).resolve(filename).toFile();
        if(file.exists() && file.canRead())
        {
            System.out.println("download file , filename is "+filename);
            return ResponseEntity.ok()
                    .contentType(file.getName().contains(".jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG)
                    .body(new FileSystemResource(file));
        }
        else
            return null;
    }

    @PostMapping("/download/image")
    public ResponseEntity<FileSystemResource> downloadImage(@RequestParam String filename)
    {
        if(filename == null || filename.isEmpty())
            return null;
        File file = Paths.get(uploadimagePathStr).resolve(filename).toFile();
        if(file.exists() && file.canRead())
        {
            System.out.println("download file , filename is "+filename);
            return ResponseEntity.ok()
                    .contentType(file.getName().contains(".jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG)
                    .body(new FileSystemResource(file));
        }
        else
            return null;
    }
    @PostMapping("/process")
    public ResponseEntity<FileSystemResource> processData(@RequestParam String cloth, @RequestParam String image) {
        if (cloth == null || image == null || cloth.isEmpty() || image.isEmpty()) {
            // Return a bad request response if cloth or image is missing
            return ResponseEntity.badRequest().build();
        }

        try {
            Path clothPath = Paths.get(uploadclothPathStr).resolve(cloth).toAbsolutePath();
            Path imagePath = Paths.get(uploadimagePathStr).resolve(image).toAbsolutePath();

            File clothFile = clothPath.toFile();
            File imageFile = imagePath.toFile();

            if (clothFile.exists() && clothFile.isFile() && imageFile.exists() && imageFile.isFile()) {
                // Construct the command to run your Python script in Conda environment
                String environmentPath = "F:\\Anaconda\\envs\\ladi-viton";
                String output_path = "F:\\Android-SpringBoot-Upload-Download-file-master\\backend\\ProcessedData";
                String python_script = "F:\\FinalBuild\\finalrun3.py";
                clothPath = clothPath.getParent();
                imagePath = imagePath.getParent();
                String command = String.format("conda run -p %s python %s " +
                        "--cloth_name %s --cloth_path %s --img_name %s --img_path %s --output_path %s " +
                        "--output_name my_processed.png",environmentPath, python_script, cloth, clothPath, image, imagePath, output_path);

                // Execute the command using ProcessBuilder
                ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s+"));
                processBuilder.directory(new File("F:\\FinalBuild")); // Set the working directory
                processBuilder.inheritIO(); // Redirect standard I/O to the current process

                Process process = processBuilder.start();
                int exitCode = process.waitFor();

                if (exitCode == 0) {
                    // Return a success response if the script runs successfully
                    File processedImage = new File(output_path, "my_processed.png");
                    return ResponseEntity.ok()
                            .contentType(processedImage.getName().contains(".jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG)
                            .body(new FileSystemResource(processedImage));
                } else {
                    // Return an internal server error response if the script fails
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            } else {
                // Return a not found response if either file does not exist
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Return an internal server error response for other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

