package com.spring;

import java.util.Collection;
import java.util.Date;

import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.spring.model.DraftMail;
import com.spring.model.Patch;

@SpringBootApplication
@ComponentScan("com.spring")
public class DemoApplication implements CommandLineRunner {

	@Autowired
	Session session;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		//session.deleteAll(Patch.class);
		//session.deleteAll(DraftMail.class);
		/*Patch p = new Patch();
		p.release="10.4";
		session.save(p);

		p = session.load(Patch.class, p.getId());

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pname", "10.4");

		Iterable<Patch> patches = session.query(Patch.class, "MATCH(n:Patch) WHERE n.release={pname} RETURN n;", parameters);

		List<Patch> plist = IterableUtils.toList(patches);

		for (Patch patch : plist) {
			System.out.println(patch.getId());
		}*/

		//timer la saatte bir kontrol et Java timer


		/* Timer timer=new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				 session.loadAll(Patch.class);

			}
		}), 120000); */

		/*Collection<Patch> patches = session.loadAll(Patch.class);
		for (Patch patch : patches) {
			if (patch.getCutOfDate() != null) {
				if(patch.getCutOfDate().equals(new Date())) {
					//mesaj at mail at
				}	
			}

		}*/
	}


}

























