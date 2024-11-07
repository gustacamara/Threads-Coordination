import java.util.ArrayList;

public class Coordination {
    public static void main(String[] args) {
        Library library = new Library();
        ArrayList<Book> books = new ArrayList<>(10);
        books.add(new Book(0, "Infinity Edge"));
        books.add(new Book(1, "Rabadon's Deathcap"));    
        books.add(new Book(2, "Thornmail"));
        books.add(new Book(3, "Sunfire Cape"));
        books.add(new Book(4, "Zhonya's Hourglass"));
        books.add(new Book(5, "Luden's Echo"));
        books.add(new Book(6, "Trinity Force"));
        books.add(new Book(7, "Guardian Angel"));
        books.add(new Book(8, "Warmog's Armor"));
        books.add(new Book(9, "Blade of the Ruined King"));
        library.setBooks(books);
            

        User ana = new User("Ana", books);
        User giovanni = new User("Giovanni", books);

        ana.setIsReadingBook(library.borrowBook(4));
        System.out.println("Ana is borrowing a book: " + ana.getIsReading().getTitle());
    }    
}
    
class User extends Thread {
    private final ArrayList<Book> booksToChoose;
    private String name;
    private Book isReading;

    public User(String name, ArrayList<Book> booksToChoose) {
        this.name = name;
        isReading = null;
        this.booksToChoose = booksToChoose;
    }

    public void setIsReadingBook(Book book) {
        isReading = book;
    }

    @Override
    public void run() {
        
    }

    public Book getIsReading() {
        return isReading;
    }
}
        

// class Library {
//     private static ArrayList<Book> books = new ArrayList<>(10);

//     public void setBooks(ArrayList<Book> books) {
//         Library.books = books;
//     }
//     public static Book getBook(int libraryId) {
//         return books.get(libraryId);
//     }

//     public boolean isBorrowed(Book book) {
//         if (book.isWithUser()) {
//             return true;
//         } else {
//             return false;
//         }
//     }

//     public Book borrowBook(int libraryId) {
//         Book book = books.get(libraryId);
//         if (!isBorrowed(book)) {
//             return null;
//         } else {
//             return book;
//         }
//     }
// }

class Book {
    private  int libraryId;
    private String title;
    private boolean withUser;

    public Book(int libraryId, String title) {
        this.libraryId = libraryId;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public boolean isWithUser() {
        return withUser;
    }

    public int getLibraryId() {
        return libraryId;
    }
}