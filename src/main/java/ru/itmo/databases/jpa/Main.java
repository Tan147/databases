package ru.itmo.databases.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ru.itmo.databases.jpa.dao.CompetitionDao;
import ru.itmo.databases.jpa.model.Competition;

public class Main {
    public static void main(String[] args){
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("competitions");
        EntityManager manager = factory.createEntityManager();

        Competition competition = new Competition(
                "Соревнование",
                "{\"text\": \"№1\", \"color\": \"white\"}"
        );

        CompetitionDao competitionDao = new CompetitionDao(manager);

        int insertedId = competitionDao.insert(competition);
        System.out.println("insertedId: " + insertedId);

        Competition fromDB = competitionDao.findById(insertedId);
        System.out.println("fromDB: " + fromDB.getTitle());

        fromDB.setTitle("Соревнование №1");

        competitionDao.update(competition);

        manager.close();
        factory.close();
    }
        /*
        manager.getTransaction().begin();
        manager.persist(competition); //менеджер сущностей берет объект под управление (сохраняет у себя в мапе ссылку)
        manager.getTransaction().commit(); //физически будет составл-ся запрос и синхронизироваться с БД
        //manager.getTransaction().rollback(); // это на блок трай-кетч, если что-то пойдет не так, то все транзакции не выполнятся

        Competition fromDB = manager.find(Competition.class, 1); //поиск по уник.идентиф-ру, это поле которое отмечено @ID,
        //тоже берет объект под свое управление

        manager.getTransaction().begin();
        manager.merge(fromDB); //обновление, апдейт запрос, тоже берет под управление объект
        manager.getTransaction().commit();

        manager.remove(competition); //удаление, исключение объекта из управления менеджера
        manager.detach(competition); //отключение, тоже исключение объекта из управления менеджера

         */
}
