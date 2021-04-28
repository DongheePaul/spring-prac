package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
public class SpringConfig {
    @Bean
    public MemberService memberService() {
                                //스프링빈에 등록된 memberRepository를 등록해준다
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        //인터페이스는 new가 안 되므로 구현체인 MemoryMemberRepository를 new 한다.
        return new MemoryMemberRepository();
    }
}
