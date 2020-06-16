package sw2.lab6.teletok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sw2.lab6.teletok.entity.Post;
import sw2.lab6.teletok.entity.User;
import sw2.lab6.teletok.repository.PostRepository;
import sw2.lab6.teletok.utils.UploadObject;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import sw2.lab6.teletok.entity.Post;
import sw2.lab6.teletok.repository.PostCommentRepository;
import sw2.lab6.teletok.repository.PostLikeRepository;
import sw2.lab6.teletok.repository.PostRepository;

import java.util.Optional;
import sw2.lab6.teletok.entity.Post;
import sw2.lab6.teletok.entity.PostComment;
import sw2.lab6.teletok.entity.PostLike;
import sw2.lab6.teletok.repository.PostCommentRepository;
import sw2.lab6.teletok.repository.PostLikeRepository;
import sw2.lab6.teletok.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostCommentRepository postCommentRepository;
    @Autowired
    PostLikeRepository postLikeRepository;




    @GetMapping(value = {"", "/"})
    public String listPost(){

        postRepository.findAll();
        postCommentRepository.obtenerCantComentPorPost();
        postLikeRepository.obtenerCantLikesPorPost();


        return "post/list";
    }

    @GetMapping("/post/new")
    public String newPost(@ModelAttribute("post") Post post) {
        return "post/new ";
    }

    @PostMapping("/post/save")
    public String savePost(@ModelAttribute("post") @Valid Post post,
                           BindingResult bindingResult,
                           RedirectAttributes attr,
                           @RequestParam("user") int user,
                           @RequestParam("photo") MultipartFile multipartFile) {

        if (!post.getDescription().isEmpty()) {
            if (post.getDescription().trim().length() < 3) {
                bindingResult.rejectValue("nombre", "error.user", "La descripción debe tener más de 3 letras.");
            }
            if (post.getDescription().trim().length() > 45) {
                bindingResult.rejectValue("nombre", "error.user", "La descripción debe tener menos de 45 letras.");
            }
            if (post.getDescription().trim().length() == 0) {
                bindingResult.rejectValue("nombre", "error.user", "Ingrese una descripción.");
            }
        }

        if (post.getMediaUrl().isEmpty()) {
            bindingResult.rejectValue("mediaUrl", "error.user", "Debe colocar una foto.");
        }


        if (bindingResult.hasErrors()) {
            return "post/new";
        } else if (post.getId() == 0) {
            post.setCreationDate(LocalDateTime.now());
            post.getUser().setId(user);
            UploadObject.uploadProfilePhoto(post,multipartFile);
            attr.addFlashAttribute("msg", "Post creado exitosamente");
            postRepository.save(post);
        }
        return "redirect:/";
    }

    @GetMapping("/post/file/{media_url}")
    public String getFile() {
        return "";
    }

    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable("id") int id, Model m) {
        Optional<Post> opt = postRepository.findById(id);
        if (opt.isPresent()){
            m.addAttribute("post",opt.get());
            List<PostComment> comments = postCommentRepository.findPostCommentByPost(opt.get());
            m.addAttribute("comments",comments);
            List<PostLike> likes = postLikeRepository.findPostLikeByPost(opt.get());
            m.addAttribute("likes",likes);

        }else{
            m.addAttribute("msgError","Ups! este post no existe :(");
        }
        return "post/view";
    }

    @PostMapping("/post/comment")
    public String postComment() { return "";
    }

    @PostMapping("/post/like")
    public String postLike() {
        return "";
    }
}
