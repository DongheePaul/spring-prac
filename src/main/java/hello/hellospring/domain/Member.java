package hello.hellospring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//아래 어노테이션 붙는 순간 JPA에서 관리하는 엔티티가 되는 것
@Entity
public class Member {
                        //디비가 아이디를 자동으로 생성하는 것을 IDENTITY 전략이라고 한다
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

