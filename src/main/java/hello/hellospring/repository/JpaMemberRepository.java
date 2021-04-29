package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    //JPA는 엔티티 매니저로 모든 것이 동작.
    // 스프링 부트가 gradle, property에서 JPA관련 설정을 보고 알아서 디비 연결하는 등 필요한 작업들 다 해서 엔티티매니저 넣어줌.
    // 그래서 그냥 인젝션 해주면 됨.
    // pk 기반 아닌 것들은 JPQL 작성해주어야 함
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        //이러면 끝. JPA가 insert 쿼리 만들어서 실행하고 setId까지 다 해줌.
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return  result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        //JPQL 이라는 쿼리 언어. 테이블 대상으로 쿼리를 날리는게 아니라 객체를 대상으로 쿼리를 날림. 그럼 이게 SQL로 번역이 됨
        //엔티티(Member)를 대상으로 쿼리를 날림. 멤버 엔티티를 조회해,
        //객체 자체(첫번째 m)를 셀렉트함.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
