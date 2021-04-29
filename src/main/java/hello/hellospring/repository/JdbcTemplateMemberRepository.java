package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    //생성자가 딱 하나일 경우 @Autowired 생략 가능
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        /*RowMapper interface 구현을 통해 SQL의 결과(record type)를 객체(object type)에 매핑하여 결과를 리턴한다.
        개발자는 mapRow()라는 interface method를 정의하여 결과를 처리한다.
        한 번만 사용하는 기능의 경우 RowMapper를 익명 클래스로 작성하여 사용한다.*/
        List<Member> result = jdbcTemplate.query("select * from MEMBER where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from MEMBER where name = ?", memberRowMapper(), name);
        return result.stream().findAny();    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from MEMBER", memberRowMapper());
    }

    //객체 생성에 관한건 여기서 콜백으로 정리됨
    private RowMapper<Member> memberRowMapper(){
        //resultSet 결과를 member 객체에 매핑한 다음 리턴시킴
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
