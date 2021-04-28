package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    //멤버컨트롤러가 @Controller로 인해 생성되어 컨테이너에 등록이 되었을 것. @Autowired 생성자를 보면 스프링이 컨테이너에 있는 MemberService를 찾아서 연결시켜줌.
    //스프링이 MemberService를 알 수 있도록 어노테이션 달아줘야함. 스프링이 DI 해준다.
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    //url 직접 입력 등 조회할 떄는 get
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    //form태그를 사용하거나 해서 데이터를 전달할 때 주로 Post
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        System.out.println("member = " + member.getName());
        memberService.join(member);
        return "redirect:/";
    }
}
