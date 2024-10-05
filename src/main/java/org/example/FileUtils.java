package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtils {
  
  public static void zipDirectory(String sourceDirPath, String zipFilePath) throws IOException {
    Path zipPath = Paths.get(zipFilePath);
    
    try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()))) {
      Path sourceDir = Paths.get(sourceDirPath);
      
      Files.walk(sourceDir).filter(path -> !Files.isDirectory(path)).forEach(path -> {
        ZipEntry zipEntry = new ZipEntry(sourceDir.relativize(path).toString());
        try {
          zos.putNextEntry(zipEntry);
          Files.copy(path, zos);
          zos.closeEntry();
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    }
  }
  
  public static void unzip(String zipFilePath, String destDirPath) throws IOException {
    File destDir = new File(destDirPath);
    if (!destDir.exists()) {
      destDir.mkdir();
    }
    
    try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
      ZipEntry zipEntry;
      
      while ((zipEntry = zis.getNextEntry()) != null) {
        File newFile = new File(destDirPath, zipEntry.getName());
        
        if (zipEntry.isDirectory()) {
          newFile.mkdirs();
        } else {
          new File(newFile.getParent()).mkdirs();
          
          try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile))) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = zis.read(buffer)) > 0) {
              bos.write(buffer, 0, len);
            }
          }
        }
        zis.closeEntry();
      }
    }
  }
  
  public static void copyFile(String sourceFile, String destinationFile) {
    Path sourcePath = Paths.get(sourceFile);
    Path destinationPath = Paths.get(destinationFile);
    
    try {
      Files.copy(sourcePath, destinationPath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static void deleteDirectory(String directory) throws IOException {
    Path directoryPath = Paths.get(directory);
    
    Files.walk(directoryPath)
      .sorted(Comparator.reverseOrder())
      .forEach(path -> {
        try {
          Files.delete(path);
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
  }
}
