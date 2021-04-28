package hello.hellospring.service;
import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

//ㅋㅋㅋ 테스트 케이스 자동완성 해주는 기능이 있네 ^^  커맨드+시프트+T ㅋㅋㅋ 기가 매키네~
public class MemberService {


    //회원서비스를 만들려면 회원 저장소가 있어야함
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member) {

        //중복 회원 검증
        validateDuplicatedName(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 비지니스 로직 중 중복이름 불가가 있다.
     * IfPresent : null이 아니라 어떤 값이 있으면 내부 로직 작동. Optional이 member를 감싼 형태이고, Optional 통해서 여러 메소드 사용 가능.
     * 과거에 if(x === null) 이런 식으로 코딩 했지만 이젠 Optional로 감싸는 것을 추천.
     * orElseGet = '있으면 꺼내고 없으면 이거 실행해' 많이 씀. 있으면 꺼내고 없으면 디폴트로 이거 반환해 라던가.
     * 값을 바로 꺼내는 것을 추천하지 않기 떄문에
     * Optional<Member> result = memberRepository.finaByName(member.getName()).get();
     * result.ifPresent(m -> {
     *       throw new IllegalAccessException("이미 존재하는 회원입니다");
     *   });을 아래와 같이 바꿔준다. 이후 메소드화 (control+t) 해줬음.
     */

    private void validateDuplicatedName(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    //서비스의 클래스들의 네이밍은 비지니스에서 많이 사용하는 용어에 가깝게.
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
