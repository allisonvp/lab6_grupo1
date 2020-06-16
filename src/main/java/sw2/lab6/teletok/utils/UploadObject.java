package sw2.lab6.teletok.utils;

import org.springframework.web.multipart.MultipartFile;
import sw2.lab6.teletok.entity.Post;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class UploadObject {

    public final static int BIGNUMBER = 1749183;
    public final static String PERFIL = "perfil";

    public static String uploadPhoto(String fileObjKeyName,  MultipartFile multipartFile, String folder) throws Exception {

        if(Objects.requireNonNull(multipartFile.getOriginalFilename()).contains("..")) {
            throw new IOException();
        }
        else {
            System.out.println("Paso 1");
            String var;
            if (multipartFile.getOriginalFilename().contains(".jpg")){
                var = ".jpg";
            }
            else if(multipartFile.getOriginalFilename().contains(".png")){
                var = ".png";
            }
            else {var = "";}
            fileObjKeyName = fileObjKeyName + var;
            File file = new File(fileObjKeyName);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();

        }
        return null;

    }

    public static void uploadProfilePhoto(Post u, MultipartFile multipartFile){
        if (multipartFile!=null && !multipartFile.isEmpty()){
            try {
                String name = Integer.toString(u.getId()* BIGNUMBER).hashCode()+Integer.toString(u.getId());

                u.setMediaUrl(UploadObject.uploadPhoto(name,multipartFile, PERFIL));
            }
            catch (Exception ex){
                ex.fillInStackTrace();
            }
        }
    }
}
