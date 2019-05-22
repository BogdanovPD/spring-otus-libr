package ru.otus.spring.libr.shell;

//import org.springframework.shell.standard.ShellComponent;
//import org.springframework.shell.standard.ShellMethod;
//import org.springframework.shell.standard.ShellOption;
//import javax.validation.constraints.Size;

//@ShellComponent
//@RequiredArgsConstructor
//@Slf4j
public class ShellCommands {

//    private final LibrService librService;
//    private final String all = "all";
//
//    @ShellMethod("Create new book")
//    public String newBook(
//            @ShellOption(value = "-a", help = "Book's Author Name") @Size(max = 255) String author,
//            @ShellOption(value = "-n", help = "Book's Name") @Size(max = 255) String name,
//            @ShellOption(value = "-g", help = "Book's Genre Name") @Size(max = 255) String genre) {
//        Optional<Book> book = librService.newBook(name, genre, author);
//        if (book.isEmpty()) {
//            log.error("Book hasn't been saved");
//            return "";
//        }
//        return book.get().toString();
//    }
//
//    @ShellMethod("Select books")
//    public List<Book> selectBooks(
//            @ShellOption(value = "-a", help = "Author to filter by", defaultValue = "all")
//            @Size(max = 255) String author,
//            @ShellOption(value = "-n", help = "Name to filter by (ignores other filters)", defaultValue = "all")
//            @Size(max = 255) String name,
//            @ShellOption(value = "-g", help = "Genre to filter by", defaultValue = "all")
//            @Size(max = 255) String genre
//    ) {
//        if (all.equals(name) && all.equals(author) && all.equals(genre)) {
//            return librService.getAllBooks();
//        }
//        if (all.equals(genre)) {
//            if (all.equals(author)) {
//                return librService.getBooksByName(name);
//            }
//            if (all.equals(name)) {
//                return librService.getBooksByAuthor(author);
//            }
//            return librService.getBooksByNameAndAuthor(name, author);
//        }
//        if (all.equals(author)) {
//            if (all.equals(name)) {
//                return librService.getBooksByGenre(genre);
//            }
//            return librService.getBooksByNameAndGenre(name, genre);
//        }
//        if (all.equals(name)) {
//            return librService.getBooksByAuthorAndGenre(author, genre);
//        }
//        return librService.getBooksByNameAndAuthorAndGenre(name, author, genre);
//    }
//
//    @ShellMethod("Select authors")
//    public Set<String> selectAuthors() {
//        return librService.getAllBooks().stream()
//                .map(Book::getAuthor)
//                .collect(Collectors.toSet());
//    }
//
//    @ShellMethod("Select authors")
//    public Set<String> selectGenres() {
//        return librService.getAllBooks().stream()
//                .map(Book::getGenre)
//                .collect(Collectors.toSet());
//    }
//
//    @ShellMethod("Add new comment to the book")
//    public String newComment(
//            @ShellOption(value = "-n", help = "Book's name") String name,
//            @ShellOption(value = "-a", help = "Author") String author,
//            @ShellOption(value = "-c", help = "Comment") String comment
//    ) {
//        if (comment.isBlank()) {
//            log.error("Error! Comment is blank");
//            return "";
//        }
//        List<Book> books = librService.getBooksByNameAndAuthor(name, author);
//        if (books.isEmpty()) {
//            log.error("There is no books with provided name/author");
//            return "";
//        }
//        Book book = books.get(0);
//        book.getComments().add(comment);
//        librService.saveBook(book);
//        return "Added!";
//    }
//
//    @ShellMethod("Select all comments by book")
//    public List<String> selectComments(
//            @ShellOption(value = "-n", help = "Book's name") String name,
//            @ShellOption(value = "-a", help = "Author") String author
//    ) {
//        List<Book> books = librService.getBooksByNameAndAuthor(name, author);
//        if (books.isEmpty()) {
//            log.error("There is no books with provided name/author");
//            return Collections.emptyList();
//        }
//        return books.get(0).getComments();
//    }

}