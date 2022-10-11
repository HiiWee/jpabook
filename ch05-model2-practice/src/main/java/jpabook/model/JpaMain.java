package jpabook.model;

import jpabook.model.entity.Member;
import jpabook.model.entity.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("jpabook");
        em = emf.createEntityManager();

        EntityTransaction tr = em.getTransaction();

        try {
            tr.begin();

            logic2(em);

            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void logic(EntityManager em) {
        Member member = new Member();
        member.setName("호석");
        member.setCity("천안");
        member.setStreet("103");
        member.setZipcode("11234");

        em.persist(member);
        System.out.println(member.getId());
        System.out.println(member.getName() + " " + member.getCity() + " " + member.getStreet() + " " + member.getZipcode());

        Member findMember = em.find(Member.class, member.getId());
        System.out.println(member == findMember);
        em.remove(member);
    }

    public static void logic2(EntityManager em) {
        Member member = new Member();
        Order order = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        em.persist(member);
        em.persist(order);
        em.persist(order2);
        em.persist(order3);
        order.setMember(member);
        order2.setMember(member);
        order3.setMember(member);
    }
}