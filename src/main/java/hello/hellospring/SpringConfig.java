package hello.hellospring;

import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class SpringConfig {

    /*private DataSource dataSource;
    스프링부트가 application.properties에 작성한 datasource 설정을 보고 여기에 주입함.
    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    @PersistenceContext
    private EntityManager em;
    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public MemberService memberService() {
                                //스프링빈에 등록된 memberRepository를 등록해준다
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        //인터페이스는 new가 안 되므로 구현체인 MemoryMemberRepository를 new 한다.
        //return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);
        //return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }
}
