package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    // /hello 로 들어오는 get메소드를 연결해줌.
    @GetMapping("hello")
    //스프링이 Model을 만들어서 넣어줌.
    public String hello(Model model){
        // 키는 data, 값은 hello spring
        model.addAttribute("data", "어 이제 값이 바뀌나봄 ㅎ");
        // resources:templates/ {ViewName} + .html  즉 ViewName("hello")을 리턴해주면 ViewResolver가
        // resources의 templates에서 ViewName.html을 찾아 "data"를 넘기고 해당 페이지를 실행시킴.
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name") String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    //객체를 만들어서 리턴한다
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    //static 키워드를 붙이면 사용가능. HelloController.Hello 형식으로.
    static class Hello {
        //return 할 json 객체의 key.
        private String name;

        //getter setter = Java bin 방식. private 한 변수에 접근할 수 있는 메소드를 퍼블릭하게 열어 놓음.
        // 이러한 방식을 property 접근 방식이라고도 한다.
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
