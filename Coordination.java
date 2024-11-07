import java.util.ArrayList;
import java.util.Random;

public class Coordination {
    public static void main(String[] args) {
        ArrayList<Book> library = new ArrayList<>(10);
        library.add(new Book(0, "Infinity Edge"));
        library.add(new Book(1, "Rabadon's Deathcap"));    
        library.add(new Book(2, "Thornmail"));
        library.add(new Book(3, "Sunfire Cape"));
        library.add(new Book(4, "Zhonya's Hourglass"));
        library.add(new Book(5, "Luden's Echo"));
        library.add(new Book(6, "Trinity Force"));
        library.add(new Book(7, "Guardian Angel"));
        library.add(new Book(8, "Warmog's Armor"));
        library.add(new Book(9, "Blade of the Ruined King"));
            
        User gusta = new User("Gusta", library);
        User ana = new User("Ana", library);
        User giovanni = new User("Giovanni", library);
        
        gusta.start();
        ana.start();
        giovanni.start();
        
    }    
}
    
class User extends Thread {
    private final ArrayList<Book> booksToChoose;
    private String myName;
    private Book isReading;

    @Override
    public void run(){
        while (true) { 
            Book book = chooseRandomBook();

            if (this.getIsReading() == null){
                if (!book.isWithUser()){

                    synchronized (chooseRandomBook()) {
                        this.setIsReadingBook(book);
                        book.setWithUser(true);
    
                        booksToChoose.set(book.getLibraryId(), book);
                        System.out.printf("O usuário %s emprestou: %s\n",
                            this.getMyname(), 
                            book.getTitle()
                        );
                        this.randomSleep();
                    }
                    
                } else {
                    System.out.printf("O usuário %s está esperando o livro %s ficar disponivel\n",
                        this.getMyname(),
                        book.getTitle()
                    );

                    synchronized (booksToChoose) {
                        try{
                            while (true) { 
                                booksToChoose.wait();
                                if (!book.isWithUser()){break;}
                                else{
                                    System.out.printf("\t\t\tO usuário %s está esperando o livro %s ficar disponivel\n",
                                        this.getMyname(),
                                        book.getTitle()
                                    );
                                    this.randomSleep();
                                }
                            }
                        } catch( InterruptedException e ){
                            System.out.println("Erro aqui");
                        }
                        this.setIsReadingBook(book);
                        book.setWithUser(true);
                        System.out.printf("O usuário %s emprestou: %s\n",
                            this.getMyname(), 
                            book.getTitle()
                        );
                        booksToChoose.notify();
                        this.randomSleep();
                    }
                }
            
            } else {
                synchronized (booksToChoose) {
                    this.isReading.setWithUser(false);
                    book = this.getIsReading();
                    booksToChoose.set(this.isReading.getLibraryId(), book);
                    this.returnBook();
                    System.out.printf("O usuário %s devolveu o livro %s\n", this.getMyname(), book.getTitle());
                    booksToChoose.notify();
                }
                this.randomSleep();
            }
        }
   
    }
    
    public Book chooseRandomBook(){
        Random randBook = new Random();
        Book book =  booksToChoose.get(randBook.nextInt(10));
        return book;
    }

    public void randomSleep() {
        Random timerand = new Random();
        try{
            Thread.sleep(timerand.nextInt(1000, 2000));
        }catch(InterruptedException e){
            System.out.println("Parei meu nego");
        }
    }
    
    public User(String name, ArrayList<Book> booksToChoose) {
        this.myName = name;
        isReading = null;
        this.booksToChoose = booksToChoose;
    }
    public void returnBook(){ this.isReading = null; }
    public void setIsReadingBook(Book book) { isReading = book; }
    public Book getIsReading() { return this.isReading; }
    public String getMyname() { return this.myName; }

}

class Book {
    private  int libraryId;
    private String title;
    private boolean withUser;

    public Book(int libraryId, String title) {
        this.libraryId = libraryId;
        this.title = title;
        this.withUser = false;
    }

    public String getTitle() { return title; }

    public boolean isWithUser() { return withUser; }

    public int getLibraryId() { return libraryId; }

    public void setWithUser(boolean withUser) { this.withUser = withUser; }
}