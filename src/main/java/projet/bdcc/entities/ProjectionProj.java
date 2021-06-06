package projet.bdcc.entities;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="p1",types={projet.bdcc.entities.Projection.class})
public interface ProjectionProj {
	
	public Long getId();
	public double getPrix();
	public Date getDateProjection();
	public Salle getSalle();
	public Film getFilm();
	public Sceance getSceance();
	public Collection<Ticket> getTickets();
}
