package ru.itmo.databases;

public class Databases {
    public static void main(String[] args) {
        System.out.println("Databases Lessons");

        Author author01 = new Author();
        author01.setUniqueName("aut001");

        Author author02 = new Author();
        author02.setUniqueName("aut002");

        AuthorDao authorDao = new AuthorDao();
        authorDao.createTable();
        //authorDao.insert(author01);
        //authorDao.insert(author02);

        Author authorFromDB = authorDao.getByUniqueName("aut001");
        System.out.println(authorFromDB.getId());
        System.out.println(authorFromDB.getRegisteredAt());

        NotesDao notesDao = new NotesDao();
        notesDao.create();




    }

}
