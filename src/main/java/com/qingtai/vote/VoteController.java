package com.qingtai.vote;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class VoteController {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VotebundleRepository votebundleRepository;

    @GetMapping("/")
    public String homePage(Principal principal, Model model){
        String username = principal.getName();
        List<Votebundle> votebundles = votebundleRepository.findByVcreator(username);
        model.addAttribute("username", username);
        if(votebundles.isEmpty()){
            votebundles = null;
        }
        Votebundle votebd = new Votebundle();
        model.addAttribute("votebd", votebd);
        model.addAttribute("votebundles", votebundles);
        return "index";
    }

//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }

    @GetMapping("/votes/{vid}")
    // public Iterable<Vote> voteView(@PathVariable("vid") Long vid, Model model)
    public String voteView(@PathVariable("vid") Integer vid, Model model)
    {
        List<Vote> voteDetails = voteRepository.findByVid(vid);
        // List<Vote> voteDetails = voteRepository.findAll();
        // Vote vote1 = voteDetails.get(0);
        model.addAttribute("voteDetails", voteDetails);
        return "voteView";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Vote> getAllVotes()
    {
        return voteRepository.findAll();
    }

    @PostMapping("/create")
    public @ResponseBody String createVote(Principal principal, @RequestParam("item") String[] items, Vote vote, @ModelAttribute(value="votebd") Votebundle votedb)
    {   
        String result = "";
        String username = principal.getName();
        votedb.setVcreator(username);
        Integer vid = votebundleRepository.save(votedb).getVid();
        for(String item : items){
            // String opt= "hello";
            vote.setItem(item);
            vote.setVid(vid);
            vote.setCount(0);
            voteRepository.save(vote);
            vote = new Vote();
            result += item + "\n";
        }
        return result;
    }

    @PostMapping("/collect")
    public @ResponseBody String createVote(@RequestParam("selectedItem") Integer iid)
    {
        Vote itemToAdd  = voteRepository.findByIid(iid);
        int itemCount = itemToAdd.getCount();
        itemCount++;
        itemToAdd.setCount(itemCount);
        voteRepository.saveAndFlush(itemToAdd);
        return "iid:"+iid+"after:"+itemToAdd.getCount();
    }

    public User newUser(){
        User user1 = new User();
        // user1.setId(1);
        user1.setUsername("qingtai");
        user1.setPassword(new BCryptPasswordEncoder().encode("admin"));
        user1.setRole("USER");
        userRepository.save(user1);
        return user1;
    }

    public void newVb(User user){
        Votebundle vb = new Votebundle();
        // user1.setId(1);
        vb.setVname("wife");
        vb.setVcreator(user.getUsername());
        votebundleRepository.save(vb);
    }
    
    @GetMapping("/test")
    public @ResponseBody String getTest() {
        User ut = newUser();
        newVb(ut);
        return "user saved";
    }
    
}