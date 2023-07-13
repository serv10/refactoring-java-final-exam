import java.util.HashMap;

public class RentalInfo {

  public String statement(Customer customer) {
    String[] codeMovies = { "regular", "childrens", "new" };
    HashMap<String, Movie> movies = initializeMovies(codeMovies);
  
    double totalAmount = 0;
    int frequentEnterPoints = 0;
    StringBuilder result = new StringBuilder("Rental Record for ").append(customer.getName()).append("\n");
  
    for (MovieRental movieRental : customer.getRentals()) {
      double thisAmount = calculateAmountForRental(movieRental, movies, codeMovies);
      frequentEnterPoints += calculateFrequentEnterPoints(movieRental, movies, codeMovies);
  
      result.append("\t").append(movies.get(movieRental.getMovieId()).getTitle()).append("\t").append(thisAmount).append("\n");
      totalAmount += thisAmount;
    }
  
    // add footer lines
    result.append("Amount owed is ").append(totalAmount).append("\n");
    result.append("You earned ").append(frequentEnterPoints).append(" frequent points\n");
  
    return result.toString();
  }
  
  private HashMap<String, Movie> initializeMovies(String[] codeMovies) {
    HashMap<String, Movie> movies = new HashMap<>();
    
    movies.put("F001", new Movie("You've Got Mail", codeMovies[0]));
    movies.put("F002", new Movie("Matrix", codeMovies[0]));
    movies.put("F003", new Movie("Cars", codeMovies[1]));
    movies.put("F004", new Movie("Fast & Furious X", codeMovies[2]));

    return movies;
  }
  
  private double calculateAmountForRental(MovieRental movieRental, HashMap<String, Movie> movies, String[] codeMovies) {
    Movie movie = movies.get(movieRental.getMovieId());
    double thisAmount = 0;
  
    if (movie.getCode().equals(codeMovies[0])) {
      thisAmount = 2;
      if (movieRental.getDays() > 2)
        thisAmount += (movieRental.getDays() - 2) * 1.5;
    } else if (movie.getCode().equals(codeMovies[2])) 
      thisAmount = movieRental.getDays() * 3.0;
    else if (movie.getCode().equals(codeMovies[1])) {
      thisAmount = 1.5;
      if (movieRental.getDays() > 3) 
        thisAmount += (movieRental.getDays() - 3) * 1.5;
    }
  
    return thisAmount;
  }
  
  private int calculateFrequentEnterPoints(MovieRental rental, HashMap<String, Movie> movies, String[] codeMovies) {
    Movie movie = movies.get(rental.getMovieId());
    int frequentEnterPoints = 1;
  
    if (movie.getCode().equals(codeMovies[2]) && rental.getDays() > 2) 
      frequentEnterPoints++;
  
    return frequentEnterPoints;
  }
}
