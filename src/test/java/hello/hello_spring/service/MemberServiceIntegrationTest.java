package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import static org.junit.jupiter.api.Assertions.*;

import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
/* @Transactional 테스트 실행 후 이전 상태로 롤백*/
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;



    /* MemoryMemberRepository의 memberRepository와 다름 (new로 생성) class
    MemoryMemberRepository memberRepository = new MemoryMemberRepository() ;*/
/* @Transactional의 사용으로 BeforeEach 와 AfterEach가 필요하지 않음
//    @BeforeEach
//    public void beforeEach() {
//        memberRepository = new MemoryMemberRepository();
//        memberService = new MemberService(memberRepository);
//    }
//
//    @AfterEach
//    public void afterEach() {
//        memberRepository.clearStore();
//    }
*/

    @Test
    void join() {
        //given 받는 파라미터
        Member member = new Member();
        member.setName("test");

        //when 검증 메서드
        Long saveId = memberService.join(member);

        //then 검증 진행
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void duplicate_member() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
/*
        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
          assertThat(e.getMessage()).isEqualTo("Member already exists");
        }
        memberService.join(member2);
    }
*/

        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

}