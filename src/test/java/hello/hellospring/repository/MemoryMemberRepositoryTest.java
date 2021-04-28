package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

//테스트 순서 보장 안 된다. 그래서 순서 보장 안 되는 것을 염두에 두고 테스트 설계를 해야함. 기본적으로 하나의 테스트가 끝나면 공용 데이터를 클리어 해줘야함.
class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    //각 테스트 케이스 끝날 떄마다 작동하는 메소드. 콜백 메소드라고 보면 됨.
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        //반환타입 Optional에서 값을 꺼내기 위해 마지막에 .get() 해준다. 좋은 방법은 아니지만 테스트코드에선 괜찮다.
        Member result = repository.findById(member.getId()).get();
        //Assertions.assertEquals(result, member);
        //요즘에 많이 쓰는거. member가 result와 똑같다. static import 가능하다. option+엔터
        assertThat(member).isEqualTo(result);
    }
    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);
        //option+command+v = 반환값 자동생성.
        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
