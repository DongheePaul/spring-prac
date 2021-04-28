package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;


public class MemoryMemberRepository  implements MemberRepository{
    //현업에서는 동시성 문제 떄문에 concurrent Hashmap 써야 함
    private static Map<Long, Member> store = new HashMap<>();
    //sequence는 0, 1, 2 등 키값을 생성해주는 애. 얘도 실무에서는 long보단 동시성 문제 고려해서 atomLong? 써야함.
    private static long sequence = 0L;
    @Override
    public Member save(Member member) {
        //id를 set 해주고
        member.setId(++sequence);
        //이름은 넘어온 상태. 클래스로 객체 만들 때 주어졌을 것.
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //과거에는 아래처럼 처리함.
        //return store.get(id);
        //현재는 옵셔널로 감싼다. null & optional 이면 클라이언트에서 처리할 수 있는 방법이 있다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        //store를 루프 돌려서. filter(람다)를 통해 파라미터로 넘어온 name과 같은 값이 있는지 확인.
        //findAny() : 하나라도 찾으면 바로 Optional로 반환됨.
        //끝까지 찾았는데 없으면 optional에 null이 포함되어 반환됨.
        return store.values().stream()
                .filter(member -> member.getName().equals((name)))
                .findAny();
    }


    @Override
    public List<Member> findAll() {
        //실무에서 List 많이 쓴다. 루프문 돌리기 편해서.
        //store.values() -> Member.
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }

}
