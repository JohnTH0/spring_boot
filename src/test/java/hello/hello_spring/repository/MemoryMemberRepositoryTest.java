package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

     MemoryMemberRepository repo = new MemoryMemberRepository();

     @AfterEach
     public void afterEach(){
          repo.clearStore();
     }

     @Test
     public void save() {
          Member member = new Member();
          member.setName("TestSpring");

          repo.save(member);
          Member result = repo.findById(member.getId()).get();
          Assertions.assertEquals(member, result);
          assertThat(member).isEqualTo(result);
     }

     @Test
     public void findByName() {
          Member member1 = new Member();
          member1.setName("TestSpring11");
          repo.save(member1);

          Member member2 = new Member();
          member2.setName("TestSpring22");
          repo.save(member2);

          Member result = repo.findByName("TestSpring11").get();
          assertThat(result).isEqualTo(member1);
     }

     @Test
     public void findAll() {
          Member member1 = new Member();
          member1.setName("TestSpring11");
          repo.save(member1);

          Member member2 = new Member();
          member2.setName("TestSpring22");
          repo.save(member2);

          List<Member> result = repo.findAll();
          assertThat(result).hasSize(2);
          assertThat(result.size()).isEqualTo(2);
     }

}
