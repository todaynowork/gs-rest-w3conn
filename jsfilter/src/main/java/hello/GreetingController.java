package hello;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
    
    @RequestMapping("/dojolib/{jsFileName}")
    public String getDojoJS(@PathVariable String jsFileName ) {
//        model.addAttribute("name", name);
        return "greeting";
    }
    
    @RequestMapping("/listfiles")
    public String listFiles(Model model) {
        model.addAttribute("files", ConsoleOutputRecord.dumpFiles().keySet());
        return "listfiles";
    }
}
