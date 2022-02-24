package server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class SomeController {

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Hello world!";
    }

    @GetMapping("/get")
    @ResponseBody
    public String getIndex() { return "Get Endpoint"; }

    @PostMapping("/post")
    @ResponseBody
    public String postIndex() { return "Post Endpoint"; }

    @DeleteMapping("/delete")
    @ResponseBody
    public String deleteIndex() { return  "Delete Endpoint" ;}

    @PutMapping("/put")
    @ResponseBody
    public String putIndex() { return  "Put Endpoint" ;}
}