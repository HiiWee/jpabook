package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        // [엔티티 매니저 팩토리] - 생성 (META-INF/persistence.xml에서 가져옴)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        // [엔티티 매니저] - 생성
        EntityManager em = emf.createEntityManager();
        // [트랜잭션] - 획득
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // [트랜잭션] - 시작
            logic(em);  // 비즈니스 로직 실행
            tx.commit(); // [트랜잭션] - 커밋
        } catch (Exception e) {
            tx.rollback();  // [트랜잭션] - 롤백
        } finally {
            em.close(); //  [엔티티 매니저] - 종료
        }
        emf.close();    // [엔티티 매니저 팩토리] - 종료
    }

    // 비즈니스 로직
    private static void logic(EntityManager em) {

        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("호석");
        member.setAge(2);

        // 등록
        em.persist(member);

        // 수정
        member.setAge(20);

        // 한건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

        // 목록 조회 JPQL은 대소문자를 구분하므로 Entity인 Member를 대소문자를 구분하여 작성해야 한다
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        System.out.println("members.size=" + members.size());

        em.remove(member);
    }
}
