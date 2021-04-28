package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    //멤버컨트롤러가 @Controller로 인해 생성되어 컨테이너에 등록이 되었을 것. @Autowired 생성자를 보면 스프링이 컨테이너에 있는 MemberService를 찾아서 연결시켜줌.
    //스프링이 MemberService를 알 수 있도록 어노테이션 달아줘야함. 스프링이 DI 해준다.
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
}
