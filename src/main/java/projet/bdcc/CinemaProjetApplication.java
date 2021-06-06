package projet.bdcc;


import projet.bdcc.entities.Film;

import projet.bdcc.entities.Salle;
import projet.bdcc.entities.Ticket;
import projet.bdcc.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CinemaProjetApplication implements CommandLineRunner {
	@Autowired private ICinemaInitService cinemaInitService;
	@Autowired private RepositoryRestConfiguration restConfiguration;
	public static void main(String[] args) {
	SpringApplication.run(CinemaProjetApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
	restConfiguration.exposeIdsFor(Film.class, Salle.class, Ticket.class);
	cinemaInitService.initVilles(); 
	cinemaInitService.initCinemas();
	cinemaInitService.initSalles(); 
	cinemaInitService.initPalces();
	cinemaInitService.initSeances(); 
	cinemaInitService.initCategories();
	cinemaInitService.initFilms();
	cinemaInitService.initProjections();
	cinemaInitService.initTickets();
	}


}