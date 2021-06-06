package projet.bdcc.web;
import java.text.ParseException;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import projet.bdcc.dao.CategorieRepository;
import projet.bdcc.dao.CinemaRepository;
import projet.bdcc.dao.FilmRepository;
import projet.bdcc.dao.PlaceRepository;
import projet.bdcc.dao.ProjectionRepository;
import projet.bdcc.dao.SalleRepository;
import projet.bdcc.dao.SceanceRepository;
import projet.bdcc.dao.TicketRepository;
import projet.bdcc.dao.VilleRepository;
import projet.bdcc.entities.Categorie;
import projet.bdcc.entities.Cinema;
import projet.bdcc.entities.Film;
import projet.bdcc.entities.Place;
import projet.bdcc.entities.Projection;
import projet.bdcc.entities.Salle;
import projet.bdcc.entities.Sceance;
import projet.bdcc.entities.Ticket;
import projet.bdcc.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class CinemaController {
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private SceanceRepository sceanceRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private TicketRepository ticketRepository;


	@GetMapping("/filmList")
	public String filmList(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		String path="..\\images\\";
		Page<Film> pageFilms= filmRepository.findByTitreContains(mc, PageRequest.of(page,5));
		model.addAttribute("listFilms", pageFilms.getContent());
		model.addAttribute("pages", new int[pageFilms.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		model.addAttribute("path", path);
		return "filmList";		
		 
	}

	@GetMapping("/deleteFilm")
	public String deleteFilm(Long id, int page, String motCle){
		filmRepository.deleteById(id);
		return "redirect:/filmList?page="+page+"&motCle="+motCle;
	
	}
	


	/** FilmController Ends **/
	/** TicketController Begins **/

	@GetMapping("/ticketList")
	public String ticketList(Model model,
			@RequestParam(name="page", defaultValue="0") int page){
		Page<Ticket> pageTickets= ticketRepository.findAll(PageRequest.of(page,5));
		model.addAttribute("listTickets", pageTickets.getContent());
		model.addAttribute("pages", new int[pageTickets.getTotalPages()]);
		model.addAttribute("currentPage", page);
		return "ticketList";
		
	}
	
	@GetMapping("/editTicket")
	public String editTicket(Long id, Model model){
		Ticket ticket = ticketRepository.findById(id).get();
		List<Place> Places= placeRepository.findAll();
		List<Projection> Projections= projectionRepository.findAll();
		model.addAttribute("listPlaces", Places);
		model.addAttribute("listProjections", Projections);
		model.addAttribute("ticket", ticket);
		return "editTicketForm";
	}
	
	@GetMapping("/deleteTicket")
	public String deleteTicket(Long id, int page, String motCle){
		ticketRepository.deleteById(id);
		return "redirect:/ticketList?page="+page+"&motCle="+motCle;
	}
	
	@GetMapping("/formTicket")
	public String formTicket(Model model){
		List<Place> Places= placeRepository.findAll();
		List<Projection> Projections= projectionRepository.findAll();
		model.addAttribute("listPlaces", Places);
		model.addAttribute("listProjections", Projections);
		model.addAttribute("ticket", new Ticket());
		
		return "formTicket";
	}
	
	@PostMapping("/saveTicket")
	public String saveTicket(Model model, @Valid Ticket ticket, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formTicket";
		ticketRepository.save(ticket);
		return "redirect:/ticketList";
	}
	@GetMapping("/cinemaList")
	public String cinemaList(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		Page<Cinema> pageCinemas= cinemaRepository.findByNameContains(mc, PageRequest.of(page,5));
		model.addAttribute("listCinemas", pageCinemas.getContent());
		model.addAttribute("pages", new int[pageCinemas.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "cinemaList";
		
	}
	
	@GetMapping("/editCinema")
	public String editCinema(Long id, Model model){
		Cinema cinema = cinemaRepository.findById(id).get();
		List<Ville> Villes= villeRepository.findAll();
		model.addAttribute("listVilles", Villes);
		model.addAttribute("cinema", cinema);
		return "editCinemaForm";
	}
	
	@GetMapping("/deleteCinema")
	public String deleteCinema(Long id, int page, String motCle){
		cinemaRepository.deleteById(id);
		return "redirect:/cinemaList?page="+page+"&motCle="+motCle;
	}
	
	@GetMapping("/formCinema")
	public String formCinema(Model model){
		List<Ville> Villes= villeRepository.findAll();
		model.addAttribute("listVilles", Villes);
		model.addAttribute("cinema", new Cinema());
		
		return "formCinema";
	}
	
	@PostMapping("/saveCinema")
	public String saveCinema(Model model, @Valid Cinema cinema, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formCinema";
		cinemaRepository.save(cinema);
		return "redirect:/cinemaList";
	}


	@GetMapping("/placeList")
	public String placeList(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		Page<Place> pagePlaces= placeRepository.findAll(PageRequest.of(page,5));
		model.addAttribute("listPlaces", pagePlaces.getContent());
		model.addAttribute("pages", new int[pagePlaces.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "placeList";
		
	}
	
	@GetMapping("/editPlace")
	public String editPlace(Long id, Model model){
		Place place = placeRepository.findById(id).get();
		List<Salle> Salles= salleRepository.findAll();
		model.addAttribute("listSalles", Salles);
		model.addAttribute("place", place);
		return "editPlaceForm";
	}
	
	@GetMapping("/deletePlace")
	public String deletePlace(Long id, int page, String motCle){
		placeRepository.deleteById(id);
		return "redirect:/placeList?page="+page+"&motCle="+motCle;
	}
	
	@GetMapping("/formPlace")
	public String formPlace(Model model){
		List<Salle> Salles= salleRepository.findAll();
		model.addAttribute("listSalles", Salles);
		model.addAttribute("place", new Place());
		
		return "formPlace";
	}
	
	@PostMapping("/savePlace")
	public String savePlace(Model model, @Valid Place place, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formPlace";
		placeRepository.save(place);
		return "redirect:/placeList";
	}

	@GetMapping("/seanceList")
	public String seanceList(Model model,
			@RequestParam(name="page", defaultValue="0") int page){
		Page<Sceance> pageSceances= sceanceRepository.findAll(PageRequest.of(page,5));
		model.addAttribute("listSceances", pageSceances.getContent());
		model.addAttribute("pages", new int[ pageSceances.getTotalPages()]);
		model.addAttribute("currentPage", page);
		return "seanceList";
		
	}
	
	@GetMapping("/projectionList")
	public String projectionList(Model model,
			@RequestParam(name="page", defaultValue="0") int page){
		Page<Projection> pageProjections= projectionRepository.findAll(PageRequest.of(page,5));
		model.addAttribute("listProjections", pageProjections.getContent());
		model.addAttribute("pages", new int[ pageProjections.getTotalPages()]);
		model.addAttribute("currentPage", page);
		return "projectionList";
		
	}
	@GetMapping("/editProjection")
	public String editProjection(Long id, Model model){
		Projection projection = projectionRepository.findById(id).get();
		List<Film> film= filmRepository.findAll();
		List<Salle> salle= salleRepository.findAll();
		List<Sceance> sceance= sceanceRepository.findAll();
		model.addAttribute("listFilms", film);
		model.addAttribute("listSalles", salle);
		model.addAttribute("listSceances", sceance);
		model.addAttribute("projection", projection);
		return "editProjectionForm";
	}
	
	@GetMapping("/deleteProjection")
	public String deleteProjection(Long id, int page, String motCle){
		projectionRepository.deleteById(id);
		return "redirect:/projectionList?page="+page+"&motCle="+motCle;
	}
	@GetMapping("/formProjection")
	public String formProjectio(Model model){
		List<Film> film= filmRepository.findAll();
		List<Salle> salle= salleRepository.findAll();
		List<Sceance> sceance= sceanceRepository.findAll();
		model.addAttribute("projection", new Projection());
		model.addAttribute("listFilms", film);
		model.addAttribute("listSalles", salle);
		model.addAttribute("listSceances", sceance);
		return "formProjection";
	}
	@PostMapping("/saveEditProjection")
	public String saveEditProjection(Model model, @Valid Projection projection, BindingResult bindingResult) throws ParseException{
		if(bindingResult.hasErrors()) return "editProjectionForm";
		
		@SuppressWarnings("deprecation")
		Date parsed = new Date(Integer.parseInt(projection.getSdateProjection().split("-")[0])-1900,Integer.parseInt(projection.getSdateProjection().split("-")[1])
				,Integer.parseInt(projection.getSdateProjection().split("-")[2]));

		projection.setDateProjection(parsed);
		projectionRepository.save(projection);
		return "redirect:/projectionList";
	}

	
	@PostMapping("/saveProjection")
	public String saveProjection(Model model, @Valid Projection projection, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formProjection";
		projectionRepository.save(projection);
		return "redirect:/projectionList";
	}
	@GetMapping("/villeList")
	public String villeList(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		Page<Ville> pageVilles= villeRepository.findByNameContains(mc, PageRequest.of(page,5));
		model.addAttribute("listVilles", pageVilles.getContent());
		model.addAttribute("pages", new int[pageVilles.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "villeList";
		
	}
	@GetMapping("/editVille")
	public String editVille(Long id, Model model){
		Ville ville = villeRepository.findById(id).get();
		model.addAttribute("ville", ville);
		return "editVilleForm";
	}
	
	@GetMapping("/deleteVille")
	public String deleteVille(Long id, int page, String motCle){
		villeRepository.deleteById(id);
		return "redirect:/villeList?page="+page+"&motCle="+motCle;
	}
	@GetMapping("/formVille")
	public String formVille(Model model){
		model.addAttribute("ville", new Ville());
		return "formVille";
	}
	
	@PostMapping("/saveVille")
	public String saveVille(Model model, @Valid Ville ville, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formVille";
		villeRepository.save(ville);
		return "redirect:/villeList";
	}
	@PostMapping("/saveEditVille")
	public String saveEditVille(Model model, @Valid Ville ville, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "editVilleForm";
		villeRepository.save(ville);
		return "redirect:/villeList";
	}

	@GetMapping("/categorieList")
	public String categorieList(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		Page<Categorie> pageCategories= categorieRepository.findByNameContains(mc, PageRequest.of(page,5));
		model.addAttribute("listCats", pageCategories.getContent());
		model.addAttribute("pages", new int[pageCategories.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "categorieList";
		
	}
	@GetMapping("/editCategorie")
	public String editCategorie(Long id, Model model){
		Categorie categorie = categorieRepository.findById(id).get();
		model.addAttribute("categorie", categorie);
		return "editCategorieForm";
	}
	
	@GetMapping("/deleteCategorie")
	public String deleteCategorie(Long id, int page, String motCle){
		categorieRepository.deleteById(id);
		return "redirect:/categorieList?page="+page+"&motCle="+motCle;
	}
	@GetMapping("/formCategorie")
	public String formCategorie(Model model){
		model.addAttribute("categorie", new Categorie());
		return "formCategorie";
	}
	
	@PostMapping("/saveCategorie")
	public String saveCategorie(Model model, @Valid Categorie categorie, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formVille";
		categorieRepository.save(categorie);
		return "redirect:/categorieList";
	}

	@GetMapping("/salleList")
	public String salleList(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		Page<Salle> pageSalles= salleRepository.findByNameContains(mc, PageRequest.of(page,5));
		model.addAttribute("listSalles", pageSalles.getContent());
		model.addAttribute("pages", new int[pageSalles.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "salleList";
		
	}
	
	@GetMapping("/editSalle")
	public String editSalle(Long id, Model model){
		Salle salle = salleRepository.findById(id).get();
		List<Cinema> Cinema= cinemaRepository.findAll();
		model.addAttribute("listCinemas", Cinema);
		model.addAttribute("salle", salle);
		return "editSalleForm";
	}
	
	@GetMapping("/deleteSalle")
	public String deleteSalle(Long id, int page, String motCle){
		salleRepository.deleteById(id);
		return "redirect:/salleList?page="+page+"&motCle="+motCle;
	}
	
	@GetMapping("/formSalle")
	public String formSalle(Model model){
		List<Cinema> Cinemas= cinemaRepository.findAll();
		model.addAttribute("listCinemas", Cinemas);
		model.addAttribute("salle", new Salle());
		
		return "formSalle";
	}
	
	@PostMapping("/saveSalle")
	public String saveSalle(Model model, @Valid Salle salle, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formSalle";
		salleRepository.save(salle);
		return "redirect:/salleList";
	}



}
