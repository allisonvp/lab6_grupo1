package sw2.lab6.teletok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sw2.lab6.teletok.entity.Post;
import sw2.lab6.teletok.repository.PostCommentRepository;
import sw2.lab6.teletok.repository.PostLikeRepository;
import sw2.lab6.teletok.repository.PostRepository;

import java.util.Optional;
import sw2.lab6.teletok.entity.Post;
import sw2.lab6.teletok.entity.PostComment;
import sw2.lab6.teletok.entity.PostLike;
import sw2.lab6.teletok.entity.User;
import sw2.lab6.teletok.repository.PostCommentRepository;
import sw2.lab6.teletok.repository.PostLikeRepository;
import sw2.lab6.teletok.repository.PostRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
    public String viewPost(@PathVariable("id") int id, Model m, @ModelAttribute("comment") PostComment comment,@ModelAttribute("like") PostLike like, HttpSession session) {
        Optional<Post> opt = postRepository.findById(id);
        if (opt.isPresent()){

            m.addAttribute("post",opt.get());

            List<PostComment> comments = postCommentRepository.findPostCommentByPost(opt.get());
            m.addAttribute("comments",comments);
            List<PostLike> likes = postLikeRepository.findPostLikeByPost(opt.get());
            List<PostLike> likespropios= postLikeRepository.findPostLikeByPostAndUser(opt.get(), (User) session.getAttribute("user"));
            int can;
            if (likespropios.isEmpty()){
                can=1;
            }else{
                can=0;
            }

            m.addAttribute("likes",likes);
            m.addAttribute("can",can);

        }else{
            m.addAttribute("msgError","Ups! este post no existe :(");
        }
        return "post/view";
    }

    @PostMapping("/post/comment")
    public String postComment(@ModelAttribute("comment") @Valid PostComment comment,
                              BindingResult bindingResult,Model m) {

        if (bindingResult.hasErrors()) {
            m.addAttribute("post",comment.getPost());
            List<PostComment> comments = postCommentRepository.findPostCommentByPost(comment.getPost());
            m.addAttribute("comments",comments);
            List<PostLike> likes = postLikeRepository.findPostLikeByPost(comment.getPost());
            m.addAttribute("likes",likes);
            return "post/view";
        }else{

            postCommentRepository.save(comment);
        }
        return "redirect:/post/1";
    }

    @PostMapping("/post/like")
    public String postLike(@ModelAttribute("like") PostLike like) {
        postLikeRepository.save(like);
        return "redirect:/post/1";
    }
}
