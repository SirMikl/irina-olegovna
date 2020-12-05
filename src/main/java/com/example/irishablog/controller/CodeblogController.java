package com.example.irishablog.controller;

import com.example.irishablog.model.Post;
import com.example.irishablog.repository.CodeblogRepository;
import com.example.irishablog.service.CodeblogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class CodeblogController {

    @Autowired
    CodeblogService codeblogService;

    @Autowired
    CodeblogRepository codeblogRepository;

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public ModelAndView getPosts(){
        ModelAndView mv = new ModelAndView("posts");
        List<Post> posts = codeblogService.findAll();
        Collections.reverse(posts);
        mv.addObject("posts", posts);
        return mv;
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public ModelAndView getPostDetails(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView("postDetails");
        Post post = codeblogService.findById(id);
        mv.addObject("post", post);
        return mv;
    }

    @RequestMapping(value = "/posts/{id}/edit", method = RequestMethod.GET)
    public ModelAndView postEdit(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView("postEdit");
        Post post = codeblogService.findById(id);
        mv.addObject("post", post);
        return mv;
    }

    @RequestMapping(value = "/posts/{id}/edit", method = RequestMethod.POST)
    public String postUpdate(@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam String author,
                             @RequestParam String text, Model model){
        Post post = codeblogRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAuthor(author);
        //post.setDate(LocalDate.now());
        post.setText(text);
        codeblogRepository.save(post);
        return "redirect:/posts";
    }

    @PostMapping("/posts/{id}/remove")
    public String postDelete(@PathVariable(value = "id") Long id, Model model){
        Post post = codeblogRepository.findById(id).orElseThrow();
        codeblogRepository.delete(post);
        return "redirect:/posts";
    }

    @RequestMapping(value = "/newpost", method = RequestMethod.GET)
    public String getPostForm(){
        return "postForm";
    }

    @RequestMapping(value = "/newpost", method = RequestMethod.POST)
    public String savePost(@Valid Post post, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            attributes.addFlashAttribute("message", "Please make sure the required fields are filled in!");
            return "redirect:/newpost";
        }
        post.setDate(LocalDate.now());
        codeblogService.save(post);
        return "redirect:/posts";
    }



}
