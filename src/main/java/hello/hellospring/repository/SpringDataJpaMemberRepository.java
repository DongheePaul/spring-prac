package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//인터페이스가 인터페이스 받을 떄는 extends 씀
//인터페이스는 다중 상속이 된다
//Spirng data JPA가 JpaRepository를 상속하고 있으면 SpringDataJpaMemberRepository 구현체를 자동으로 스프링 빈에 등록해준다.
 public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    @Override
    Optional<Member> findByName(String name);
}
