package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;
    /*
    멤버정보 저장은 MemoryMemberRepository에서 해쉬맵 형태로 하고 있다. 그래서 메모리 클리어 하려면 MemoryMemberRepository를 클리어해야 하는데
    여기서 애매한게 MemberService 안에서 memberRepository = new MemoryMemberRepository(); 하고 있는데 이것과
    테스트코드에서 하는 MemoryMemberRepository repository = new MemoryMemberRepository(); 이것과 다름
    두개를 쓸 이유가 없고 테스트케이스 내에서 하나의 repository만 써야함.
    MemoryMemberRepository 안의 store가 static으로 선언되어 있고. static은 인스턴스와 상관없이 클래스 레벨에서 붙기 때문에 지금은 괜찮지만 좋은 방법이 아니다.
    문제는 MemberService 클래스에서 new MemoryMemberRepository()와 테스트케이스에서 만든 MemoryMemberRepository()가 서로 다른 repository  instance라는 것이다.
    만약 MemoryMemberRepository에서 store가 static이 아니면 문제가 발생.
    암튼 같은 store로 테스트하는게 맞는 건데 서로 다른 store(static이긴 하지만)로 테스트 진행 하는건 문제.
    그래서 같은 인스턴스를 쓰게 할려면 MemberService에서 MemberRepository를 new MemoryMemberRepository로 생성하게 하는 것이 아니라
    constructor를 사용해 MemberRepository를 매개변수로 전달받게 하고 그것을 MemberRepository에 할당하게 하면 됨,
    그리고 테스트코드 내 beforeEach()에 repository에 new MemoryMemberRepository 할당해주면 된다.
    MemberService 입장에서 memberRepository를 외부에서 넣어준다. 이를 dependency injection 이라고 부른다.
     */

    @BeforeEach
    public void beforeEach() {
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }

    @AfterEach
    public void afterEach(){
        memoryMemberRepository.clearStore();
    }
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

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}