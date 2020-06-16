package sw2.lab6.teletok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

//
    @GetMapping(value = {"", "/"})
    public String listPost(){

        postRepository.findAll();
        postCommentRepository.obtenerCantComentPorPost();
        postLikeRepository.obtenerCantLikesPorPost();
        return "post/list";
    }

    @GetMapping("/post/new")
    public String newPost(){
        return "post/new";
    }

    @PostMapping("/post/save")
    public String savePost() {
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
