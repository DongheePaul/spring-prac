package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
//아래 어노테이션이 달리면 트랜잭션이 끝나고 테스트 끝나면 자동으로 롤백함. ㅋㅋ 아예 반영을 안한다는게 더 정확하다고 함. 메소드마다 적용됨.
@Transactional
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    /**
     * given: 이것들이 주어지고
     * when: 이것을 실행했을 때
     * then: 이렇게 되어야 한다
     */
    void join() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long resultId = memberService.join(member);

        //then
        Member result = memberService.findOne(resultId).get();
        assertThat(member.getName()).isEqualTo(result.getName());
    }

    //테스트는 정상작동도 중요하지만 예외 체크가 훨씬 중요하다.
    @Test
    public void 중복회원예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
/*        try {
            memberService.join(member2);

        }catch (IllegalAccessError e){
            assertThat(e.getMessage()).isEqualTo("This user already exist");
        }
        근데 이것 때문에 트라이-캐치 쓰는 건 좀 애매하다. 이 경우 사용하기 좋은 문법을 살펴보자*/

        //memberService.join(member1) 할 떄 IllegalStateException이 발생해야 한다.
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


    }

}