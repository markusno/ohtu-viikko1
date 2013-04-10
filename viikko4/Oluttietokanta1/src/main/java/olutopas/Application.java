package olutopas;

import com.avaje.ebean.EbeanServer;
import java.util.List;
import java.util.Scanner;
import javax.persistence.OptimisticLockException;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.model.Pub;
import olutopas.model.Rating;
import olutopas.model.Users;

public class Application {

    private EbeanServer server;
    private Scanner scanner = new Scanner(System.in);
    private Users user;

    public Application(EbeanServer server) {
        this.server = server;
    }

    private void login() {
        while (true) {
            System.out.println("\nLogin (give ? to register a new user)");
            System.out.print("username: ");
            String username = scanner.nextLine();
            if (username.equals("?")) {
                createUser();
            } else {
                if (loginExistingUser(username)) {
                    return;
                }
            }
        }
    }

    private boolean loginExistingUser(String username) {
        user = server.find(Users.class).where().like("username", username).findUnique();
        if (user == null) {
            System.out.println("invalid username");
            return false;
        }
        System.out.println("Welcome " + username);
        return true;
    }

    private void createUser() {
        System.out.println("\nRegister a new user");
        System.out.print("give username: ");
        String username = scanner.nextLine();
        Users newUser = new Users(username);
        server.save(newUser);
        System.out.println("user created");
    }

    public void run(boolean newDatabase) {
        if (newDatabase) {
            seedDatabase();
        }
        System.out.println("Welcome!");
        login();
        while (true) {
            menu();
            System.out.print("> ");
            String command = scanner.nextLine();

            if (command.equals("0")) {
                break;
            } else if (command.equals("1")) {
                findBrewery();
            } else if (command.equals("2")) {
                findBeer();
            } else if (command.equals("3")) {
                addBeer();
            } else if (command.equals("4")) {
                listBreweries();
            } else if (command.equals("5")) {
                deleteBeer();
            } else if (command.equals("6")) {
                listBeers();
            } else if (command.equals("7")) {
                addBrewery();
            } else if (command.equals("8")) {
                deleteBrewery();
            } else if (command.equals("9")) {
                listUsers();
            } else if (command.equals("10")) {
                listRatingsByUser();
                
            } else if (command.equals("11")) {
                addPub();
            } else if (command.equals("12")) {
                addBeerToPub();
            } else if (command.equals("13")) {
                showBeersInPub();
            } else if (command.equals("14")) {
                listPubs();
            } else if (command.equals("15")) {
                removeBeerFromPub();
            } else {
                System.out.println("unknown command");
            }

            System.out.print("\npress enter to continue");
            scanner.nextLine();
        }

        System.out.println("bye");
    }

    private void menu() {
        System.out.println("");
        System.out.println("1   find brewery");
        System.out.println("2   find beer");
        System.out.println("3   add beer");
        System.out.println("4   list breweries");
        System.out.println("5   delete beer");
        System.out.println("6   list beers");
        System.out.println("7   add brewery");
        System.out.println("8   delete brewery");
        System.out.println("9   list users");
        System.out.println("10  my ratings");
        

        System.out.println("11   add pub");
        System.out.println("12   add beer to pub");
        System.out.println("13   show beers in pub");
        System.out.println("14   list pubs");
        System.out.println("15  remove beer from pub");
        System.out.println("0   quit");
        System.out.println("");
    }

    // jos kanta on luotu uudelleen, suoritetaan tämä ja laitetaan kantaan hiukan dataa
    private void seedDatabase() throws OptimisticLockException {
        Brewery brewery = new Brewery("Schlenkerla");
        brewery.addBeer(new Beer("Urbock"));
        brewery.addBeer(new Beer("Lager"));
        // tallettaa myös luodut oluet, sillä Brewery:n OneToMany-mappingiin on määritelty
        // CascadeType.all
        server.save(brewery);

        // luodaan olut ilman panimon asettamista
        Beer b = new Beer("Märzen");
        server.save(b);

        // jotta saamme panimon asetettua, tulee olot lukea uudelleen kannasta
        b = server.find(Beer.class, b.getId());
        brewery = server.find(Brewery.class, brewery.getId());
        brewery.addBeer(b);
        server.save(brewery);

        server.save(new Brewery("Paulaner"));
        Users user = new Users("user");
        server.save(user);
        server.save(new Rating(3, b, user));
        server.save(new Pub("Pikkulintu"));
        
    }
    
    private void printList(List list) {
        for (Object object : list) {
            System.out.println("  " + object);
        }
    }

    private void removeBeerFromPub() {
        System.out.print("pub: ");
        String n = scanner.nextLine();
        Pub foundPub = server.find(Pub.class).where().like("name", n).findUnique();

        if (n == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println(foundPub);
        List<Beer> beers = foundPub.getBeers();
        printList(beers);

        System.out.print("beer to remove: ");
        n = scanner.nextLine();
        for (int i = 0; i < beers.size(); i++) {
            Beer beer = beers.get(i);
            if (beer.getName().equals(n)) {
                beers.remove(beer);
                server.save(foundPub);
                System.out.println(n + " removed");
                return;
            }
        }
        System.out.println(n + " not found");
    }

    private void listPubs() {
        List<Pub> pubs = server.find(Pub.class).findList();
        for (Pub pub : pubs) {
            System.out.println(pub);
            printList(pub.getBeers());
        }
    }

    private void showBeersInPub() {
        System.out.print("pub: ");
        String n = scanner.nextLine();
        Pub foundPub = server.find(Pub.class).where().like("name", n).findUnique();

        if (n == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println(foundPub);

        printList(foundPub.getBeers());
    }
    
    private void listRatingsByUser() {
        List<Rating> ratings = server.find(Rating.class).where()
                .ilike("user.username", user.getUsername()).findList();
        if (ratings.isEmpty()){
            System.out.println("No ratings by " + user);
            return;
        }
        System.out.println("Ratings by " + user);
        for (Rating rating : ratings) {
            System.out.println(rating);
        }
    }
    
    private void ratingsByBeer(Beer beer) {
        List<Rating> ratings = server.find(Rating.class).where()
                .ilike("beer.name", beer.getName()).findList();
        if (ratings.isEmpty()){
            System.out.println("  no ratings");
            return;
        }
        double sum = 0;
        for (Rating rating : ratings) {
            sum += rating.getPoints();
        }
        System.out.println("  ratings given " + ratings.size()
                + " average " + (sum/ratings.size()));
    }

    private void rateBeer(Beer beer) {
        System.out.print("give rating (leave empty if not): ");
        String p = scanner.nextLine();
        if (!p.matches("[0-5]")){
            return;
        }
        Rating rating = new Rating(Integer.parseInt(p), beer, user);
        server.save(rating);
    }

    private void listUsers() {
        List<Users> users = server.find(Users.class).findList();
        for (Users u : users) {
            System.out.println(u);
        }
    }

    private void deleteBrewery() {
        System.out.print("brewery to delete: ");
        String n = scanner.nextLine();
        Brewery breweryToDelete = server.find(Brewery.class).where().like("name", n).findUnique();

        if (breweryToDelete == null) {
            System.out.println(n + " not found");
            return;
        }

        server.delete(breweryToDelete);
        System.out.println("deleted: " + breweryToDelete.getName());

    }

    private void listBeers() {
        System.out.println("");
        List<Beer> beers = server.find(Beer.class).findList();
        for (Beer beer : beers) {
            System.out.println(beer);
            ratingsByBeer(beer);
        }
    }

    private void addBrewery() {
        System.out.print("brewery to add: ");

        String name = scanner.nextLine();

        Brewery brewery = new Brewery(name);
        server.save(brewery);
        System.out.println(" added new brewery: " + name);
    }

    private void findBeer() {
        System.out.print("beer to find: ");
        String n = scanner.nextLine();
        Beer foundBeer = server.find(Beer.class).where().like("name", n).findUnique();

        if (foundBeer == null) {
            System.out.println(n + " not found");
            return;
        }
        System.out.println(foundBeer);
        ratingsByBeer(foundBeer);
        rateBeer(foundBeer);
    }

    private void findBrewery() {
        System.out.print("brewery to find: ");
        String n = scanner.nextLine();
        Brewery foundBrewery = server.find(Brewery.class).where().like("name", n).findUnique();

        if (foundBrewery == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println(foundBrewery);
        for (Beer bier : foundBrewery.getBeers()) {
            System.out.println("   " + bier.getName());
        }
    }

    private void listBreweries() {
        List<Brewery> breweries = server.find(Brewery.class).findList();
        for (Brewery brewery : breweries) {
            System.out.println(brewery);
        }
    }

    private void addBeer() {
        System.out.print("to which brewery: ");
        String name = scanner.nextLine();
        Brewery brewery = server.find(Brewery.class).where().like("name", name).findUnique();

        if (brewery == null) {
            System.out.println(name + " does not exist");
            return;
        }

        System.out.print("beer to add: ");

        name = scanner.nextLine();

        Beer exists = server.find(Beer.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }

        brewery.addBeer(new Beer(name));
        server.save(brewery);
        System.out.println(name + " added to " + brewery.getName());
    }

    private void deleteBeer() {
        System.out.print("beer to delete: ");
        String n = scanner.nextLine();
        Beer beerToDelete = server.find(Beer.class).where().like("name", n).findUnique();

        if (beerToDelete == null) {
            System.out.println(n + " not found");
            return;
        }

        server.delete(beerToDelete);
        System.out.println("deleted: " + beerToDelete);

    }
    
    private void addBeerToPub() {
        System.out.print("beer: ");
        String name = scanner.nextLine();
        Beer beer = server.find(Beer.class).where().like("name", name).findUnique();

        if (beer == null) {
            System.out.println("does not exist");
            return;
        }

        System.out.print("pub: ");
        name = scanner.nextLine();
        Pub pub = server.find(Pub.class).where().like("name", name).findUnique();

        if (pub == null) {
            System.out.println("does not exist");
            return;
        }

        pub.addBeer(beer);
        server.save(pub);
    }
    
    private void addPub() {
        System.out.print("pub to add: ");

        String name = scanner.nextLine();

        Pub exists = server.find(Pub.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }

        server.save(new Pub(name));
    }
}
