package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    //회원이 저장소에 저장됨.
    Member save(Member member);
    //Optional 만약 값이 없을 경우 null을 반환할텐데, 요즘엔 그냥 null을 반환하기보다 Optional로 감싸는 것을 선호. Java8에 들어갔음.
    //저장소 저장된 회원을 찾는 메소드들.
    Optional<Member> findById (Long id);
    Optional<Member> findByName (String name);
    List<Member> findAll();
}
